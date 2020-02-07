package ua.sumdu.yermolenko.services.impl;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.services.OpenWeatherMapService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;

import static ua.sumdu.yermolenko.services.ServiceConstants.OPENWEATHERMAP_SERVICENAME;

/**
 * Class OpenWeatherMapServiceImpl implements interface OpenWeatherMapService.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
@Service
public class OpenWeatherMapServiceImpl implements OpenWeatherMapService {
    private final static Logger logger = LogManager.getLogger(OpenWeatherMapServiceImpl.class);
    private final ResponseEntity<WeatherDataDto> RESPONSE_FAILED = new ResponseEntity<WeatherDataDto>(
            new WeatherDataDto(OPENWEATHERMAP_SERVICENAME,
                    "Response Failed. Server error."),
            HttpStatus.INTERNAL_SERVER_ERROR);
    private final String PROBLEM_MESSAGE = "OpenWeatherMapService problem";

    @Value("${openweathermap.api.key}")
    private String apiKey;
    @Value("${openweathermap.url}")
    private String url;

    /**
     * Method getTemperature executes an API request to obtain temperature data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Cacheable("openWeatherMapTemperature")
    public ResponseEntity<WeatherDataDto> getTemperature(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(response.getBody())));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        String temperature;
        try {
            temperature = xpath.evaluate("/current/temperature/@value", doc);
        } catch (XPathExpressionException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        WeatherDataDto weatherDataDto = new WeatherDataDto();
        weatherDataDto.setServiceName(OPENWEATHERMAP_SERVICENAME);
        weatherDataDto.setName(city);
        weatherDataDto.setCountry(countryCode);
        weatherDataDto.setTemperature(temperature);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(OPENWEATHERMAP_SERVICENAME, response.getBody()), response.getStatusCode());
        }
    }

    /**
     * Method getCityCoordinates executes an API request to obtain city coordinates.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Cacheable("openWeatherMapCityCoordinates")
    public ResponseEntity<WeatherDataDto> getCityCoordinates(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(response.getBody())));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        String latitude;
        String longitude;
        try {
            latitude = xpath.evaluate("/current/city/coord/@lat", doc);
            longitude = xpath.evaluate("/current/city/coord/@lon", doc);
        } catch (XPathExpressionException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        WeatherDataDto weatherDataDto = new WeatherDataDto();
        weatherDataDto.setServiceName(OPENWEATHERMAP_SERVICENAME);
        weatherDataDto.setName(city);
        weatherDataDto.setCountry(countryCode);
        weatherDataDto.setLatitude(latitude);
        weatherDataDto.setLongitude(longitude);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(OPENWEATHERMAP_SERVICENAME, response.getBody()), response.getStatusCode());
        }
    }

    /**
     * Method getPressure executes an API request to obtain pressure data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    @Override
    @Cacheable("openWeatherMapPressure")
    public ResponseEntity<WeatherDataDto> getPressure(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(response.getBody())));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        String pressure;
        try {
            pressure = xpath.evaluate("/current/pressure/@value", doc);
        } catch (XPathExpressionException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        WeatherDataDto weatherDataDto = new WeatherDataDto();
        weatherDataDto.setServiceName(OPENWEATHERMAP_SERVICENAME);
        weatherDataDto.setName(city);
        weatherDataDto.setCountry(countryCode);
        weatherDataDto.setPressure(pressure);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(OPENWEATHERMAP_SERVICENAME, response.getBody()), response.getStatusCode());
        }
    }

    /**
     * Method getWindSpeed executes an API request to obtain wind speed data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    @Override
    @Cacheable("openWeatherMapWindSpeed")
    public ResponseEntity<WeatherDataDto> getWindSpeed(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(response.getBody())));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        String windSpeed;
        try {
            windSpeed = xpath.evaluate("/current/wind/speed/@value", doc);
        } catch (XPathExpressionException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        WeatherDataDto weatherDataDto = new WeatherDataDto();
        weatherDataDto.setServiceName(OPENWEATHERMAP_SERVICENAME);
        weatherDataDto.setName(city);
        weatherDataDto.setCountry(countryCode);
        weatherDataDto.setWindSpeed(windSpeed);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(OPENWEATHERMAP_SERVICENAME, response.getBody()), response.getStatusCode());
        }
    }

    /**
     * Method getHumidity executes an API request to obtain humidity data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    @Override
    @Cacheable("openWeatherMapCityHumidity")
    public ResponseEntity<WeatherDataDto> getHumidity(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(response.getBody())));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        String humidity;
        try {
            humidity = xpath.evaluate("/current/humidity/@value", doc);
        } catch (XPathExpressionException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        WeatherDataDto weatherDataDto = new WeatherDataDto();
        weatherDataDto.setServiceName(OPENWEATHERMAP_SERVICENAME);
        weatherDataDto.setName(city);
        weatherDataDto.setCountry(countryCode);
        weatherDataDto.setHumidity(humidity);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(OPENWEATHERMAP_SERVICENAME, response.getBody()), response.getStatusCode());
        }
    }

    /**
     * Method getFullWeather executes an API request to obtain current weather data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Override
    @Cacheable("openWeatherMapFullWeather")
    public ResponseEntity<WeatherDataDto> getFullWeather(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(response.getBody())));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        String temperature;
        String pressure;
        String windSpeed;
        String humidity;
        try {
            temperature = xpath.evaluate("/current/temperature/@value", doc);
            pressure = xpath.evaluate("/current/pressure/@value", doc);
            windSpeed = xpath.evaluate("/current/wind/speed/@value", doc);
            humidity = xpath.evaluate("/current/humidity/@value", doc);
        } catch (XPathExpressionException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        WeatherDataDto weatherDataDto = new WeatherDataDto();
        weatherDataDto.setServiceName(OPENWEATHERMAP_SERVICENAME);
        weatherDataDto.setName(city);
        weatherDataDto.setCountry(countryCode);
        weatherDataDto.setTemperature(temperature);
        weatherDataDto.setPressure(pressure);
        weatherDataDto.setWindSpeed(windSpeed);
        weatherDataDto.setHumidity(humidity);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(OPENWEATHERMAP_SERVICENAME, response.getBody()), response.getStatusCode());
        }
    }

    /**
     * Method getSunRiseTime executes an API request to obtain city sunrise time.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    @Override
    @Cacheable("openWeatherMapSunriseTime")
    public ResponseEntity<WeatherDataDto> getSunriseTime(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(response.getBody())));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        String sunrise;
        try {
            sunrise = xpath.evaluate("/current/city/sun/@rise", doc);
        } catch (XPathExpressionException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        WeatherDataDto weatherDataDto = new WeatherDataDto();
        weatherDataDto.setServiceName(OPENWEATHERMAP_SERVICENAME);
        weatherDataDto.setName(city);
        weatherDataDto.setCountry(countryCode);
        weatherDataDto.setSunrise(sunrise);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(OPENWEATHERMAP_SERVICENAME, response.getBody()), response.getStatusCode());
        }
    }

    /**
     * Method getFeelsLikeTemperature executes an API request to obtain temperature how does it feel.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    @Cacheable("openWeatherMapFeelsLikeTemperature")
    public ResponseEntity<WeatherDataDto> getFeelsLikeTemperature(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(response.getBody())));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        String temperatureFeelsLike;
        try {
            temperatureFeelsLike = xpath.evaluate("/current/feels_like/@value", doc);
        } catch (XPathExpressionException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        WeatherDataDto weatherDataDto = new WeatherDataDto();
        weatherDataDto.setServiceName(OPENWEATHERMAP_SERVICENAME);
        weatherDataDto.setName(city);
        weatherDataDto.setCountry(countryCode);
        weatherDataDto.setTemperatureFeelsLike(temperatureFeelsLike);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(OPENWEATHERMAP_SERVICENAME, response.getBody()), response.getStatusCode());
        }
    }

    /**
     * Method getDirectionWind executes an API request to obtain direction wind data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    @Cacheable("openWeatherMapDirectionWind")
    public ResponseEntity<WeatherDataDto> getDirectionWind(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(response.getBody())));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        String windDir;
        try {
            windDir = xpath.evaluate("/current/wind/direction/@name", doc);
        } catch (XPathExpressionException e) {
            logger.error(PROBLEM_MESSAGE, e);
            return RESPONSE_FAILED;
        }

        WeatherDataDto weatherDataDto = new WeatherDataDto();
        weatherDataDto.setServiceName(OPENWEATHERMAP_SERVICENAME);
        weatherDataDto.setName(city);
        weatherDataDto.setCountry(countryCode);
        weatherDataDto.setDirectionWind(windDir);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(OPENWEATHERMAP_SERVICENAME, response.getBody()), response.getStatusCode());
        }
    }

    /**
     * Method getWeatherDescription executes an API request to obtain weather description.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    @Cacheable("openWeatherMapWeatherDescription")
    public ResponseEntity<WeatherDataDto> getWeatherDescription(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        String weatherDescription = "Api does not support this field.";

        WeatherDataDto weatherDataDto = new WeatherDataDto();
        weatherDataDto.setServiceName(OPENWEATHERMAP_SERVICENAME);
        weatherDataDto.setName(city);
        weatherDataDto.setCountry(countryCode);
        weatherDataDto.setWeatherDescription(weatherDescription);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(OPENWEATHERMAP_SERVICENAME, response.getBody()), response.getStatusCode());
        }
    }

    private ResponseEntity<String> getWeather(@NonNull String city, @NonNull String countryCode) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class,
                city, countryCode, apiKey
        );
        return response;
    }
}
