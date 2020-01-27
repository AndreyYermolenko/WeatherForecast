package ua.sumdu.yermolenko.services.impl;

import lombok.NonNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.services.ExecutorSingleton;
import ua.sumdu.yermolenko.services.interfaces.WeatherBitService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class WeatherBitServiceImpl implements WeatherBitService {
    @Value("${servicename.weatherbit}")
    private String serviceName;
    @Value("${weatherbit.api.key}")
    private String apiKey;
    @Value("${weatherbit.url}")
    private String url;
    @Value("${api.timeout}")
    private int apiTimeout;

    @Override
    @Cacheable("weatherBitCurrent")
    public String weatherBitCurrentWeatherThread(@NonNull String city, @NonNull String countryCode) {
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
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class,
                city, countryCode, apiKey
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            JSONObject jsonObject = new JSONObject(response.getBody());
            String temperature = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("temp"));

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
