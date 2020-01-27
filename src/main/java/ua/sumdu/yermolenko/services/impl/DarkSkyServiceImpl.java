package ua.sumdu.yermolenko.services.impl;

import lombok.NonNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.services.ExecutorSingleton;
import ua.sumdu.yermolenko.services.interfaces.CityCoordinatesService;
import ua.sumdu.yermolenko.services.interfaces.DarkSkyService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


@Service
public class DarkSkyServiceImpl implements DarkSkyService {
    @Value("${servicename.darksky}")
    private String serviceName;
    @Value("${darksky.api.key}")
    private String apiKey;
    @Value("${darksky.url}")
    private String url;
    @Value("${api.timeout}")
    private int apiTimeout;
    @Autowired
    private CityCoordinatesService cityCoordinatesService;

    @Override
    @Cacheable("darkSkyCurrent")
    public String darkSkyCurrentWeatherThread(@NonNull String city, @NonNull String countryCode) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->
                currentWeather(city, countryCode), ExecutorSingleton.getExecutor()
        ).completeOnTimeout("Превышено время ожидания ответа от сервера.", apiTimeout, TimeUnit.SECONDS);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public String currentWeather(@NonNull String city, @NonNull String countryCode) {
        long unixTime = System.currentTimeMillis() / 1000L;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(headers);

        double[] cord;
        try {
            cord = cityCoordinatesService.getCityCoordinates(city, countryCode);
        } catch (JSONException | IllegalArgumentException e) {
            return "Request Failed: City not found";
        } catch (HttpServerErrorException e) {
            return "City Coordinates Server Exception: " + e.toString();
        }

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class,
                apiKey, cord[0], cord[1], unixTime
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            String temperature = String.valueOf(jsonObject
                    .getJSONObject("currently")
                    .getDouble("temperature"));

            WeatherDataDto weatherDataDto = new WeatherDataDto();
            weatherDataDto.setServiceName(serviceName);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setTemperature(temperature);

            return weatherDataDto.toString();
        } else {
            return "Request Failed" +"\n"
                    + response.getStatusCode();
        }
    }
}
