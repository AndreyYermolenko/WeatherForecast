package ua.sumdu.yermolenko.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import ua.sumdu.yermolenko.model.WeatherData;
import ua.sumdu.yermolenko.services.interfaces.WeatherStack;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class WeatherStackImpl implements WeatherStack {
    @Value("${api.key}")
    private String api_key;

    public String currentWeather(String city) {
        String url = "http://api.weatherstack.com/current?access_key={api_key}&query={city}";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class,
                api_key, city
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherData weatherData;
            try {
                weatherData = objectMapper.readValue(response.getBody(), WeatherData.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "Request Successful" +"\n"
                    + weatherData.toString();
        } else {
            return "Request Failed" +"\n"
                    + response.getStatusCode();
        }
    }
}
