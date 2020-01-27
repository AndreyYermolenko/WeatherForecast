package ua.sumdu.yermolenko.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ua.sumdu.yermolenko.services.interfaces.DarkSkyService;
import ua.sumdu.yermolenko.services.interfaces.OpenWeatherMapService;
import ua.sumdu.yermolenko.services.interfaces.WeatherBitService;
import ua.sumdu.yermolenko.services.interfaces.WeatherStackService;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class WeatherAggregationDataService {
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

    @Cacheable("weatherAggregationData")
    public String WeatherAggregationData(@NonNull String city, @NonNull String countryCode) {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() ->
                darkSkyService.currentWeather(city, countryCode), ExecutorSingleton.getExecutor()
        ).completeOnTimeout("Превышено время ожидания ответа от сервера.", apiTimeout, TimeUnit.SECONDS);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() ->
                openWeatherMapService.currentWeather(city, countryCode), ExecutorSingleton.getExecutor()
        ).completeOnTimeout("Превышено время ожидания ответа от сервера.", apiTimeout, TimeUnit.SECONDS);
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() ->
                weatherBitService.currentWeather(city, countryCode), ExecutorSingleton.getExecutor()
        ).completeOnTimeout("Превышено время ожидания ответа от сервера.", apiTimeout, TimeUnit.SECONDS);
        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() ->
                weatherStackService.currentWeather(city, countryCode), ExecutorSingleton.getExecutor()
        ).completeOnTimeout("Превышено время ожидания ответа от сервера.", apiTimeout, TimeUnit.SECONDS);

        ArrayList<String> list = new ArrayList<>();
        try {
            list.add(future1.get());
            list.add(future2.get());
            list.add(future3.get());
            list.add(future4.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
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
