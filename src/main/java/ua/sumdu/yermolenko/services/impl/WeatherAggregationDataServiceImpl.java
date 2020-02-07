package ua.sumdu.yermolenko.services.impl;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.sumdu.yermolenko.config.ExecutorSingleton;
import ua.sumdu.yermolenko.model.WeatherDataArr;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.services.*;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static ua.sumdu.yermolenko.services.ServiceConstants.*;

/**
 * Class WeatherAggregationDataServiceImpl implements interface WeatherAggregationDataService.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
@Service
public class WeatherAggregationDataServiceImpl implements WeatherAggregationDataService {
    private final static Logger logger = LogManager.getLogger(WeatherAggregationDataServiceImpl.class);

    private final ResponseEntity<WeatherDataDto> RESPONSE_FAILED = new ResponseEntity<WeatherDataDto>(
            new WeatherDataDto(null,
                    "Response Failed. Server error."),
            HttpStatus.INTERNAL_SERVER_ERROR);

    private final ResponseEntity<WeatherDataDto> DARKSKY_TIMEOUT = new ResponseEntity<>(
            new WeatherDataDto(DARKSKY_SERVICENAME,TIMEOUT_EXCEPTION_MESSAGE),
            HttpStatus.REQUEST_TIMEOUT);
    private final ResponseEntity<WeatherDataDto> OPENWEATHERMAP_TIMEOUT = new ResponseEntity<>(
            new WeatherDataDto(OPENWEATHERMAP_SERVICENAME,TIMEOUT_EXCEPTION_MESSAGE),
            HttpStatus.REQUEST_TIMEOUT);
    private final ResponseEntity<WeatherDataDto> WEATHERBIT_TIMEOUT = new ResponseEntity<>(
            new WeatherDataDto(WEATHERBIT_SERVICENAME,TIMEOUT_EXCEPTION_MESSAGE),
            HttpStatus.REQUEST_TIMEOUT);
    private final ResponseEntity<WeatherDataDto> WEATHERSTACK_TIMEOUT = new ResponseEntity<>(
            new WeatherDataDto(WEATHERSTACK_SERVICENAME,TIMEOUT_EXCEPTION_MESSAGE),
            HttpStatus.REQUEST_TIMEOUT);

    @Autowired
    private DarkSkyService darkSkyService;
    @Autowired
    private OpenWeatherMapService openWeatherMapService;
    @Autowired
    private WeatherBitService weatherBitService;
    @Autowired
    private WeatherStackService weatherStackService;

    /**
     * Method WeatherAggregationData returns temperature data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return String
     */
    @Override
    @Cacheable("temperatureAggregation")
    public ResponseEntity<WeatherDataDto> temperatureAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<ResponseEntity<WeatherDataDto>> darkSkyFuture = CompletableFuture.supplyAsync(() ->
                darkSkyService.getTemperature(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(DARKSKY_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.getTemperature(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherBitFuture = CompletableFuture.supplyAsync(() ->
                weatherBitService.getTemperature(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERBIT_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherStackFuture = CompletableFuture.supplyAsync(() ->
                weatherStackService.getTemperature(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
            list.add(darkSkyFuture.get().getBody());
            list.add(openWeatherMapFuture.get().getBody());
            list.add(weatherBitFuture.get().getBody());
            list.add(weatherStackFuture.get().getBody());
        } catch (InterruptedException | ExecutionException e) {
            logger.error("TemperatureAggregation problem", e);
            return RESPONSE_FAILED;
        }

        if ("json".equals(ext)) {
            return new ResponseEntity(list, HttpStatus.OK);
        } else {
            WeatherDataArr arrXml = new WeatherDataArr(listToArr(list));
            return new ResponseEntity(arrXml, HttpStatus.OK);
        }
    }

    /**
     * Method WeatherAggregationData returns city coordinates from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return String
     */
    @Override
    @Cacheable("cityCoordinatesAggregation")
    public ResponseEntity<WeatherDataDto> cityCoordinatesAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<ResponseEntity<WeatherDataDto>> darkSkyFuture = CompletableFuture.supplyAsync(() ->
                darkSkyService.getCityCoordinates(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(DARKSKY_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.getCityCoordinates(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherBitFuture = CompletableFuture.supplyAsync(() ->
                weatherBitService.getCityCoordinates(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERBIT_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherStackFuture = CompletableFuture.supplyAsync(() ->
                weatherStackService.getCityCoordinates(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
            list.add(darkSkyFuture.get().getBody());
            list.add(openWeatherMapFuture.get().getBody());
            list.add(weatherBitFuture.get().getBody());
            list.add(weatherStackFuture.get().getBody());
        } catch (InterruptedException | ExecutionException e) {
            logger.error("CityCoordinatesAggregation problem", e);
            return RESPONSE_FAILED;
        }

        if ("json".equals(ext)) {
            return new ResponseEntity(list, HttpStatus.OK);
        } else {
            WeatherDataArr arrXml = new WeatherDataArr(listToArr(list));
            return new ResponseEntity(arrXml, HttpStatus.OK);
        }
    }

    /**
     * Method WeatherAggregationData returns pressure data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return String
     */
    @Override
    @Cacheable("pressureAggregation")
    public ResponseEntity<WeatherDataDto> pressureAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<ResponseEntity<WeatherDataDto>> darkSkyFuture = CompletableFuture.supplyAsync(() ->
                darkSkyService.getPressure(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(DARKSKY_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.getPressure(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherBitFuture = CompletableFuture.supplyAsync(() ->
                weatherBitService.getPressure(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERBIT_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherStackFuture = CompletableFuture.supplyAsync(() ->
                weatherStackService.getPressure(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
            list.add(darkSkyFuture.get().getBody());
            list.add(openWeatherMapFuture.get().getBody());
            list.add(weatherBitFuture.get().getBody());
            list.add(weatherStackFuture.get().getBody());
        } catch (InterruptedException | ExecutionException e) {
            logger.error("PressureAggregation problem", e);
            return RESPONSE_FAILED;
        }

        if ("json".equals(ext)) {
            return new ResponseEntity(list, HttpStatus.OK);
        } else {
            WeatherDataArr arrXml = new WeatherDataArr(listToArr(list));
            return new ResponseEntity(arrXml, HttpStatus.OK);
        }
    }

    /**
     * Method WeatherAggregationData returns wind speed data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return String
     */
    @Override
    @Cacheable("windSpeedAggregation")
    public ResponseEntity<WeatherDataDto> windSpeedAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<ResponseEntity<WeatherDataDto>> darkSkyFuture = CompletableFuture.supplyAsync(() ->
                darkSkyService.getWindSpeed(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(DARKSKY_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.getWindSpeed(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherBitFuture = CompletableFuture.supplyAsync(() ->
                weatherBitService.getWindSpeed(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERBIT_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherStackFuture = CompletableFuture.supplyAsync(() ->
                weatherStackService.getWindSpeed(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
            list.add(darkSkyFuture.get().getBody());
            list.add(openWeatherMapFuture.get().getBody());
            list.add(weatherBitFuture.get().getBody());
            list.add(weatherStackFuture.get().getBody());
        } catch (InterruptedException | ExecutionException e) {
            logger.error("WindSpeedAggregation problem", e);
            return RESPONSE_FAILED;
        }

        if ("json".equals(ext)) {
            return new ResponseEntity(list, HttpStatus.OK);
        } else {
            WeatherDataArr arrXml = new WeatherDataArr(listToArr(list));
            return new ResponseEntity(arrXml, HttpStatus.OK);
        }
    }

    /**
     * Method WeatherAggregationData returns humidity data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return String
     */
    @Override
    @Cacheable("humidityAggregation")
    public ResponseEntity<WeatherDataDto> humidityAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<ResponseEntity<WeatherDataDto>> darkSkyFuture = CompletableFuture.supplyAsync(() ->
                darkSkyService.getHumidity(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(DARKSKY_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.getHumidity(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherBitFuture = CompletableFuture.supplyAsync(() ->
                weatherBitService.getHumidity(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERBIT_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherStackFuture = CompletableFuture.supplyAsync(() ->
                weatherStackService.getHumidity(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
            list.add(darkSkyFuture.get().getBody());
            list.add(openWeatherMapFuture.get().getBody());
            list.add(weatherBitFuture.get().getBody());
            list.add(weatherStackFuture.get().getBody());
        } catch (InterruptedException | ExecutionException e) {
            logger.error("HumidityAggregation problem", e);
            return RESPONSE_FAILED;
        }

        if ("json".equals(ext)) {
            return new ResponseEntity(list, HttpStatus.OK);
        } else {
            WeatherDataArr arrXml = new WeatherDataArr(listToArr(list));
            return new ResponseEntity(arrXml, HttpStatus.OK);
        }
    }

    /**
     * Method WeatherAggregationData returns weather data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return String
     */
    @Override
    @Cacheable("fullWeatherAggregation")
    public ResponseEntity<WeatherDataDto> fullWeatherAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<ResponseEntity<WeatherDataDto>> darkSkyFuture = CompletableFuture.supplyAsync(() ->
                darkSkyService.getFullWeather(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(DARKSKY_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.getFullWeather(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherBitFuture = CompletableFuture.supplyAsync(() ->
                weatherBitService.getFullWeather(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERBIT_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherStackFuture = CompletableFuture.supplyAsync(() ->
                weatherStackService.getFullWeather(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
            list.add(darkSkyFuture.get().getBody());
            list.add(openWeatherMapFuture.get().getBody());
            list.add(weatherBitFuture.get().getBody());
            list.add(weatherStackFuture.get().getBody());
        } catch (InterruptedException | ExecutionException e) {
            logger.error("FullWeatherAggregation problem", e);
            return RESPONSE_FAILED;
        }

        if ("json".equals(ext)) {
            return new ResponseEntity(list, HttpStatus.OK);
        } else {
            WeatherDataArr arrXml = new WeatherDataArr(listToArr(list));
            return new ResponseEntity(arrXml, HttpStatus.OK);
        }
    }

    /**
     * Method WeatherAggregationData returns city sunrise time from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    @Override
    @Cacheable("sunriseTimeAggregation")
    public ResponseEntity<WeatherDataDto> sunriseTimeAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<ResponseEntity<WeatherDataDto>> darkSkyFuture = CompletableFuture.supplyAsync(() ->
                darkSkyService.getSunriseTime(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(DARKSKY_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.getSunriseTime(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherBitFuture = CompletableFuture.supplyAsync(() ->
                weatherBitService.getSunriseTime(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERBIT_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherStackFuture = CompletableFuture.supplyAsync(() ->
                weatherStackService.getSunriseTime(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
            list.add(darkSkyFuture.get().getBody());
            list.add(openWeatherMapFuture.get().getBody());
            list.add(weatherBitFuture.get().getBody());
            list.add(weatherStackFuture.get().getBody());
        } catch (InterruptedException | ExecutionException e) {
            logger.error("FullWeatherAggregation problem", e);
            return RESPONSE_FAILED;
        }

        if ("json".equals(ext)) {
            return new ResponseEntity(list, HttpStatus.OK);
        } else {
            WeatherDataArr arrXml = new WeatherDataArr(listToArr(list));
            return new ResponseEntity(arrXml, HttpStatus.OK);
        }
    }

    /**
     * Method WeatherAggregationData returns direction wind from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    @Override
    @Cacheable("directionWindAggregation")
    public ResponseEntity<WeatherDataDto> directionWindAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<ResponseEntity<WeatherDataDto>> darkSkyFuture = CompletableFuture.supplyAsync(() ->
                darkSkyService.getDirectionWind(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(DARKSKY_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.getDirectionWind(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherBitFuture = CompletableFuture.supplyAsync(() ->
                weatherBitService.getDirectionWind(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERBIT_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherStackFuture = CompletableFuture.supplyAsync(() ->
                weatherStackService.getDirectionWind(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
            list.add(darkSkyFuture.get().getBody());
            list.add(openWeatherMapFuture.get().getBody());
            list.add(weatherBitFuture.get().getBody());
            list.add(weatherStackFuture.get().getBody());
        } catch (InterruptedException | ExecutionException e) {
            logger.error("FullWeatherAggregation problem", e);
            return RESPONSE_FAILED;
        }

        if ("json".equals(ext)) {
            return new ResponseEntity(list, HttpStatus.OK);
        } else {
            WeatherDataArr arrXml = new WeatherDataArr(listToArr(list));
            return new ResponseEntity(arrXml, HttpStatus.OK);
        }
    }

    /**
     * Method WeatherAggregationData returns temperature how does it feel from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    @Override
    @Cacheable("feelsLikeTemperatureAggregation")
    public ResponseEntity<WeatherDataDto> feelsLikeTemperatureAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<ResponseEntity<WeatherDataDto>> darkSkyFuture = CompletableFuture.supplyAsync(() ->
                darkSkyService.getFeelsLikeTemperature(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(DARKSKY_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.getFeelsLikeTemperature(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherBitFuture = CompletableFuture.supplyAsync(() ->
                weatherBitService.getFeelsLikeTemperature(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERBIT_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherStackFuture = CompletableFuture.supplyAsync(() ->
                weatherStackService.getFeelsLikeTemperature(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
            list.add(darkSkyFuture.get().getBody());
            list.add(openWeatherMapFuture.get().getBody());
            list.add(weatherBitFuture.get().getBody());
            list.add(weatherStackFuture.get().getBody());
        } catch (InterruptedException | ExecutionException e) {
            logger.error("FullWeatherAggregation problem", e);
            return RESPONSE_FAILED;
        }

        if ("json".equals(ext)) {
            return new ResponseEntity(list, HttpStatus.OK);
        } else {
            WeatherDataArr arrXml = new WeatherDataArr(listToArr(list));
            return new ResponseEntity(arrXml, HttpStatus.OK);
        }
    }

    /**
     * Method WeatherAggregationData returns weather description from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    @Override
    @Cacheable("weatherDescriptionAggregation")
    public ResponseEntity<WeatherDataDto> weatherDescriptionAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<ResponseEntity<WeatherDataDto>> darkSkyFuture = CompletableFuture.supplyAsync(() ->
                darkSkyService.getWeatherDescription(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(DARKSKY_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.getWeatherDescription(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherBitFuture = CompletableFuture.supplyAsync(() ->
                weatherBitService.getWeatherDescription(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERBIT_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<ResponseEntity<WeatherDataDto>> weatherStackFuture = CompletableFuture.supplyAsync(() ->
                weatherStackService.getWeatherDescription(city, countryCode), ExecutorSingleton.getExecutor())
                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
            list.add(darkSkyFuture.get().getBody());
            list.add(openWeatherMapFuture.get().getBody());
            list.add(weatherBitFuture.get().getBody());
            list.add(weatherStackFuture.get().getBody());
        } catch (InterruptedException | ExecutionException e) {
            logger.error("FullWeatherAggregation problem", e);
            return RESPONSE_FAILED;
        }

        if ("json".equals(ext)) {
            return new ResponseEntity(list, HttpStatus.OK);
        } else {
            WeatherDataArr arrXml = new WeatherDataArr(listToArr(list));
            return new ResponseEntity(arrXml, HttpStatus.OK);
        }
    }

    private WeatherDataDto[] listToArr(ArrayList<WeatherDataDto> list) {
        WeatherDataDto[] arr = new WeatherDataDto[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }
}
