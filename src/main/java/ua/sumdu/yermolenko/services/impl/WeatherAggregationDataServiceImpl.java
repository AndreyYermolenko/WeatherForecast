package ua.sumdu.yermolenko.services.impl;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ua.sumdu.yermolenko.config.ExecutorSingleton;
import ua.sumdu.yermolenko.services.*;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Class WeatherAggregationDataServiceImpl implements interface WeatherAggregationDataService.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
@Service
public class WeatherAggregationDataServiceImpl implements WeatherAggregationDataService {
    private final static Logger logger = LogManager.getLogger(WeatherAggregationDataServiceImpl.class);
    @Autowired
    private DarkSkyService darkSkyService;
    @Autowired
    private OpenWeatherMapService openWeatherMapService;
    @Autowired
    private WeatherBitService weatherBitService;
    @Autowired
    private WeatherStackService weatherStackService;
    @Value("${api.timeout}")
    private int apiTimeout;

    /**
     * Method WeatherAggregationData returns weather data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Override
    @Cacheable("weatherAggregationData")
    public String WeatherAggregationData(@NonNull String city, @NonNull String countryCode) {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() ->
                darkSkyService.getFullWeather(city, countryCode), ExecutorSingleton.getExecutor()
        ).completeOnTimeout("Превышено время ожидания ответа от сервера.", apiTimeout, TimeUnit.SECONDS);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.getFullWeather(city, countryCode), ExecutorSingleton.getExecutor()
        ).completeOnTimeout("Превышено время ожидания ответа от сервера.", apiTimeout, TimeUnit.SECONDS);
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() ->
                weatherBitService.getFullWeather(city, countryCode), ExecutorSingleton.getExecutor()
        ).completeOnTimeout("Превышено время ожидания ответа от сервера.", apiTimeout, TimeUnit.SECONDS);
        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() ->
                weatherStackService.getFullWeather(city, countryCode), ExecutorSingleton.getExecutor()
        ).completeOnTimeout("Превышено время ожидания ответа от сервера.", apiTimeout, TimeUnit.SECONDS);

        ArrayList<String> list = new ArrayList<>();
        try {
            list.add(future1.get());
            list.add(future2.get());
            list.add(future3.get());
            list.add(future4.get());
        } catch (InterruptedException | ExecutionException e) {
            logger.error("WeatherAggregationDataService problem", e);
            return "Request Failed. Server error.";
        }

        StringBuilder resultJson = new StringBuilder();
        resultJson.append("{");
        resultJson.append(list.get(0), 1, list.get(0).length() - 1);
        for (int i = 1; i < list.size(); i++) {
            resultJson.append(",");
            resultJson.append(list.get(i), 1, list.get(i).length() - 1);
        }
        resultJson.append("}");
        return resultJson.toString();
    }
}
