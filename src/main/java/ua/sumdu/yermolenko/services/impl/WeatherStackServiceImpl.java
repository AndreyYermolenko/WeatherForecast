package ua.sumdu.yermolenko.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.model.WeatherStackData;
import ua.sumdu.yermolenko.config.ExecutorSingleton;
import ua.sumdu.yermolenko.tools.WeatherDataConverter;
import ua.sumdu.yermolenko.services.WeatherStackService;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Class WeatherStackServiceImpl implements interface WeatherStackService.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
@Service
public class WeatherStackServiceImpl implements WeatherStackService {
    private final static Logger logger = LogManager.getLogger(WeatherStackServiceImpl.class);
    @Value("${weatherstack.api.key}")
    private String apiKey;
    @Value("${weatherstack.url}")
    private String url;
    @Autowired
    private WeatherDataConverter weatherDataConverter;
    @Value("${api.timeout}")
    private int apiTimeout;

    /**
     * Method getTemperatureThread executes an API request in a separate thread
     * to obtain temperature data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Override
    public String getTemperatureThread(@NonNull String city, @NonNull String countryCode) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->
                getTemperature(city, countryCode), ExecutorSingleton.getExecutor()
        ).completeOnTimeout("Превышено время ожидания ответа от сервера.", apiTimeout, TimeUnit.SECONDS);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("WeatherStackService problem", e);
            return "Request Failed. Server error.";
        }
    }

    /**
     * Method getCityCoordinatesThread executes an API request in a separate thread
     * to obtain data on the coordinates of the city.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Override
    public String getCityCoordinatesThread(@NonNull String city, @NonNull String countryCode) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->
                getCityCoordinates(city, countryCode), ExecutorSingleton.getExecutor()
        ).completeOnTimeout("Превышено время ожидания ответа от сервера.", apiTimeout, TimeUnit.SECONDS);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("WeatherStackService problem", e);
            return "Request Failed. Server error.";
        }
    }

    /**
     * Method getFullWeatherThread executes an API request in a separate thread
     * to obtain current weather data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Override
    public String getFullWeatherThread(@NonNull String city, @NonNull String countryCode) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->
                getFullWeather(city, countryCode), ExecutorSingleton.getExecutor()
        ).completeOnTimeout("Превышено время ожидания ответа от сервера.", apiTimeout, TimeUnit.SECONDS);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("WeatherStackService problem", e);
            return "Request Failed. Server error.";
        }
    }

    /**
     * Method getTemperature executes an API request to obtain temperature data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Cacheable("weatherStackTemperature")
    public String getTemperature(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherStackData weatherData;
            WeatherDataDto weatherDataDto;
            try {
                weatherData = objectMapper.readValue(response.getBody(), WeatherStackData.class);
                weatherDataDto = weatherDataConverter.toJsonWeatherStackDataConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                logger.error("WeatherStackService problem", e);
                return "Request Failed. Server error.";
            }
            return weatherDataDto.toJsonTemperature();
        } else {
            return "Request Failed" +"\n"
                    + response.getStatusCode();
        }
    }

    /**
     * Method getCityCoordinates executes an API request to obtain data
     * on the coordinates of the city.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Cacheable("weatherStackCityCoordinates")
    public String getCityCoordinates(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherStackData weatherData;
            WeatherDataDto weatherDataDto;
            try {
                weatherData = objectMapper.readValue(response.getBody(), WeatherStackData.class);
                weatherDataDto = weatherDataConverter.toJsonWeatherStackDataConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                logger.error("WeatherStackService problem", e);
                return "Request Failed. Server error.";
            }
            return weatherDataDto.toJsonCoordinates();
        } else {
            return "Request Failed" +"\n"
                    + response.getStatusCode();
        }
    }

    /**
     * Method getFullWeather executes an API request to obtain current weather data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Override
    @Cacheable("weatherStackFullWeather")
    public String getFullWeather(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherStackData weatherData;
            WeatherDataDto weatherDataDto;
            try {
                weatherData = objectMapper.readValue(response.getBody(), WeatherStackData.class);
                weatherDataDto = weatherDataConverter.toJsonWeatherStackDataConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                logger.error("WeatherStackService problem", e);
                return "Request Failed. Server error.";
            }
            return weatherDataDto.toJsonFullWeather();
        } else {
            return "Request Failed" +"\n"
                    + response.getStatusCode();
        }
    }

    private ResponseEntity<String> getWeather(@NonNull String city, @NonNull String countryCode) {
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
        return response;
    }
}
