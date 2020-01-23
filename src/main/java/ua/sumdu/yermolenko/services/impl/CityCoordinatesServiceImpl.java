package ua.sumdu.yermolenko.services.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.sumdu.yermolenko.services.interfaces.CityCoordinatesService;

@Service
public class CityCoordinatesServiceImpl implements CityCoordinatesService {
    @Value("${opencagedata.api.key}")
    private String apiKey;
    @Value("${opencagedata.url}")
    String url;

    public double[] getCityCoordinates(String city, String countryCode) throws JSONException, IllegalArgumentException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class,
                city, apiKey, countryCode
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            double[] cord = new double[2];
            JSONObject jsonObject = new JSONObject(response.getBody());
            cord[0] = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getDouble("lat");
            cord[1] = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getDouble("lng");
            String typePlace = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("components").getString("_type");
            if (!typePlace.equals("city")) {
                throw new IllegalArgumentException();
            }
            return cord;
        } else {
            throw new RuntimeException();
        }
    }
}
