package ua.sumdu.yermolenko.services.impl;

import lombok.NonNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.services.interfaces.CityCoordinatesService;
import ua.sumdu.yermolenko.services.interfaces.DarkSkyService;

import java.util.concurrent.Future;

@Service
public class DarkSkyServiceImpl implements DarkSkyService {
    @Value("${servicename.darksky}")
    private String serviceName;
    @Value("${darksky.api.key}")
    private String apiKey;
    @Value("${darksky.url}")
    String url;
    @Autowired
    CityCoordinatesService cityCoordinatesService;

    @Override
    @Async
    @Cacheable("darkSkyCurrent")
    public Future<String> currentWeather(@NonNull String city, @NonNull String countryCode) {
        long unixTime = System.currentTimeMillis() / 1000L;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(headers);

        double[] cord;
        try {
            cord = cityCoordinatesService.getCityCoordinates(city, countryCode);
        } catch (JSONException | IllegalArgumentException e) {
            return new AsyncResult<>("Request Failed: City not found");
        } catch (HttpServerErrorException e) {
            return new AsyncResult<>("City Coordinates Server Exception: " + e.toString());
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

            return new AsyncResult<>(weatherDataDto.toString());
        } else {
            return new AsyncResult<>("Request Failed" +"\n"
                    + response.getStatusCode());
        }
    }
}
