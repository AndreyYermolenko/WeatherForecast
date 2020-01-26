package ua.sumdu.yermolenko.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import ua.sumdu.yermolenko.model.WeatherStackData;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.services.WeatherDataConverter;
import ua.sumdu.yermolenko.services.interfaces.WeatherStackService;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.Future;

@Service
public class WeatherStackServiceImpl implements WeatherStackService {
    @Value("${weatherstack.api.key}")
    private String apiKey;
    @Value("${weatherstack.url}")
    private String url;
    @Autowired
    private WeatherDataConverter weatherDataConverter;

    @Override
    @Async
    @Cacheable("weatherStackCurrent")
    public Future<String> currentWeather(String city, String countryCode) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class,
                apiKey, city, countryCode
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherStackData weatherData;
            WeatherDataDto weatherDataDto;
            try {
                weatherData = objectMapper.readValue(response.getBody(), WeatherStackData.class);
                weatherDataDto = weatherDataConverter.toJsonWeatherStackDataConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return new AsyncResult<>(weatherDataDto.toString());
        } else {
            return new AsyncResult<>("Request Failed" +"\n"
                    + response.getStatusCode());
        }
    }
}
