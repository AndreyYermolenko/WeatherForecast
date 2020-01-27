package ua.sumdu.yermolenko.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.model.WeatherStackData;
import ua.sumdu.yermolenko.services.ExecutorSingleton;
import ua.sumdu.yermolenko.services.WeatherDataConverter;
import ua.sumdu.yermolenko.services.interfaces.WeatherStackService;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class WeatherStackServiceImpl implements WeatherStackService {
    @Value("${weatherstack.api.key}")
    private String apiKey;
    @Value("${weatherstack.url}")
    private String url;
    @Autowired
    private WeatherDataConverter weatherDataConverter;
    @Value("${api.timeout}")
    private int apiTimeout;

    @Override
    @Cacheable("weatherStackCurrent")
    public String weatherStackCurrentWeatherThread(@NonNull String city, @NonNull String countryCode) {
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
                apiKey, city, countryCode
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherStackData weatherData;
            WeatherDataDto weatherDataDto;
            try {
                weatherData = objectMapper.readValue(response.getBody(), WeatherStackData.class);
                weatherDataDto = weatherDataConverter.toJsonWeatherStackDataConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return weatherDataDto.toString();
        } else {
            return "Request Failed" +"\n"
                    + response.getStatusCode();
        }
    }
}
