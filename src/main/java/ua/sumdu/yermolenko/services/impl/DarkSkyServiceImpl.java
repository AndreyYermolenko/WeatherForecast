package ua.sumdu.yermolenko.services.impl;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import ua.sumdu.yermolenko.config.ExecutorSingleton;
import ua.sumdu.yermolenko.services.CityCoordinatesService;
import ua.sumdu.yermolenko.services.DarkSkyService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Class DarkSkyServiceImpl implements interface DarkSkyService.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
@Service
public class DarkSkyServiceImpl implements DarkSkyService {
    private final static Logger logger = LogManager.getLogger(DarkSkyServiceImpl.class);
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
            logger.error("DarkSkyService problem", e);
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
            logger.error("DarkSkyService problem", e);
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
            logger.error("DarkSkyService problem", e);
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
    @Cacheable("darkSkyTemperature")
    public String getTemperature(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
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

            return weatherDataDto.toJsonTemperature();
        } else {
            return "Request Failed: "
                    + response.getBody() + " "
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
    @Cacheable("darkSkyCityCoordinates")
    public String getCityCoordinates(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            String latitude = String.valueOf(jsonObject
                    .getDouble("latitude"));
            String longitude = String.valueOf(jsonObject
                    .getDouble("longitude"));

            WeatherDataDto weatherDataDto = new WeatherDataDto();
            weatherDataDto.setServiceName(serviceName);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setLatitude(latitude);
            weatherDataDto.setLongitude(longitude);

            return weatherDataDto.toJsonCoordinates();
        } else {
            return "Request Failed: "
                    + response.getBody() + " "
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
    @Cacheable("darkSkyFullWeather")
    public String getFullWeather(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            String temperature = String.valueOf(jsonObject
                    .getJSONObject("currently")
                    .getDouble("temperature"));
            String pressure = String.valueOf(jsonObject
                    .getJSONObject("currently")
                    .getDouble("pressure"));
            String windSpeed = String.valueOf(jsonObject
                    .getJSONObject("currently")
                    .getDouble("windSpeed"));

            WeatherDataDto weatherDataDto = new WeatherDataDto();
            weatherDataDto.setServiceName(serviceName);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setTemperature(temperature);
            weatherDataDto.setPressure(pressure);
            weatherDataDto.setWindSpeed(windSpeed);

            return weatherDataDto.toJsonFullWeather();
        } else {
            return "Request Failed: "
                    + response.getBody() + " "
                    + response.getStatusCode();
        }
    }

    private ResponseEntity<String> getWeather(@NonNull String city, @NonNull String countryCode) {
        long unixTime = System.currentTimeMillis() / 1000L;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(headers);

        double[] cord;
        try {
            cord = cityCoordinatesService.getCityCoordinates(city, countryCode);
        } catch (JSONException | IllegalArgumentException e) {
            return new ResponseEntity<String>("City not found", HttpStatus.BAD_REQUEST);
        } catch (HttpServerErrorException e) {
            return new ResponseEntity<String>("City Coordinates Server Exception: " + e.toString(), HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class,
                apiKey, cord[0], cord[1], unixTime
        );

        return response;
    }
}
