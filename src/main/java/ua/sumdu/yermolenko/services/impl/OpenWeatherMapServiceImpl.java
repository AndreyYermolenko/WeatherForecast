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
import ua.sumdu.yermolenko.config.ExecutorSingleton;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Class OpenWeatherMapServiceImpl implements interface OpenWeatherMapService.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
@Service
public class OpenWeatherMapServiceImpl implements OpenWeatherMapService {
    private final static Logger logger = LogManager.getLogger(OpenWeatherMapServiceImpl.class);
    @Value("${servicename.openweathermap}")
    private String serviceName;
    @Value("${openweathermap.api.key}")
    private String apiKey;
    @Value("${openweathermap.url}")
    private String url;
    @Value("${api.timeout}")
    private int apiTimeout;

    /**
     * Method getTemperatureThread executes an API request in a separate thread
     * to obtain temperature data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Override
    public String getTemperatureThread(@NonNull String city, @NonNull String countryCode) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->
                getTemperature(city, countryCode), ExecutorSingleton.getExecutor()
        ).completeOnTimeout("Превышено время ожидания ответа от сервера.", apiTimeout, TimeUnit.SECONDS);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("OpenWeatherMapService problem", e);
            return "Request Failed. Server error.";
        }
    }

    /**
     * Method getCityCoordinatesThread executes an API request in a separate thread
     * to obtain data on the coordinates of the city.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Override
    public String getCityCoordinatesThread(@NonNull String city, @NonNull String countryCode) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->
                getCityCoordinates(city, countryCode), ExecutorSingleton.getExecutor()
        ).completeOnTimeout("Превышено время ожидания ответа от сервера.", apiTimeout, TimeUnit.SECONDS);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("OpenWeatherMapService problem", e);
            return "Request Failed. Server error.";
        }
    }

    /**
     * Method getFullWeatherThread executes an API request in a separate thread
     * to obtain current weather data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Override
    public String getFullWeatherThread(@NonNull String city, @NonNull String countryCode) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->
                getFullWeather(city, countryCode), ExecutorSingleton.getExecutor()
        ).completeOnTimeout("Превышено время ожидания ответа от сервера.", apiTimeout, TimeUnit.SECONDS);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("OpenWeatherMapService problem", e);
            return "Request Failed. Server error.";
        }
    }

    /**
     * Method getTemperature executes an API request to obtain temperature data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Cacheable("openWeatherMapTemperature")
    public String getTemperature(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(response.getBody())));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error("OpenWeatherMapService problem", e);
            return "Request Failed. Server error.";
        }

        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        String temperature;
        try {
            temperature = xpath.evaluate("/current/temperature/@value", doc);
        } catch (XPathExpressionException e) {
            logger.error("OpenWeatherMapService problem", e);
            return "Request Failed. Server error.";
        }

        WeatherDataDto weatherDataDto = new WeatherDataDto();
        weatherDataDto.setServiceName(serviceName);
        weatherDataDto.setName(city);
        weatherDataDto.setCountry(countryCode);
        weatherDataDto.setTemperature(temperature);

        if (response.getStatusCode() == HttpStatus.OK) {
            return weatherDataDto.toJsonTemperature();
        } else {
            return "Request Failed" +"\n"
                    + response.getStatusCode();
        }
    }

    /**
     * Method getCityCoordinates executes an API request to obtain data
     * on the coordinates of the city.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Cacheable("openWeatherMapCityCoordinates")
    public String getCityCoordinates(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(response.getBody())));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error("OpenWeatherMapService problem", e);
            return "Request Failed. Server error.";
        }

        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        String latitude;
        String longitude;
        try {
            latitude = xpath.evaluate("/current/city/coord/@lat", doc);
            longitude = xpath.evaluate("/current/city/coord/@lon", doc);
        } catch (XPathExpressionException e) {
            logger.error("OpenWeatherMapService problem", e);
            return "Request Failed. Server error.";
        }

        WeatherDataDto weatherDataDto = new WeatherDataDto();
        weatherDataDto.setServiceName(serviceName);
        weatherDataDto.setName(city);
        weatherDataDto.setCountry(countryCode);
        weatherDataDto.setLatitude(latitude);
        weatherDataDto.setLongitude(longitude);

        if (response.getStatusCode() == HttpStatus.OK) {
            return weatherDataDto.toJsonCoordinates();
        } else {
            return "Request Failed" +"\n"
                    + response.getStatusCode();
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
    public String getFullWeather(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(response.getBody())));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error("OpenWeatherMapService problem", e);
            return "Request Failed. Server error.";
        }

        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        String temperature;
        String pressure;
        String windSpeed;
        try {
            temperature = xpath.evaluate("/current/temperature/@value", doc);
            pressure = xpath.evaluate("/current/pressure/@value", doc);
            windSpeed = xpath.evaluate("/current/wind/speed/@value", doc);
        } catch (XPathExpressionException e) {
            logger.error("OpenWeatherMapService problem", e);
            return "Request Failed. Server error.";
        }

        WeatherDataDto weatherDataDto = new WeatherDataDto();
        weatherDataDto.setServiceName(serviceName);
        weatherDataDto.setName(city);
        weatherDataDto.setCountry(countryCode);
        weatherDataDto.setTemperature(temperature);
        weatherDataDto.setPressure(pressure);
        weatherDataDto.setWindSpeed(windSpeed);

        if (response.getStatusCode() == HttpStatus.OK) {
            return weatherDataDto.toJsonFullWeather();
        } else {
            return "Request Failed" +"\n"
                    + response.getStatusCode();
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
