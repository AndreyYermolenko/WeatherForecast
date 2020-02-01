package ua.sumdu.yermolenko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.sumdu.yermolenko.services.WeatherAggregationDataService;

/**
 * Class WeatherAggregationDataController provides work with DarkSky API,
 * OpenWeatherMap API, WeatherBit API and WeatherStackAPI.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
@RestController
public class WeatherAggregationDataController {
    @Autowired
    private WeatherAggregationDataService weatherAggregationDataService;

    /**
     * Method WeatherAggregation the method returns the current temperature,
     * pressure and wind speed in a specific city according to the DarkSky API,
     * OpenWeatherMap API, WeatherBit API and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @RequestMapping(path = "/weatherAggregation", method = RequestMethod.GET, params = {"city", "countryCode"})
    public String WeatherAggregation(String city, String countryCode) {
        return weatherAggregationDataService.WeatherAggregationData(city, countryCode);
    }
}
