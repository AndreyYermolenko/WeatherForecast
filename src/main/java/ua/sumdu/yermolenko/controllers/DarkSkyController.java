package ua.sumdu.yermolenko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.sumdu.yermolenko.services.DarkSkyService;

/**
 * Class DarkSkyController provides work with DarkSky API.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
@RestController
@RequestMapping(path = "/darkSky")
public class DarkSkyController {
    @Autowired
    private DarkSkyService darkSkyService;

    /**
     * Method darkSkyTemperature returns the current temperature
     * in a specific city according to the DarkSky API.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @RequestMapping(path = "/temperature", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String darkSkyTemperature(String city, String countryCode) {
        return darkSkyService.getTemperatureThread(city, countryCode);
    }

    /**
     * Method darkSkyCoordinates returns city coordinates.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @RequestMapping(path = "/coordinates", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String darkSkyCoordinates(String city, String countryCode) {
        return darkSkyService.getCityCoordinatesThread(city, countryCode);
    }

    /**
     * Method darkSkyFullWeather returns the current temperature,
     * pressure and wind speed in a specific city according to the DarkSky API.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @RequestMapping(path = "/fullWeather", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String darkSkyFullWeather(String city, String countryCode) {
        return darkSkyService.getFullWeatherThread(city, countryCode);
    }
}
