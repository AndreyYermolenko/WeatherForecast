package ua.sumdu.yermolenko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.sumdu.yermolenko.services.WeatherStackService;

/**
 * Class WeatherStackController provides work with WeatherStack API.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
@RestController
@RequestMapping(path = "/weatherStack")
public class WeatherStackController {
    @Autowired
    private WeatherStackService weatherStackService;

    /**
     * Method weatherStackTemperature returns the current temperature
     * in a specific city according to the WeatherStack API.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @RequestMapping(path = "/temperature", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String weatherStackTemperature(String city, String countryCode) {
        return weatherStackService.getTemperatureThread(city, countryCode);
    }

    /**
     * Method weatherStackCoordinates returns city coordinates.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @RequestMapping(path = "/coordinates", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String weatherStackCoordinates(String city, String countryCode) {
        return weatherStackService.getCityCoordinatesThread(city, countryCode);
    }

    /**
     * Method weatherStackFullWeather returns the current temperature,
     * pressure and wind speed in a specific city according to the WeatherStack API.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @RequestMapping(path = "/fullWeather", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String weatherStackFullWeather(String city, String countryCode) {
        return weatherStackService.getFullWeatherThread(city, countryCode);
    }
}
