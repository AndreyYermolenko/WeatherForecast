package ua.sumdu.yermolenko.services.impl;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.sumdu.yermolenko.exceptions.WeatherForecastException;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.model.WeatherDataFormat;
import ua.sumdu.yermolenko.model.WeatherDataFormatJson;
import ua.sumdu.yermolenko.model.WeatherDataFormatXml;
import ua.sumdu.yermolenko.services.WeatherAggregationDataService;
import ua.sumdu.yermolenko.services.WeatherService;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static ua.sumdu.yermolenko.constants.ServiceConstants.*;

/**
 * Class WeatherAggregationDataServiceImpl implements interface WeatherAggregationDataService.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
@Service
public class WeatherAggregationDataServiceImpl implements WeatherAggregationDataService {
    private final WeatherDataDto DARKSKY_TIMEOUT =
            new WeatherDataDto(DARKSKY_SERVICENAME,TIMEOUT_EXCEPTION_MESSAGE);

    @Autowired
    private ArrayList<WeatherService> weatherServices;
    private final ExecutorService executor;

    public WeatherAggregationDataServiceImpl(ExecutorService executor) {
        this.executor = executor;
    }


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
    public WeatherDataFormat temperatureAggregation(@NonNull String city,
                                                    @NonNull String countryCode,
                                                    @NonNull String ext) {
        ArrayList<CompletableFuture<WeatherDataDto>> completableFutures = new ArrayList<>();
        ArrayList<WeatherDataDto> weatherDataDtos = new ArrayList<>();

        for(WeatherService service: weatherServices) {
            CompletableFuture<WeatherDataDto> serviceFuture = CompletableFuture.supplyAsync(() ->
                    service.getTemperature(city, countryCode), executor)
                    .completeOnTimeout(DARKSKY_TIMEOUT,
                            API_TIMEOUT,
                            TimeUnit.SECONDS);
            completableFutures.add(serviceFuture);
        }

        for(CompletableFuture<WeatherDataDto> completableFuture: completableFutures) {
            try {
                weatherDataDtos.add(completableFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new WeatherForecastException("TemperatureAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if ("json".equals(ext)) {
            return new WeatherDataFormatJson(weatherDataDtos);
        } else {
            return new WeatherDataFormatXml(weatherDataDtos);
        }
    }

//    /**
//     * Method WeatherAggregationData returns city coordinates from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    @Override
//    @Cacheable("cityCoordinatesAggregation")
//    public WeatherDataFormat cityCoordinatesAggregation(@NonNull String city,
//                                                        @NonNull String countryCode,
//                                                        @NonNull String ext) {
//        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
//                weatherService.getCityCoordinates(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(DARKSKY_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
//                openWeatherMapService.getCityCoordinates(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
//                weatherBitService.getCityCoordinates(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(WEATHERBIT_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
//                weatherStackService.getCityCoordinates(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//
//        ArrayList<WeatherDataDto> list = new ArrayList<>();
//        try {
//            list.add(darkSkyFuture.get());
//            list.add(openWeatherMapFuture.get());
//            list.add(weatherBitFuture.get());
//            list.add(weatherStackFuture.get());
//        } catch (InterruptedException | ExecutionException e) {
//            throw new WeatherForecastException("CityCoordinatesAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        if ("json".equals(ext)) {
//            return new WeatherDataFormatJson(list);
//        } else {
//            return new WeatherDataFormatXml(list);
//        }
//    }
//
//    /**
//     * Method WeatherAggregationData returns pressure data from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    @Override
//    @Cacheable("pressureAggregation")
//    public WeatherDataFormat pressureAggregation(@NonNull String city,
//                                                 @NonNull String countryCode,
//                                                 @NonNull String ext) {
//        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
//                weatherService.getPressure(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(DARKSKY_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
//                openWeatherMapService.getPressure(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
//                weatherBitService.getPressure(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(WEATHERBIT_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
//                weatherStackService.getPressure(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//
//        ArrayList<WeatherDataDto> list = new ArrayList<>();
//        try {
//            list.add(darkSkyFuture.get());
//            list.add(openWeatherMapFuture.get());
//            list.add(weatherBitFuture.get());
//            list.add(weatherStackFuture.get());
//        } catch (InterruptedException | ExecutionException e) {
//            throw new WeatherForecastException("PressureAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        if ("json".equals(ext)) {
//            return new WeatherDataFormatJson(list);
//        } else {
//            return new WeatherDataFormatXml(list);
//        }
//    }
//
//    /**
//     * Method WeatherAggregationData returns wind speed data from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    @Override
//    @Cacheable("windSpeedAggregation")
//    public WeatherDataFormat windSpeedAggregation(@NonNull String city,
//                                                  @NonNull String countryCode,
//                                                  @NonNull String ext) {
//        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
//                weatherService.getWindSpeed(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(DARKSKY_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
//                openWeatherMapService.getWindSpeed(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
//                weatherBitService.getWindSpeed(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(WEATHERBIT_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
//                weatherStackService.getWindSpeed(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//
//        ArrayList<WeatherDataDto> list = new ArrayList<>();
//        try {
//            list.add(darkSkyFuture.get());
//            list.add(openWeatherMapFuture.get());
//            list.add(weatherBitFuture.get());
//            list.add(weatherStackFuture.get());
//        } catch (InterruptedException | ExecutionException e) {
//            throw new WeatherForecastException("WindSpeedAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        if ("json".equals(ext)) {
//            return new WeatherDataFormatJson(list);
//        } else {
//            return new WeatherDataFormatXml(list);
//        }
//    }
//
//    /**
//     * Method WeatherAggregationData returns humidity data from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    @Override
//    @Cacheable("humidityAggregation")
//    public WeatherDataFormat humidityAggregation(@NonNull String city,
//                                                 @NonNull String countryCode,
//                                                 @NonNull String ext) {
//        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
//                weatherService.getHumidity(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(DARKSKY_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
//                openWeatherMapService.getHumidity(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
//                weatherBitService.getHumidity(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(WEATHERBIT_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
//                weatherStackService.getHumidity(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//
//        ArrayList<WeatherDataDto> list = new ArrayList<>();
//        try {
//            list.add(darkSkyFuture.get());
//            list.add(openWeatherMapFuture.get());
//            list.add(weatherBitFuture.get());
//            list.add(weatherStackFuture.get());
//        } catch (InterruptedException | ExecutionException e) {
//            throw new WeatherForecastException("HumidityAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        if ("json".equals(ext)) {
//            return new WeatherDataFormatJson(list);
//        } else {
//            return new WeatherDataFormatXml(list);
//        }
//    }
//
//    /**
//     * Method WeatherAggregationData returns weather data from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    @Override
//    @Cacheable("fullWeatherAggregation")
//    public WeatherDataFormat fullWeatherAggregation(@NonNull String city,
//                                                    @NonNull String countryCode,
//                                                    @NonNull String ext) {
//        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
//        weatherService.getFullWeather(city, countryCode), executorConfig.getExecutor())
//        .completeOnTimeout(DARKSKY_TIMEOUT,
//        API_TIMEOUT,
//        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
//        openWeatherMapService.getFullWeather(city, countryCode), executorConfig.getExecutor())
//        .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
//        API_TIMEOUT,
//        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
//        weatherBitService.getFullWeather(city, countryCode), executorConfig.getExecutor())
//        .completeOnTimeout(WEATHERBIT_TIMEOUT,
//        API_TIMEOUT,
//        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
//        weatherStackService.getFullWeather(city, countryCode), executorConfig.getExecutor())
//        .completeOnTimeout(WEATHERSTACK_TIMEOUT,
//        API_TIMEOUT,
//        TimeUnit.SECONDS);
//
//        ArrayList<WeatherDataDto> list = new ArrayList<>();
//        try {
//        list.add(darkSkyFuture.get());
//        list.add(openWeatherMapFuture.get());
//        list.add(weatherBitFuture.get());
//        list.add(weatherStackFuture.get());
//        } catch (InterruptedException | ExecutionException e) {
//        throw new WeatherForecastException("FullWeatherAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        if ("json".equals(ext)) {
//        return new WeatherDataFormatJson(list);
//        } else {
//        return new WeatherDataFormatXml(list);
//        }
//    }
//
//    /**
//     * Method WeatherAggregationData returns city sunrise time from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    @Override
//    @Cacheable("sunriseTimeAggregation")
//    public WeatherDataFormat sunriseTimeAggregation(@NonNull String city,
//                                                    @NonNull String countryCode,
//                                                    @NonNull String ext) {
//        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
//        weatherService.getSunriseTime(city, countryCode), executorConfig.getExecutor())
//        .completeOnTimeout(DARKSKY_TIMEOUT,
//        API_TIMEOUT,
//        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
//        openWeatherMapService.getSunriseTime(city, countryCode), executorConfig.getExecutor())
//        .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
//        API_TIMEOUT,
//        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
//        weatherBitService.getSunriseTime(city, countryCode), executorConfig.getExecutor())
//        .completeOnTimeout(WEATHERBIT_TIMEOUT,
//        API_TIMEOUT,
//        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
//        weatherStackService.getSunriseTime(city, countryCode), executorConfig.getExecutor())
//        .completeOnTimeout(WEATHERSTACK_TIMEOUT,
//        API_TIMEOUT,
//        TimeUnit.SECONDS);
//
//        ArrayList<WeatherDataDto> list = new ArrayList<>();
//        try {
//        list.add(darkSkyFuture.get());
//        list.add(openWeatherMapFuture.get());
//        list.add(weatherBitFuture.get());
//        list.add(weatherStackFuture.get());
//        } catch (InterruptedException | ExecutionException e) {
//            throw new WeatherForecastException("SunriseTimeAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        if ("json".equals(ext)) {
//        return new WeatherDataFormatJson(list);
//        } else {
//        return new WeatherDataFormatXml(list);
//        }
//    }
//
//    /**
//     * Method WeatherAggregationData returns direction wind from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    @Override
//    @Cacheable("directionWindAggregation")
//    public WeatherDataFormat directionWindAggregation(@NonNull String city,
//                                                      @NonNull String countryCode,
//                                                      @NonNull String ext) {
//        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
//        weatherService.getDirectionWind(city, countryCode), executorConfig.getExecutor())
//        .completeOnTimeout(DARKSKY_TIMEOUT,
//        API_TIMEOUT,
//        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
//        openWeatherMapService.getDirectionWind(city, countryCode), executorConfig.getExecutor())
//        .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
//        API_TIMEOUT,
//        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
//        weatherBitService.getDirectionWind(city, countryCode), executorConfig.getExecutor())
//        .completeOnTimeout(WEATHERBIT_TIMEOUT,
//        API_TIMEOUT,
//        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
//        weatherStackService.getDirectionWind(city, countryCode), executorConfig.getExecutor())
//        .completeOnTimeout(WEATHERSTACK_TIMEOUT,
//        API_TIMEOUT,
//        TimeUnit.SECONDS);
//
//        ArrayList<WeatherDataDto> list = new ArrayList<>();
//        try {
//        list.add(darkSkyFuture.get());
//        list.add(openWeatherMapFuture.get());
//        list.add(weatherBitFuture.get());
//        list.add(weatherStackFuture.get());
//        } catch (InterruptedException | ExecutionException e) {
//            throw new WeatherForecastException("DirectionWindAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        if ("json".equals(ext)) {
//        return new WeatherDataFormatJson(list);
//        } else {
//        return new WeatherDataFormatXml(list);
//        }
//    }
//
//    /**
//     * Method WeatherAggregationData returns temperature how does it feel from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    @Override
//    @Cacheable("feelsLikeTemperatureAggregation")
//    public WeatherDataFormat feelsLikeTemperatureAggregation(@NonNull String city,
//                                                             @NonNull String countryCode,
//                                                             @NonNull String ext) {
//        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
//                weatherService.getFeelsLikeTemperature(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(DARKSKY_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
//                openWeatherMapService.getFeelsLikeTemperature(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
//                weatherBitService.getFeelsLikeTemperature(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(WEATHERBIT_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
//                weatherStackService.getFeelsLikeTemperature(city, countryCode), executorConfig.getExecutor())
//                .completeOnTimeout(WEATHERSTACK_TIMEOUT,
//                        API_TIMEOUT,
//                        TimeUnit.SECONDS);
//
//        ArrayList<WeatherDataDto> list = new ArrayList<>();
//        try {
//            list.add(darkSkyFuture.get());
//            list.add(openWeatherMapFuture.get());
//            list.add(weatherBitFuture.get());
//            list.add(weatherStackFuture.get());
//        } catch (InterruptedException | ExecutionException e) {
//            throw new WeatherForecastException("FeelsLikeTemperatureAggregation problem",
//                    HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        if ("json".equals(ext)) {
//            return new WeatherDataFormatJson(list);
//        } else {
//            return new WeatherDataFormatXml(list);
//        }
//    }
//
//    /**
//     * Method WeatherAggregationData returns weather description from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    @Override
//    @Cacheable("weatherDescriptionAggregation")
//    public WeatherDataFormat weatherDescriptionAggregation(@NonNull String city,
//                                                           @NonNull String countryCode,
//                                                           @NonNull String ext) {
//        CompletableFuture<WeatherDataDto> darkSkyFuture = CompletableFuture.supplyAsync(() ->
//        weatherService.getWeatherDescription(city, countryCode), executorConfig.getExecutor())
//        .completeOnTimeout(DARKSKY_TIMEOUT,
//        API_TIMEOUT,
//        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> openWeatherMapFuture = CompletableFuture.supplyAsync(() ->
//        openWeatherMapService.getWeatherDescription(city, countryCode), executorConfig.getExecutor())
//        .completeOnTimeout(OPENWEATHERMAP_TIMEOUT,
//        API_TIMEOUT,
//        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherBitFuture = CompletableFuture.supplyAsync(() ->
//        weatherBitService.getWeatherDescription(city, countryCode), executorConfig.getExecutor())
//        .completeOnTimeout(WEATHERBIT_TIMEOUT,
//        API_TIMEOUT,
//        TimeUnit.SECONDS);
//        CompletableFuture<WeatherDataDto> weatherStackFuture = CompletableFuture.supplyAsync(() ->
//        weatherStackService.getWeatherDescription(city, countryCode), executorConfig.getExecutor())
//        .completeOnTimeout(WEATHERSTACK_TIMEOUT,
//        API_TIMEOUT,
//        TimeUnit.SECONDS);
//
//        ArrayList<WeatherDataDto> list = new ArrayList<>();
//        try {
//        list.add(darkSkyFuture.get());
//        list.add(openWeatherMapFuture.get());
//        list.add(weatherBitFuture.get());
//        list.add(weatherStackFuture.get());
//        } catch (InterruptedException | ExecutionException e) {
//            throw new WeatherForecastException("WeatherDescriptionAggregation problem",
//                HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        if ("json".equals(ext)) {
//        return new WeatherDataFormatJson(list);
//        } else {
//        return new WeatherDataFormatXml(list);
//        }
//    }
}
