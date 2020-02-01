package ua.sumdu.yermolenko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.sumdu.yermolenko.services.WeatherBitService;

/**
 * Class WeatherBitController provides work with WeatherBit API.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
@RestController
@RequestMapping(path = "/weatherBit")
public class WeatherBitController {
    @Autowired
    private WeatherBitService weatherBitService;

    /**
     * Method weatherBitTemperature returns the current temperature
     * in a specific city according to the WeatherBit API.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @RequestMapping(path = "/temperature", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String weatherBitTemperature(String city, String countryCode) {
        return weatherBitService.getTemperatureThread(city, countryCode);
    }

    /**
     * Method weatherBitCoordinates returns city coordinates.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @RequestMapping(path = "/coordinates", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String weatherBitCoordinates(String city, String countryCode) {
        return weatherBitService.getCityCoordinatesThread(city, countryCode);
    }

    /**
     * Method weatherBitFullWeather returns the current temperature,
     * pressure and wind speed in a specific city according to the WeatherBit API.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @RequestMapping(path = "/fullWeather", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String weatherBitFullWeather(String city, String countryCode) {
        return weatherBitService.getFullWeatherThread(city, countryCode);
    }
}
