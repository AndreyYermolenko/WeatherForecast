package ua.sumdu.yermolenko.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.sumdu.yermolenko.model.WeatherDataFormatJson;
import ua.sumdu.yermolenko.model.WeatherDataFormatXml;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.services.WeatherAggregationDataService;
import ua.sumdu.yermolenko.exceptions.WeatherForecastException;

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
                                                    @RequestParam(name = "ext", defaultValue = "json", required = false)
                                                                String ext) {
        String requestExtension = ext;
        if (!ext.matches("xml|json")) {
            requestExtension = "json";
        }

        try {
            if (requestExtension.equals("json")) {
                WeatherDataFormatJson weatherDataJson = (WeatherDataFormatJson) weatherAggregationDataService
                        .temperatureAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataFormatXml weatherDataXml = (WeatherDataFormatXml) weatherAggregationDataService
                        .temperatureAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            LOGGER.error(e.getMessage(), e);
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
                WeatherDataFormatJson weatherDataJson = (WeatherDataFormatJson) weatherAggregationDataService
                        .cityCoordinatesAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataFormatXml weatherDataXml = (WeatherDataFormatXml) weatherAggregationDataService
                        .cityCoordinatesAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            LOGGER.error(e.getMessage(), e);
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
                WeatherDataFormatJson weatherDataJson = (WeatherDataFormatJson) weatherAggregationDataService
                        .pressureAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataFormatXml weatherDataXml = (WeatherDataFormatXml) weatherAggregationDataService
                        .pressureAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            LOGGER.error(e.getMessage(), e);
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
                WeatherDataFormatJson weatherDataJson = (WeatherDataFormatJson) weatherAggregationDataService
                        .windSpeedAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataFormatXml weatherDataXml = (WeatherDataFormatXml) weatherAggregationDataService
                        .windSpeedAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            LOGGER.error(e.getMessage(), e);
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
                WeatherDataFormatJson weatherDataJson = (WeatherDataFormatJson) weatherAggregationDataService
                        .humidityAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataFormatXml weatherDataXml = (WeatherDataFormatXml) weatherAggregationDataService
                        .humidityAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            LOGGER.error(e.getMessage(), e);
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
                WeatherDataFormatJson weatherDataJson = (WeatherDataFormatJson) weatherAggregationDataService
                        .fullWeatherAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataFormatXml weatherDataXml = (WeatherDataFormatXml) weatherAggregationDataService
                        .fullWeatherAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            LOGGER.error(e.getMessage(), e);
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
                WeatherDataFormatJson weatherDataJson = (WeatherDataFormatJson) weatherAggregationDataService
                        .sunriseTimeAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataFormatXml weatherDataXml = (WeatherDataFormatXml) weatherAggregationDataService
                        .sunriseTimeAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            LOGGER.error(e.getMessage(), e);
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
                WeatherDataFormatJson weatherDataJson = (WeatherDataFormatJson) weatherAggregationDataService
                        .directionWindAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataFormatXml weatherDataXml = (WeatherDataFormatXml) weatherAggregationDataService
                        .directionWindAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            LOGGER.error(e.getMessage(), e);
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
                WeatherDataFormatJson weatherDataJson = (WeatherDataFormatJson) weatherAggregationDataService
                        .feelsLikeTemperatureAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataFormatXml weatherDataXml = (WeatherDataFormatXml) weatherAggregationDataService
                        .feelsLikeTemperatureAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            LOGGER.error(e.getMessage(), e);
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
                WeatherDataFormatJson weatherDataJson = (WeatherDataFormatJson) weatherAggregationDataService
                        .weatherDescriptionAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataJson, HttpStatus.OK);
            } else {
                WeatherDataFormatXml weatherDataXml = (WeatherDataFormatXml) weatherAggregationDataService
                        .weatherDescriptionAggregation(city, countryCode, requestExtension);
                return new ResponseEntity(weatherDataXml, HttpStatus.OK);
            }
        } catch (WeatherForecastException e) {
            LOGGER.error(e.getMessage(), e);
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
