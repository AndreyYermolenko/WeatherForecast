package ua.sumdu.yermolenko.services.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.services.interfaces.WeatherBitService;

import java.util.concurrent.Future;

@Service
public class WeatherBitServiceImpl implements WeatherBitService {
    @Value("${servicename.weatherbit}")
    private String serviceName;
    @Value("${weatherbit.api.key}")
    private String apiKey;
    @Value("${weatherbit.url}")
    private String url;

    @Override
    @Async
    @Cacheable("weatherBitCurrent")
    public Future<String> currentWeather(String city, String countryCode) {
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

        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            JSONObject jsonObject = new JSONObject(response.getBody());
            String temperature = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("temp"));

            weatherDataDto.setServiceName(serviceName);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setTemperature(temperature);

            return new AsyncResult<>(weatherDataDto.toString());
        } else {
            return new AsyncResult<>("Request Failed" +"\n"
                    + response.getStatusCode());
        }
    }
}
