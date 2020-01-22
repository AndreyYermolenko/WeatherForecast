package ua.sumdu.yermolenko.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import ua.sumdu.yermolenko.services.interfaces.WeatherStack;
import org.springframework.web.client.RestTemplate;

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
            return "Request Successful" +"\n"
                    + response.getBody();
        } else {
            return "Request Failed" +"\n"
                    + response.getStatusCode();
        }
    }
}
