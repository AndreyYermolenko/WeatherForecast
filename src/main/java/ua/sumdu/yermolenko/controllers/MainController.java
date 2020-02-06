package ua.sumdu.yermolenko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.sumdu.yermolenko.services.WeatherAggregationDataService;

/**
 * Class WeatherAggregationDataController provides work with DarkSky API,
 * OpenWeatherMap API, WeatherBit API and WeatherStackAPI.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
@RestController
public class MainController {
    @Autowired
    private WeatherAggregationDataService weatherAggregationDataService;

    /**
     * Method WeatherAggregation the method returns the temperature in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @RequestMapping(path = "/temperature/{countryCode}/{city}", method = RequestMethod.GET)
    public ResponseEntity<?> temperatureAggregation(@PathVariable String countryCode,
                                             @PathVariable String city,
                                             @RequestParam(name = "ext", defaultValue = "json", required = false) String ext) {
        String requestExtension = ext;
        if (!ext.matches("xml|json")) {
            requestExtension = "json";
        }
        return weatherAggregationDataService.temperatureAggregation(city, countryCode, requestExtension);
    }

    /**
     * Method WeatherAggregation the method returns the city coordinates in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @RequestMapping(path = "/cityCoordinates/{countryCode}/{city}", method = RequestMethod.GET)
    public ResponseEntity<?> cityCoordinatesAggregation(@PathVariable String countryCode,
                                                @PathVariable String city,
                                                @RequestParam(name = "ext", defaultValue = "json", required = false) String ext) {
        String requestExtension = ext;
        if (!ext.matches("xml|json")) {
            requestExtension = "json";
        }
        return weatherAggregationDataService.cityCoordinatesAggregation(city, countryCode, requestExtension);
    }

    @RequestMapping(path = "/pressure/{countryCode}/{city}", method = RequestMethod.GET)
    public ResponseEntity<?> pressureAggregation(@PathVariable String countryCode,
                                                    @PathVariable String city,
                                                    @RequestParam(name = "ext", defaultValue = "json", required = false) String ext) {
        String requestExtension = ext;
        if (!ext.matches("xml|json")) {
            requestExtension = "json";
        }
        return weatherAggregationDataService.pressureAggregation(city, countryCode, requestExtension);
    }

    @RequestMapping(path = "/windSpeed/{countryCode}/{city}", method = RequestMethod.GET)
    public ResponseEntity<?> windSpeedAggregation(@PathVariable String countryCode,
                                                 @PathVariable String city,
                                                 @RequestParam(name = "ext", defaultValue = "json", required = false) String ext) {
        String requestExtension = ext;
        if (!ext.matches("xml|json")) {
            requestExtension = "json";
        }
        return weatherAggregationDataService.windSpeedAggregation(city, countryCode, requestExtension);
    }

    @RequestMapping(path = "/humidity/{countryCode}/{city}", method = RequestMethod.GET)
    public ResponseEntity<?> humidityAggregation(@PathVariable String countryCode,
                                                 @PathVariable String city,
                                                 @RequestParam(name = "ext", defaultValue = "json", required = false) String ext) {
        String requestExtension = ext;
        if (!ext.matches("xml|json")) {
            requestExtension = "json";
        }
        return weatherAggregationDataService.humidityAggregation(city, countryCode, requestExtension);
    }

    /**
     * Method WeatherAggregation the method returns the full weather in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @RequestMapping(path = "/fullWeather/{countryCode}/{city}", method = RequestMethod.GET)
    public ResponseEntity<?> fullWeatherAggregation(@PathVariable String countryCode,
                                                    @PathVariable String city,
                                                    @RequestParam(name = "ext", defaultValue = "json", required = false) String ext) {
        String requestExtension = ext;
        if (!ext.matches("xml|json")) {
            requestExtension = "json";
        }
        return weatherAggregationDataService.fullWeatherAggregation(city, countryCode, requestExtension);
    }


    @RequestMapping()
    public ResponseEntity<String> fallbackMethod(){
        return new ResponseEntity<String>("Page not found.", HttpStatus.NOT_FOUND);
    }
}
