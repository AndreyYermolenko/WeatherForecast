package ua.sumdu.yermolenko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.sumdu.yermolenko.services.WeatherAggregationDataService;
import ua.sumdu.yermolenko.services.interfaces.DarkSkyService;
import ua.sumdu.yermolenko.services.interfaces.OpenWeatherMapService;
import ua.sumdu.yermolenko.services.interfaces.WeatherBitService;
import ua.sumdu.yermolenko.services.interfaces.WeatherStackService;

@RestController
public class MainController {
    @Autowired
    private DarkSkyService darkSkyService;
    @Autowired
    private OpenWeatherMapService openWeatherMapService;
    @Autowired
    private WeatherBitService weatherBitService;
    @Autowired
    private WeatherStackService weatherStackService;
    @Autowired
    private WeatherAggregationDataService weatherAggregationDataService;

    @RequestMapping(path = "/darkSky", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String DarkSkyService(String city, String countryCode) {
        return darkSkyService.darkSkyCurrentWeatherThread(city, countryCode);
    }

    @RequestMapping(path = "/openWeatherMapService", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String OpenWeatherMapService(String city, String countryCode) {
        return openWeatherMapService.openWeatherMapCurrentWeatherThread(city, countryCode);
    }

    @RequestMapping(path = "/weatherBit", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String WeatherBitService(String city, String countryCode) {
        return weatherBitService.weatherBitCurrentWeatherThread(city, countryCode);
    }

    @RequestMapping(path = "/weatherStack", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String WeatherStackService(String city, String countryCode) {
        return weatherStackService.weatherStackCurrentWeatherThread(city, countryCode);
    }

    @RequestMapping(path = "/weatherAggregation", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String WeatherAggregation(String city, String countryCode) {
        return weatherAggregationDataService.WeatherAggregationData(city, countryCode);
    }
}
