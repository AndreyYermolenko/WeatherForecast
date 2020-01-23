package ua.sumdu.yermolenko.services.impl;

import lombok.NonNull;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.sumdu.yermolenko.services.interfaces.CityCoordinatesService;
import ua.sumdu.yermolenko.services.interfaces.DarkSkyService;


@Service
public class DarkSkyServiceImpl implements DarkSkyService {
    @Value("${darksky.api.key}")
    private String apiKey;
    @Value("${darksky.url}")
    String url;
    @Autowired
    CityCoordinatesService cityCoordinatesService;

    @Override
    public String currentWeather(@NonNull String city, @NonNull String countryCode) {
        long unixTime = System.currentTimeMillis() / 1000L;


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(headers);

        double[] cord;
        try {
            cord = cityCoordinatesService.getCityCoordinates(city, countryCode);
        } catch (JSONException | IllegalArgumentException e) {
            return "Request Failed: City not found";
        }

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class,
                apiKey, cord[0], cord[1], unixTime
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return "Request Failed" +"\n"
                    + response.getStatusCode();
        }
    }
}
