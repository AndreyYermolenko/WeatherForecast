package ua.sumdu.yermolenko.services.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ua.sumdu.yermolenko.services.CityCoordinatesService;

import static org.springframework.http.HttpStatus.*;

/**
 * Class CityCoordinatesServiceImpl implements interface CityCoordinatesService.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
@Service
public class CityCoordinatesServiceImpl implements CityCoordinatesService {
    @Value("${opencagedata.api.key}")
    private String apiKey;
    @Value("${opencagedata.url}")
    private String url;

    /**
     * Method getCityCoordinates returns city coordinates.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return double[]
     * @throws JSONException when city not found.
     * @throws IllegalArgumentException when city not found.
     * @throws HttpStatusCodeException when request failed.
     */
    @Cacheable("cityCoordinates")
    public double[] getCityCoordinates(String city, String countryCode) throws JSONException, IllegalArgumentException, HttpStatusCodeException {
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
        if (response.getStatusCode() == OK) {
            double[] cord = new double[2];
            JSONObject jsonObject = new JSONObject(response.getBody());
            cord[0] = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getDouble("lat");
            cord[1] = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getDouble("lng");
            String typePlace = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("components").getString("_type");
            if (!typePlace.equals("city") &&
                !typePlace.equals("neighbourhood")) {
                throw new IllegalArgumentException();
            }
            return cord;
        } else {
            throw new HttpServerErrorException(response.getStatusCode());
        }
    }
}
