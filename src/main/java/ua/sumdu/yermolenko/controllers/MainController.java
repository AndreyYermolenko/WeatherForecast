package ua.sumdu.yermolenko.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.sumdu.yermolenko.exceptions.WeatherForecastException;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.services.WeatherAggregationDataService;

import java.util.ArrayList;

/**
 * Class WeatherAggregationDataController provides work with DarkSky API,
 * OpenWeatherMap API, WeatherBit API and WeatherStackAPI.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
@RestController
public class MainController {
    private final static Logger LOGGER = LogManager.getLogger(MainController.class);
    private final WeatherAggregationDataService weatherAggregationDataService;

    public MainController(WeatherAggregationDataService weatherAggregationDataService) {
        this.weatherAggregationDataService = weatherAggregationDataService;
    }

    /**
     * Method WeatherAggregation the method returns the temperature data in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param countryCode of type String
     * @param city of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/temperature/{countryCode}/{city}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> temperatureAggregation(@PathVariable String countryCode,
                                                                   @PathVariable String city) {
        try {
            ArrayList<WeatherDataDto> weatherDataJson = weatherAggregationDataService
                    .temperatureAggregation(city, countryCode);
            return new ResponseEntity<>(weatherDataJson, HttpStatus.OK);
        } catch (WeatherForecastException e) {
            LOGGER.error(e);
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the city coordinates in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/cityCoordinates/{countryCode}/{city}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> cityCoordinatesAggregation(@PathVariable String countryCode,
                                                                     @PathVariable String city) {
        try {
            ArrayList<WeatherDataDto> weatherDataJson = weatherAggregationDataService
                    .cityCoordinatesAggregation(city, countryCode);
            return new ResponseEntity<>(weatherDataJson, HttpStatus.OK);
        } catch (WeatherForecastException e) {
            LOGGER.error(e);
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the pressure data in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/pressure/{countryCode}/{city}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> pressureAggregation(@PathVariable String countryCode,
                                                              @PathVariable String city) {
        try {
            ArrayList<WeatherDataDto> weatherDataJson = weatherAggregationDataService
                    .pressureAggregation(city, countryCode);
            return new ResponseEntity<>(weatherDataJson, HttpStatus.OK);
        } catch (WeatherForecastException e) {
            LOGGER.error(e);
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the wind speed data in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/windSpeed/{countryCode}/{city}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> windSpeedAggregation(@PathVariable String countryCode,
                                                               @PathVariable String city) {
        try {
            ArrayList<WeatherDataDto> weatherDataJson = weatherAggregationDataService
                    .windSpeedAggregation(city, countryCode);
            return new ResponseEntity<>(weatherDataJson, HttpStatus.OK);
        } catch (WeatherForecastException e) {
            LOGGER.error(e);
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the humidity data in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/humidity/{countryCode}/{city}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> humidityAggregation(@PathVariable String countryCode,
                                                              @PathVariable String city) {
        try {
            ArrayList<WeatherDataDto> weatherDataJson = weatherAggregationDataService
                    .humidityAggregation(city, countryCode);
            return new ResponseEntity<>(weatherDataJson, HttpStatus.OK);
        } catch (WeatherForecastException e) {
            LOGGER.error(e);
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the full weather in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/fullWeather/{countryCode}/{city}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> fullWeatherAggregation(@PathVariable String countryCode,
                                                                 @PathVariable String city) {
        try {
            ArrayList<WeatherDataDto> weatherDataJson = weatherAggregationDataService
                    .fullWeatherAggregation(city, countryCode);
            return new ResponseEntity<>(weatherDataJson, HttpStatus.OK);
        } catch (WeatherForecastException e) {
            LOGGER.error(e);
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the sunrise time in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/sunriseTime/{countryCode}/{city}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> sunriseTimeAggregation(@PathVariable String countryCode,
                                                                 @PathVariable String city) {
        try {
            ArrayList<WeatherDataDto> weatherDataJson = weatherAggregationDataService
                    .sunriseTimeAggregation(city, countryCode);
            return new ResponseEntity<>(weatherDataJson, HttpStatus.OK);
        } catch (WeatherForecastException e) {
            LOGGER.error(e);
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the direction wind data in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/directionWind/{countryCode}/{city}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> directionWindAggregation(@PathVariable String countryCode,
                                                                   @PathVariable String city) {
        try {
            ArrayList<WeatherDataDto> weatherDataJson = weatherAggregationDataService
                    .directionWindAggregation(city, countryCode);
            return new ResponseEntity<>(weatherDataJson, HttpStatus.OK);
        } catch (WeatherForecastException e) {
            LOGGER.error(e);
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the temperature how does it feel in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/feelsLikeTemperature/{countryCode}/{city}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> feelsLikeTemperatureAggregation(@PathVariable String countryCode,
                                                                          @PathVariable String city) {
        try {
            ArrayList<WeatherDataDto> weatherDataJson = weatherAggregationDataService
                    .feelsLikeTemperatureAggregation(city, countryCode);
            return new ResponseEntity<>(weatherDataJson, HttpStatus.OK);
        } catch (WeatherForecastException e) {
            LOGGER.error(e);
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the weather description in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/weatherDescription/{countryCode}/{city}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> weatherDescriptionAggregation(@PathVariable String countryCode,
                                                                        @PathVariable String city) {
        try {
            ArrayList<WeatherDataDto> weatherDataJson = weatherAggregationDataService
                    .weatherDescriptionAggregation(city, countryCode);
            return new ResponseEntity<>(weatherDataJson, HttpStatus.OK);
        } catch (WeatherForecastException e) {
            LOGGER.error(e);
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method fallbackMethod is called when a nonexistent URI is specified.
     * @return ResponseEntity<String>
     */
    @RequestMapping()
    public ResponseEntity<String> fallbackMethod(){
        return new ResponseEntity<String>("Page not found.", HttpStatus.NOT_FOUND);
    }
}
