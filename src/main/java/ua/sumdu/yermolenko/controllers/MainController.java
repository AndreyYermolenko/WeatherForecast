package ua.sumdu.yermolenko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.sumdu.yermolenko.services.WeatherAggregationDataService;
import ua.sumdu.yermolenko.services.interfaces.DarkSkyService;
import ua.sumdu.yermolenko.services.interfaces.WeatherBitService;
import ua.sumdu.yermolenko.services.interfaces.OpenWeatherMapService;
import ua.sumdu.yermolenko.services.interfaces.WeatherStackService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@EnableAsync
public class MainController {
    @Autowired
    private WeatherStackService weatherStack;
    @Autowired
    private DarkSkyService darkSkyService;
    @Autowired
    private OpenWeatherMapService openWeatherMapService;
    @Autowired
    private WeatherBitService weatherBitService;
    @Autowired
    private WeatherAggregationDataService weatherAggregationDataService;

    @RequestMapping(path = "/weatherStack", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String WeatherStackService(String city, String countryCode) {
        try {
            return weatherStack.currentWeather(city, countryCode).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(path = "/darkSky", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String DarkSkyService(String city, String countryCode) {
        try {
            return darkSkyService.currentWeather(city, countryCode).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(path = "/openWeatherMapService", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String OpenWeatherMapService(String city, String countryCode) {
        try {
            return openWeatherMapService.currentWeather(city, countryCode).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(path = "/weatherBit", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String WeatherBitService(String city, String countryCode) {
        try {
            return weatherBitService.currentWeather(city, countryCode).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(path = "/weatherAggregation", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String WeatherAggregation(String city, String countryCode) {
        String[] weatherData = new String[4];
        Future<String> weatherData1 = weatherStack.currentWeather(city, countryCode);
        Future<String> weatherData2 = darkSkyService.currentWeather(city, countryCode);
        Future<String> weatherData3 = openWeatherMapService.currentWeather(city, countryCode);
        Future<String> weatherData4 = weatherBitService.currentWeather(city, countryCode);

        try {
            weatherData[0] = weatherData1.get();
            weatherData[1] = weatherData2.get();
            weatherData[2] = weatherData3.get();
            weatherData[3] = weatherData4.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        while (!(weatherData1.isDone()
                && weatherData2.isDone()
                && weatherData3.isDone()
                && weatherData4.isDone())) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return weatherAggregationDataService.WeatherAggregationData(weatherData);
    }
}
