package ua.sumdu.yermolenko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.sumdu.yermolenko.services.interfaces.DarkSkyService;
import ua.sumdu.yermolenko.services.interfaces.OpenWeatherMapService;
import ua.sumdu.yermolenko.services.interfaces.WeatherStackService;

@RestController
public class MainController {
    @Autowired
    private WeatherStackService weatherStack;
    @Autowired
    private DarkSkyService darkSkyService;
    @Autowired
    private OpenWeatherMapService openWeatherMapService;

    @RequestMapping(path = "/weatherStack", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String WeatherStackService(String city, String countryCode) {
        return weatherStack.currentWeather(city, countryCode);
    }

    @RequestMapping(path = "/darkSky", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String DarkSkyService(String city, String countryCode) {
        return darkSkyService.currentWeather(city, countryCode);
    }

    @RequestMapping(path = "/openWeatherMapService", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String OpenWeatherMapService(String city, String countryCode) {
        return openWeatherMapService.currentWeather(city, countryCode);
    }
}