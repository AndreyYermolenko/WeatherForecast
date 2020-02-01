package ua.sumdu.yermolenko.services.impl;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.config.ExecutorSingleton;
import ua.sumdu.yermolenko.services.WeatherBitService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Class WeatherBitServiceImpl implements interface WeatherBitService.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
@Service
public class WeatherBitServiceImpl implements WeatherBitService {
    private final static Logger logger = LogManager.getLogger(WeatherBitServiceImpl.class);
    @Value("${servicename.weatherbit}")
    private String serviceName;
    @Value("${weatherbit.api.key}")
    private String apiKey;
    @Value("${weatherbit.url}")
    private String url;
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
            logger.error("WeatherBitService problem", e);
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
            logger.error("WeatherBitService problem", e);
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
            logger.error("WeatherBitService problem", e);
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
    @Cacheable("weatherBitTemperature")
    public String getTemperature(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
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
    @Cacheable("weatherBitCityCoordinates")
    public String getCityCoordinates(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            JSONObject jsonObject = new JSONObject(response.getBody());
            String latitude = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("lat"));
            String longitude = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("lon"));

            weatherDataDto.setServiceName(serviceName);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setLatitude(latitude);
            weatherDataDto.setLongitude(longitude);

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
    @Cacheable("weatherBitFullWeather")
    public String getFullWeather(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            JSONObject jsonObject = new JSONObject(response.getBody());
            String temperature = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("temp"));
            String pressure = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("pres"));
            String windSpeed = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("wind_spd"));

            weatherDataDto.setServiceName(serviceName);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setTemperature(temperature);
            weatherDataDto.setPressure(pressure);
            weatherDataDto.setWindSpeed(windSpeed);

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
                city, countryCode, apiKey
        );
        return response;
    }
}
