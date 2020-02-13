package ua.sumdu.yermolenko.services.impl;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.sumdu.yermolenko.config.ExecutorSingleton;
import ua.sumdu.yermolenko.model.WeatherDataFormat;
import ua.sumdu.yermolenko.model.WeatherDataJson;
import ua.sumdu.yermolenko.model.WeatherDataXml;
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

    private final WeatherDataDto DARKSKY_TIMEOUT = new WeatherDataDto(DARKSKY_SERVICENAME,TIMEOUT_EXCEPTION_MESSAGE);
    private final WeatherDataDto OPENWEATHERMAP_TIMEOUT = new WeatherDataDto(OPENWEATHERMAP_SERVICENAME,TIMEOUT_EXCEPTION_MESSAGE);
    private final WeatherDataDto WEATHERBIT_TIMEOUT = new WeatherDataDto(WEATHERBIT_SERVICENAME,TIMEOUT_EXCEPTION_MESSAGE);
    private final WeatherDataDto WEATHERSTACK_TIMEOUT = new WeatherDataDto(WEATHERSTACK_SERVICENAME,TIMEOUT_EXCEPTION_MESSAGE);

    @Autowired
    private DarkSkyService darkSkyService;
    @Autowired
    private OpenWeatherMapService openWeatherMapService;
    @Autowired
    private WeatherBitService weatherBitService;
    @Autowired
    private WeatherStackService weatherStackService;
    @Autowired
    private ExecutorSingleton executorSingleton;

    /**
     * Method WeatherAggregationData returns temperature data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return WeatherDataFormat
     */
    @Override
    @Cacheable("temperatureAggregation")
    public WeatherDataFormat temperatureAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
                darkSkyService.getTemperature(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(DARKSKY_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.getTemperature(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
                weatherBitService.getTemperature(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(WEATHERBIT_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
                weatherStackService.getTemperature(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
            list.add(darkSkyFuture.get());
            list.add(openWeatherMapFuture.get());
            list.add(weatherBitFuture.get());
            list.add(weatherStackFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new WeatherForecastException("TemperatureAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if ("json".equals(ext)) {
            return new WeatherDataJson(list);
        } else {
            return new WeatherDataXml(list);
        }
    }

    /**
     * Method WeatherAggregationData returns city coordinates from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return WeatherDataFormat
     */
    @Override
    @Cacheable("cityCoordinatesAggregation")
    public WeatherDataFormat cityCoordinatesAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
                darkSkyService.getCityCoordinates(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(DARKSKY_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.getCityCoordinates(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
                weatherBitService.getCityCoordinates(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(WEATHERBIT_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
                weatherStackService.getCityCoordinates(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
            list.add(darkSkyFuture.get());
            list.add(openWeatherMapFuture.get());
            list.add(weatherBitFuture.get());
            list.add(weatherStackFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new WeatherForecastException("CityCoordinatesAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if ("json".equals(ext)) {
            return new WeatherDataJson(list);
        } else {
            return new WeatherDataXml(list);
        }
    }

    /**
     * Method WeatherAggregationData returns pressure data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return WeatherDataFormat
     */
    @Override
    @Cacheable("pressureAggregation")
    public WeatherDataFormat pressureAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
                darkSkyService.getPressure(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(DARKSKY_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.getPressure(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
                weatherBitService.getPressure(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(WEATHERBIT_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
                weatherStackService.getPressure(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
            list.add(darkSkyFuture.get());
            list.add(openWeatherMapFuture.get());
            list.add(weatherBitFuture.get());
            list.add(weatherStackFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new WeatherForecastException("PressureAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if ("json".equals(ext)) {
            return new WeatherDataJson(list);
        } else {
            return new WeatherDataXml(list);
        }
    }

    /**
     * Method WeatherAggregationData returns wind speed data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return WeatherDataFormat
     */
    @Override
    @Cacheable("windSpeedAggregation")
    public WeatherDataFormat windSpeedAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
                darkSkyService.getWindSpeed(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(DARKSKY_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.getWindSpeed(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
                weatherBitService.getWindSpeed(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(WEATHERBIT_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
                weatherStackService.getWindSpeed(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
            list.add(darkSkyFuture.get());
            list.add(openWeatherMapFuture.get());
            list.add(weatherBitFuture.get());
            list.add(weatherStackFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new WeatherForecastException("WindSpeedAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if ("json".equals(ext)) {
            return new WeatherDataJson(list);
        } else {
            return new WeatherDataXml(list);
        }
    }

    /**
     * Method WeatherAggregationData returns humidity data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return WeatherDataFormat
     */
    @Override
    @Cacheable("humidityAggregation")
    public WeatherDataFormat humidityAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
                darkSkyService.getHumidity(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(DARKSKY_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.getHumidity(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
                weatherBitService.getHumidity(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(WEATHERBIT_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
                weatherStackService.getHumidity(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
            list.add(darkSkyFuture.get());
            list.add(openWeatherMapFuture.get());
            list.add(weatherBitFuture.get());
            list.add(weatherStackFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new WeatherForecastException("HumidityAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if ("json".equals(ext)) {
            return new WeatherDataJson(list);
        } else {
            return new WeatherDataXml(list);
        }
    }

    /**
     * Method WeatherAggregationData returns weather data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return WeatherDataFormat
     */
    @Override
    @Cacheable("fullWeatherAggregation")
    public WeatherDataFormat fullWeatherAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
        darkSkyService.getFullWeather(city, countryCode), executorSingleton.getExecutor())
        .completeOnTimeout(DARKSKY_TIMEOUT,
        API_TIMEOUT,
        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
        openWeatherMapService.getFullWeather(city, countryCode), executorSingleton.getExecutor())
        .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
        API_TIMEOUT,
        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
        weatherBitService.getFullWeather(city, countryCode), executorSingleton.getExecutor())
        .completeOnTimeout(WEATHERBIT_TIMEOUT,
        API_TIMEOUT,
        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
        weatherStackService.getFullWeather(city, countryCode), executorSingleton.getExecutor())
        .completeOnTimeout(WEATHERSTACK_TIMEOUT,
        API_TIMEOUT,
        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
        list.add(darkSkyFuture.get());
        list.add(openWeatherMapFuture.get());
        list.add(weatherBitFuture.get());
        list.add(weatherStackFuture.get());
        } catch (InterruptedException | ExecutionException e) {
        throw new WeatherForecastException("FullWeatherAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if ("json".equals(ext)) {
        return new WeatherDataJson(list);
        } else {
        return new WeatherDataXml(list);
        }
    }

    /**
     * Method WeatherAggregationData returns city sunrise time from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return WeatherDataFormat
     */
    @Override
    @Cacheable("sunriseTimeAggregation")
    public WeatherDataFormat sunriseTimeAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
        darkSkyService.getSunriseTime(city, countryCode), executorSingleton.getExecutor())
        .completeOnTimeout(DARKSKY_TIMEOUT,
        API_TIMEOUT,
        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
        openWeatherMapService.getSunriseTime(city, countryCode), executorSingleton.getExecutor())
        .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
        API_TIMEOUT,
        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
        weatherBitService.getSunriseTime(city, countryCode), executorSingleton.getExecutor())
        .completeOnTimeout(WEATHERBIT_TIMEOUT,
        API_TIMEOUT,
        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
        weatherStackService.getSunriseTime(city, countryCode), executorSingleton.getExecutor())
        .completeOnTimeout(WEATHERSTACK_TIMEOUT,
        API_TIMEOUT,
        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
        list.add(darkSkyFuture.get());
        list.add(openWeatherMapFuture.get());
        list.add(weatherBitFuture.get());
        list.add(weatherStackFuture.get());
        } catch (InterruptedException | ExecutionException e) {
        throw new WeatherForecastException("SunriseTimeAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if ("json".equals(ext)) {
        return new WeatherDataJson(list);
        } else {
        return new WeatherDataXml(list);
        }
    }

    /**
     * Method WeatherAggregationData returns direction wind from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return WeatherDataFormat
     */
    @Override
    @Cacheable("directionWindAggregation")
    public WeatherDataFormat directionWindAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
        darkSkyService.getDirectionWind(city, countryCode), executorSingleton.getExecutor())
        .completeOnTimeout(DARKSKY_TIMEOUT,
        API_TIMEOUT,
        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
        openWeatherMapService.getDirectionWind(city, countryCode), executorSingleton.getExecutor())
        .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
        API_TIMEOUT,
        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
        weatherBitService.getDirectionWind(city, countryCode), executorSingleton.getExecutor())
        .completeOnTimeout(WEATHERBIT_TIMEOUT,
        API_TIMEOUT,
        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
        weatherStackService.getDirectionWind(city, countryCode), executorSingleton.getExecutor())
        .completeOnTimeout(WEATHERSTACK_TIMEOUT,
        API_TIMEOUT,
        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
        list.add(darkSkyFuture.get());
        list.add(openWeatherMapFuture.get());
        list.add(weatherBitFuture.get());
        list.add(weatherStackFuture.get());
        } catch (InterruptedException | ExecutionException e) {
        throw new WeatherForecastException("DirectionWindAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if ("json".equals(ext)) {
        return new WeatherDataJson(list);
        } else {
        return new WeatherDataXml(list);
        }
    }

    /**
     * Method WeatherAggregationData returns temperature how does it feel from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return WeatherDataFormat
     */
    @Override
    @Cacheable("feelsLikeTemperatureAggregation")
    public WeatherDataFormat feelsLikeTemperatureAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
                darkSkyService.getFeelsLikeTemperature(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(DARKSKY_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.getFeelsLikeTemperature(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
                weatherBitService.getFeelsLikeTemperature(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(WEATHERBIT_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
                weatherStackService.getFeelsLikeTemperature(city, countryCode), executorSingleton.getExecutor())
                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
                        API_TIMEOUT,
                        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
            list.add(darkSkyFuture.get());
            list.add(openWeatherMapFuture.get());
            list.add(weatherBitFuture.get());
            list.add(weatherStackFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new WeatherForecastException("FeelsLikeTemperatureAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if ("json".equals(ext)) {
            return new WeatherDataJson(list);
        } else {
            return new WeatherDataXml(list);
        }
    }

    /**
     * Method WeatherAggregationData returns weather description from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return WeatherDataFormat
     */
    @Override
    @Cacheable("weatherDescriptionAggregation")
    public WeatherDataFormat weatherDescriptionAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext) {
        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
        darkSkyService.getWeatherDescription(city, countryCode), executorSingleton.getExecutor())
        .completeOnTimeout(DARKSKY_TIMEOUT,
        API_TIMEOUT,
        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
        openWeatherMapService.getWeatherDescription(city, countryCode), executorSingleton.getExecutor())
        .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
        API_TIMEOUT,
        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
        weatherBitService.getWeatherDescription(city, countryCode), executorSingleton.getExecutor())
        .completeOnTimeout(WEATHERBIT_TIMEOUT,
        API_TIMEOUT,
        TimeUnit.SECONDS);
        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
        weatherStackService.getWeatherDescription(city, countryCode), executorSingleton.getExecutor())
        .completeOnTimeout(WEATHERSTACK_TIMEOUT,
        API_TIMEOUT,
        TimeUnit.SECONDS);

        ArrayList<WeatherDataDto> list = new ArrayList<>();
        try {
        list.add(darkSkyFuture.get());
        list.add(openWeatherMapFuture.get());
        list.add(weatherBitFuture.get());
        list.add(weatherStackFuture.get());
        } catch (InterruptedException | ExecutionException e) {
        throw new WeatherForecastException("WeatherDescriptionAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if ("json".equals(ext)) {
        return new WeatherDataJson(list);
        } else {
        return new WeatherDataXml(list);
        }
    }
}
