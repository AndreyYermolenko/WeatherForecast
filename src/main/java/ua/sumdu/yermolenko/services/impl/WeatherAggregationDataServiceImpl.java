package ua.sumdu.yermolenko.services.impl;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.sumdu.yermolenko.exceptions.WeatherForecastException;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.services.WeatherAggregationDataService;
import ua.sumdu.yermolenko.services.WeatherService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Class WeatherAggregationDataServiceImpl implements interface WeatherAggregationDataService.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
@Service
public class WeatherAggregationDataServiceImpl implements WeatherAggregationDataService {
    private final List<WeatherService> weatherServices;
    private final ExecutorService executor;
    private static final String TIMEOUT_EXCEPTION_MESSAGE = "Server response timed out.";
    @Value("${api.timeout}")
    private int API_TIMEOUT;

    public WeatherAggregationDataServiceImpl(List<WeatherService> weatherServices, ExecutorService executor) {
        this.weatherServices = weatherServices;
        this.executor = executor;
    }

    /**
     * Method WeatherAggregationData returns temperature data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    @Override
    @Cacheable("temperatureAggregation")
    public ArrayList<WeatherDataDto> temperatureAggregation(@NonNull String city,
                                                            @NonNull String countryCode) {
        ArrayList<CompletableFuture<WeatherDataDto>> completableFutures = new ArrayList<>();
        ArrayList<WeatherDataDto> weatherDataDtos = new ArrayList<>();

        for(WeatherService service: weatherServices) {
            CompletableFuture<WeatherDataDto> serviceFuture = CompletableFuture.supplyAsync(() ->
                    service.getTemperature(city, countryCode), executor)
                    .completeOnTimeout(new WeatherDataDto(service.getServiceName(),TIMEOUT_EXCEPTION_MESSAGE),
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
        return weatherDataDtos;
    }

    /**
     * Method WeatherAggregationData returns city coordinates from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    @Override
    @Cacheable("cityCoordinatesAggregation")
    public ArrayList<WeatherDataDto> cityCoordinatesAggregation(@NonNull String city,
                                                        @NonNull String countryCode) {
        ArrayList<CompletableFuture<WeatherDataDto>> completableFutures = new ArrayList<>();
        ArrayList<WeatherDataDto> weatherDataDtos = new ArrayList<>();

        for(WeatherService service: weatherServices) {
            CompletableFuture<WeatherDataDto> serviceFuture = CompletableFuture.supplyAsync(() ->
                    service.getCityCoordinates(city, countryCode), executor)
                    .completeOnTimeout(new WeatherDataDto(service.getServiceName(),TIMEOUT_EXCEPTION_MESSAGE),
                            API_TIMEOUT,
                            TimeUnit.SECONDS);
            completableFutures.add(serviceFuture);
        }

        for(CompletableFuture<WeatherDataDto> completableFuture: completableFutures) {
            try {
                weatherDataDtos.add(completableFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new WeatherForecastException("CityCoordinatesAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return weatherDataDtos;
    }

    /**
     * Method WeatherAggregationData returns pressure data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    @Override
    @Cacheable("pressureAggregation")
    public ArrayList<WeatherDataDto> pressureAggregation(@NonNull String city,
                                                 @NonNull String countryCode) {
        ArrayList<CompletableFuture<WeatherDataDto>> completableFutures = new ArrayList<>();
        ArrayList<WeatherDataDto> weatherDataDtos = new ArrayList<>();

        for(WeatherService service: weatherServices) {
            CompletableFuture<WeatherDataDto> serviceFuture = CompletableFuture.supplyAsync(() ->
                    service.getPressure(city, countryCode), executor)
                    .completeOnTimeout(new WeatherDataDto(service.getServiceName(),TIMEOUT_EXCEPTION_MESSAGE),
                            API_TIMEOUT,
                            TimeUnit.SECONDS);
            completableFutures.add(serviceFuture);
        }

        for(CompletableFuture<WeatherDataDto> completableFuture: completableFutures) {
            try {
                weatherDataDtos.add(completableFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new WeatherForecastException("PressureAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return weatherDataDtos;
    }

    /**
     * Method WeatherAggregationData returns wind speed data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    @Override
    @Cacheable("windSpeedAggregation")
    public ArrayList<WeatherDataDto> windSpeedAggregation(@NonNull String city,
                                                  @NonNull String countryCode) {
        ArrayList<CompletableFuture<WeatherDataDto>> completableFutures = new ArrayList<>();
        ArrayList<WeatherDataDto> weatherDataDtos = new ArrayList<>();

        for(WeatherService service: weatherServices) {
            CompletableFuture<WeatherDataDto> serviceFuture = CompletableFuture.supplyAsync(() ->
                    service.getWindSpeed(city, countryCode), executor)
                    .completeOnTimeout(new WeatherDataDto(service.getServiceName(),TIMEOUT_EXCEPTION_MESSAGE),
                            API_TIMEOUT,
                            TimeUnit.SECONDS);
            completableFutures.add(serviceFuture);
        }

        for(CompletableFuture<WeatherDataDto> completableFuture: completableFutures) {
            try {
                weatherDataDtos.add(completableFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new WeatherForecastException("WindSpeedAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return weatherDataDtos;
    }

    /**
     * Method WeatherAggregationData returns humidity data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    @Override
    @Cacheable("humidityAggregation")
    public ArrayList<WeatherDataDto> humidityAggregation(@NonNull String city,
                                                 @NonNull String countryCode) {
        ArrayList<CompletableFuture<WeatherDataDto>> completableFutures = new ArrayList<>();
        ArrayList<WeatherDataDto> weatherDataDtos = new ArrayList<>();

        for(WeatherService service: weatherServices) {
            CompletableFuture<WeatherDataDto> serviceFuture = CompletableFuture.supplyAsync(() ->
                    service.getHumidity(city, countryCode), executor)
                    .completeOnTimeout(new WeatherDataDto(service.getServiceName(),TIMEOUT_EXCEPTION_MESSAGE),
                            API_TIMEOUT,
                            TimeUnit.SECONDS);
            completableFutures.add(serviceFuture);
        }

        for(CompletableFuture<WeatherDataDto> completableFuture: completableFutures) {
            try {
                weatherDataDtos.add(completableFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new WeatherForecastException("HumidityAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return weatherDataDtos;
    }

    /**
     * Method WeatherAggregationData returns weather data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    @Override
    @Cacheable("fullWeatherAggregation")
    public ArrayList<WeatherDataDto> fullWeatherAggregation(@NonNull String city,
                                                    @NonNull String countryCode) {
        ArrayList<CompletableFuture<WeatherDataDto>> completableFutures = new ArrayList<>();
        ArrayList<WeatherDataDto> weatherDataDtos = new ArrayList<>();

        for(WeatherService service: weatherServices) {
            CompletableFuture<WeatherDataDto> serviceFuture = CompletableFuture.supplyAsync(() ->
                    service.getFullWeather(city, countryCode), executor)
                    .completeOnTimeout(new WeatherDataDto(service.getServiceName(),TIMEOUT_EXCEPTION_MESSAGE),
                            API_TIMEOUT,
                            TimeUnit.SECONDS);
            completableFutures.add(serviceFuture);
        }

        for(CompletableFuture<WeatherDataDto> completableFuture: completableFutures) {
            try {
                weatherDataDtos.add(completableFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new WeatherForecastException("FullWeather problem", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return weatherDataDtos;
    }

    /**
     * Method WeatherAggregationData returns city sunrise time from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    @Override
    @Cacheable("sunriseTimeAggregation")
    public ArrayList<WeatherDataDto> sunriseTimeAggregation(@NonNull String city,
                                                    @NonNull String countryCode) {
        ArrayList<CompletableFuture<WeatherDataDto>> completableFutures = new ArrayList<>();
        ArrayList<WeatherDataDto> weatherDataDtos = new ArrayList<>();

        for(WeatherService service: weatherServices) {
            CompletableFuture<WeatherDataDto> serviceFuture = CompletableFuture.supplyAsync(() ->
                    service.getSunriseTime(city, countryCode), executor)
                    .completeOnTimeout(new WeatherDataDto(service.getServiceName(),TIMEOUT_EXCEPTION_MESSAGE),
                            API_TIMEOUT,
                            TimeUnit.SECONDS);
            completableFutures.add(serviceFuture);
        }

        for(CompletableFuture<WeatherDataDto> completableFuture: completableFutures) {
            try {
                weatherDataDtos.add(completableFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new WeatherForecastException("SunriseTimeAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return weatherDataDtos;
    }

    /**
     * Method WeatherAggregationData returns direction wind from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    @Override
    @Cacheable("directionWindAggregation")
    public ArrayList<WeatherDataDto> directionWindAggregation(@NonNull String city,
                                                      @NonNull String countryCode) {
        ArrayList<CompletableFuture<WeatherDataDto>> completableFutures = new ArrayList<>();
        ArrayList<WeatherDataDto> weatherDataDtos = new ArrayList<>();

        for(WeatherService service: weatherServices) {
            CompletableFuture<WeatherDataDto> serviceFuture = CompletableFuture.supplyAsync(() ->
                    service.getDirectionWind(city, countryCode), executor)
                    .completeOnTimeout(new WeatherDataDto(service.getServiceName(),TIMEOUT_EXCEPTION_MESSAGE),
                            API_TIMEOUT,
                            TimeUnit.SECONDS);
            completableFutures.add(serviceFuture);
        }

        for(CompletableFuture<WeatherDataDto> completableFuture: completableFutures) {
            try {
                weatherDataDtos.add(completableFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new WeatherForecastException("DirectionWindAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return weatherDataDtos;
    }

    /**
     * Method WeatherAggregationData returns temperature how does it feel from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    @Override
    @Cacheable("feelsLikeTemperatureAggregation")
    public ArrayList<WeatherDataDto> feelsLikeTemperatureAggregation(@NonNull String city,
                                                             @NonNull String countryCode) {
        ArrayList<CompletableFuture<WeatherDataDto>> completableFutures = new ArrayList<>();
        ArrayList<WeatherDataDto> weatherDataDtos = new ArrayList<>();

        for(WeatherService service: weatherServices) {
            CompletableFuture<WeatherDataDto> serviceFuture = CompletableFuture.supplyAsync(() ->
                    service.getFeelsLikeTemperature(city, countryCode), executor)
                    .completeOnTimeout(new WeatherDataDto(service.getServiceName(),TIMEOUT_EXCEPTION_MESSAGE),
                            API_TIMEOUT,
                            TimeUnit.SECONDS);
            completableFutures.add(serviceFuture);
        }

        for(CompletableFuture<WeatherDataDto> completableFuture: completableFutures) {
            try {
                weatherDataDtos.add(completableFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new WeatherForecastException("FeelsLikeTemperatureAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return weatherDataDtos;
    }

    /**
     * Method WeatherAggregationData returns weather description from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    @Override
    @Cacheable("weatherDescriptionAggregation")
    public ArrayList<WeatherDataDto> weatherDescriptionAggregation(@NonNull String city,
                                                           @NonNull String countryCode) {
        ArrayList<CompletableFuture<WeatherDataDto>> completableFutures = new ArrayList<>();
        ArrayList<WeatherDataDto> weatherDataDtos = new ArrayList<>();

        for(WeatherService service: weatherServices) {
            CompletableFuture<WeatherDataDto> serviceFuture = CompletableFuture.supplyAsync(() ->
                    service.getWeatherDescription(city, countryCode), executor)
                    .completeOnTimeout(new WeatherDataDto(service.getServiceName(),TIMEOUT_EXCEPTION_MESSAGE),
                            API_TIMEOUT,
                            TimeUnit.SECONDS);
            completableFutures.add(serviceFuture);
        }

        for(CompletableFuture<WeatherDataDto> completableFuture: completableFutures) {
            try {
                weatherDataDtos.add(completableFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new WeatherForecastException("WeatherDescriptionAggregation problem", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return weatherDataDtos;
    }
}
