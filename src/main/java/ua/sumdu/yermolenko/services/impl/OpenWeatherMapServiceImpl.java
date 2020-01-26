package ua.sumdu.yermolenko.services.impl;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.services.interfaces.OpenWeatherMapService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.Future;

@Service
public class OpenWeatherMapServiceImpl implements OpenWeatherMapService {
    @Value("${servicename.openweathermap}")
    private String serviceName;
    @Value("${openweathermap.api.key}")
    private String apiKey;
    @Value("${openweathermap.url}")
    String url;

    @Override
    @Async
    @Cacheable("openWeatherMapCurrent")
    public Future<String> currentWeather(@NonNull String city, @NonNull String countryCode) {
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

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(response.getBody())));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        String temperature;
        try {
            temperature = xpath.evaluate("/current/temperature/@value", doc);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }

        WeatherDataDto weatherDataDto = new WeatherDataDto();
        weatherDataDto.setServiceName(serviceName);
        weatherDataDto.setName(city);
        weatherDataDto.setCountry(countryCode);
        weatherDataDto.setTemperature(temperature);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new AsyncResult<>(weatherDataDto.toString());
        } else {
            return new AsyncResult<>("Request Failed" +"\n"
                    + response.getStatusCode());
        }
    }
}
