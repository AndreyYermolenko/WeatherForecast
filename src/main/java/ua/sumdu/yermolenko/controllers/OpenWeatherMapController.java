package ua.sumdu.yermolenko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.sumdu.yermolenko.services.OpenWeatherMapService;

/**
 * Class OpenWeatherMapController provides work with OpenWeatherMap API.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
@RestController
@RequestMapping(path = "/openWeatherMapService")
public class OpenWeatherMapController {
    @Autowired
    private OpenWeatherMapService openWeatherMapService;

    /**
     * Method openWeatherMapTemperature returns the current temperature
     * in a specific city according to the OpenWeatherMap API.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @RequestMapping(path = "/temperature", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String openWeatherMapTemperature(String city, String countryCode) {
        return openWeatherMapService.getTemperatureThread(city, countryCode);
    }

    /**
     * Method openWeatherMapCoordinates returns city coordinates.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @RequestMapping(path = "/coordinates", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String openWeatherMapCoordinates(String city, String countryCode) {
        return openWeatherMapService.getCityCoordinatesThread(city, countryCode);
    }

    /**
     * Method openWeatherMapFullWeather returns the current temperature,
     * pressure and wind speed in a specific city according to the DarkSky API.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @RequestMapping(path = "/fullWeather", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String openWeatherMapFullWeather(String city, String countryCode) {
        return openWeatherMapService.getFullWeatherThread(city, countryCode);
    }
}
