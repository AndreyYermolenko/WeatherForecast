package ua.sumdu.yermolenko.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.sumdu.yermolenko.model.WeatherDataJson;
import ua.sumdu.yermolenko.model.WeatherDataXml;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.services.WeatherAggregationDataService;
import ua.sumdu.yermolenko.services.WeatherForecastException;

/**
 * Class WeatherAggregationDataController provides work with DarkSky API,
 * OpenWeatherMap API, WeatherBit API and WeatherStackAPI.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
@RestController
public class MainController {
    private final static Logger logger = LogManager.getLogger(MainController.class);

    @Autowired
    private WeatherAggregationDataService weatherAggregationDataService;

    /**
     * Method WeatherAggregation the method returns the temperature data in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/temperature/{countryCode}/{city}", method = RequestMethod.GET)
    public ResponseEntity<?> temperatureAggregation(@PathVariable String countryCode,
                                                                    @PathVariable String city,
                                                                    @RequestParam(name = "ext", defaultValue = "json", required = false) String ext) {
        String requestExtension = ext;
        if (!ext.matches("xml|json")) {
            requestExtension = "json";
        }

        try {
            if (requestExtension.equals("json")) {
                WeatherDataJson weatherDataJson = (WeatherDataJson) weatherAggregationDataService.temperatureAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataXml weatherDataXml = (WeatherDataXml) weatherAggregationDataService.temperatureAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the city coordinates in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/cityCoordinates/{countryCode}/{city}", method = RequestMethod.GET)
    public ResponseEntity<WeatherDataDto> cityCoordinatesAggregation(@PathVariable String countryCode,
                                                @PathVariable String city,
                                                @RequestParam(name = "ext", defaultValue = "json", required = false) String ext) {
        String requestExtension = ext;
        if (!ext.matches("xml|json")) {
            requestExtension = "json";
        }

        try {
            if (requestExtension.equals("json")) {
                WeatherDataJson weatherDataJson = (WeatherDataJson) weatherAggregationDataService.cityCoordinatesAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataXml weatherDataXml = (WeatherDataXml) weatherAggregationDataService.cityCoordinatesAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the pressure data in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/pressure/{countryCode}/{city}", method = RequestMethod.GET)
    public ResponseEntity<WeatherDataDto> pressureAggregation(@PathVariable String countryCode,
                                                    @PathVariable String city,
                                                    @RequestParam(name = "ext", defaultValue = "json", required = false) String ext) {
        String requestExtension = ext;
        if (!ext.matches("xml|json")) {
            requestExtension = "json";
        }

        try {
            if (requestExtension.equals("json")) {
                WeatherDataJson weatherDataJson = (WeatherDataJson) weatherAggregationDataService.pressureAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataXml weatherDataXml = (WeatherDataXml) weatherAggregationDataService.pressureAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the wind speed data in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/windSpeed/{countryCode}/{city}", method = RequestMethod.GET)
    public ResponseEntity<WeatherDataDto> windSpeedAggregation(@PathVariable String countryCode,
                                                 @PathVariable String city,
                                                 @RequestParam(name = "ext", defaultValue = "json", required = false) String ext) {
        String requestExtension = ext;
        if (!ext.matches("xml|json")) {
            requestExtension = "json";
        }

        try {
            if (requestExtension.equals("json")) {
                WeatherDataJson weatherDataJson = (WeatherDataJson) weatherAggregationDataService.windSpeedAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataXml weatherDataXml = (WeatherDataXml) weatherAggregationDataService.windSpeedAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the humidity data in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/humidity/{countryCode}/{city}", method = RequestMethod.GET)
    public ResponseEntity<WeatherDataDto> humidityAggregation(@PathVariable String countryCode,
                                                 @PathVariable String city,
                                                 @RequestParam(name = "ext", defaultValue = "json", required = false) String ext) {
        String requestExtension = ext;
        if (!ext.matches("xml|json")) {
            requestExtension = "json";
        }

        try {
            if (requestExtension.equals("json")) {
                WeatherDataJson weatherDataJson = (WeatherDataJson) weatherAggregationDataService.humidityAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataXml weatherDataXml = (WeatherDataXml) weatherAggregationDataService.humidityAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the full weather in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/fullWeather/{countryCode}/{city}", method = RequestMethod.GET)
    public ResponseEntity<WeatherDataDto> fullWeatherAggregation(@PathVariable String countryCode,
                                                                 @PathVariable String city,
                                                                 @RequestParam(name = "ext", defaultValue = "json", required = false) String ext) {
        String requestExtension = ext;
        if (!ext.matches("xml|json")) {
            requestExtension = "json";
        }

        try {
            if (requestExtension.equals("json")) {
                WeatherDataJson weatherDataJson = (WeatherDataJson) weatherAggregationDataService.fullWeatherAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataXml weatherDataXml = (WeatherDataXml) weatherAggregationDataService.fullWeatherAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the sunrise time in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/sunriseTime/{countryCode}/{city}", method = RequestMethod.GET)
    public ResponseEntity<WeatherDataDto> sunriseTimeAggregation(@PathVariable String countryCode,
                                                                 @PathVariable String city,
                                                                 @RequestParam(name = "ext", defaultValue = "json", required = false) String ext) {
        String requestExtension = ext;
        if (!ext.matches("xml|json")) {
            requestExtension = "json";
        }

        try {
            if (requestExtension.equals("json")) {
                WeatherDataJson weatherDataJson = (WeatherDataJson) weatherAggregationDataService.sunriseTimeAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataXml weatherDataXml = (WeatherDataXml) weatherAggregationDataService.sunriseTimeAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the direction wind data in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/directionWind/{countryCode}/{city}", method = RequestMethod.GET)
    public ResponseEntity<WeatherDataDto> directionWindAggregation(@PathVariable String countryCode,
                                                                 @PathVariable String city,
                                                                 @RequestParam(name = "ext", defaultValue = "json", required = false) String ext) {
        String requestExtension = ext;
        if (!ext.matches("xml|json")) {
            requestExtension = "json";
        }

        try {
            if (requestExtension.equals("json")) {
                WeatherDataJson weatherDataJson = (WeatherDataJson) weatherAggregationDataService.directionWindAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataXml weatherDataXml = (WeatherDataXml) weatherAggregationDataService.directionWindAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the temperature how does it feel in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/feelsLikeTemperature/{countryCode}/{city}", method = RequestMethod.GET)
    public ResponseEntity<WeatherDataDto> feelsLikeTemperatureAggregation(@PathVariable String countryCode,
                                                                 @PathVariable String city,
                                                                 @RequestParam(name = "ext", defaultValue = "json", required = false) String ext) {
        String requestExtension = ext;
        if (!ext.matches("xml|json")) {
            requestExtension = "json";
        }

        try {
            if (requestExtension.equals("json")) {
                WeatherDataJson weatherDataJson = (WeatherDataJson) weatherAggregationDataService.feelsLikeTemperatureAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataXml weatherDataXml = (WeatherDataXml) weatherAggregationDataService.feelsLikeTemperatureAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    /**
     * Method WeatherAggregation the method returns the weather description in a specific
     * city according to the DarkSky API, OpenWeatherMap API, WeatherBit API
     * and WeatherStackAPI.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<?>
     */
    @RequestMapping(path = "/weatherDescription/{countryCode}/{city}", method = RequestMethod.GET)
    public ResponseEntity<WeatherDataDto> weatherDescriptionAggregation(@PathVariable String countryCode,
                                                                          @PathVariable String city,
                                                                          @RequestParam(name = "ext", defaultValue = "json", required = false) String ext) {
        String requestExtension = ext;
        if (!ext.matches("xml|json")) {
            requestExtension = "json";
        }

        try {
            if (requestExtension.equals("json")) {
                WeatherDataJson weatherDataJson = (WeatherDataJson) weatherAggregationDataService.weatherDescriptionAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataXml weatherDataXml = (WeatherDataXml) weatherAggregationDataService.weatherDescriptionAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
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
