package ua.sumdu.yermolenko.services.impl;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.services.CityCoordinatesService;
import ua.sumdu.yermolenko.services.DarkSkyService;

import static ua.sumdu.yermolenko.services.ServiceConstants.DARKSKY_SERVICENAME;

/**
 * Class DarkSkyServiceImpl implements interface DarkSkyService.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
@Service
public class DarkSkyServiceImpl implements DarkSkyService {
    private final static Logger logger = LogManager.getLogger(DarkSkyServiceImpl.class);
    @Value("${darksky.api.key}")
    private String apiKey;
    @Value("${darksky.url}")
    private String url;
    @Autowired
    private CityCoordinatesService cityCoordinatesService;

    /**
     * Method getTemperature executes an API request to obtain temperature data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Cacheable("darkSkyTemperature")
    public ResponseEntity<WeatherDataDto> getTemperature(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            String temperature = String.valueOf(jsonObject
                    .getJSONObject("currently")
                    .getDouble("temperature"));

            WeatherDataDto weatherDataDto = new WeatherDataDto();
            weatherDataDto.setServiceName(DARKSKY_SERVICENAME);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setTemperature(temperature);

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<>(new WeatherDataDto(DARKSKY_SERVICENAME, response.getBody()), response.getStatusCode());
        }
    }

    /**
     * Method getCityCoordinates executes an API request to obtain city coordinates.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Cacheable("darkSkyCityCoordinates")
    public ResponseEntity<WeatherDataDto> getCityCoordinates(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            String latitude = String.valueOf(jsonObject
                    .getDouble("latitude"));
            String longitude = String.valueOf(jsonObject
                    .getDouble("longitude"));

            WeatherDataDto weatherDataDto = new WeatherDataDto();
            weatherDataDto.setServiceName(DARKSKY_SERVICENAME);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setLatitude(latitude);
            weatherDataDto.setLongitude(longitude);

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<>(new WeatherDataDto(DARKSKY_SERVICENAME, response.getBody()), response.getStatusCode());
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
    @Cacheable("darkSkyPressure")
    public ResponseEntity<WeatherDataDto> getPressure(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            String pressure = String.valueOf(jsonObject
                    .getJSONObject("currently")
                    .getDouble("pressure"));

            WeatherDataDto weatherDataDto = new WeatherDataDto();
            weatherDataDto.setServiceName(DARKSKY_SERVICENAME);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setPressure(pressure);

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<>(new WeatherDataDto(DARKSKY_SERVICENAME, response.getBody()), response.getStatusCode());
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
    @Cacheable("darkSkyWindSpeed")
    public ResponseEntity<WeatherDataDto> getWindSpeed(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            String windSpeed = String.valueOf(jsonObject
                    .getJSONObject("currently")
                    .getDouble("windSpeed"));

            WeatherDataDto weatherDataDto = new WeatherDataDto();
            weatherDataDto.setServiceName(DARKSKY_SERVICENAME);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setWindSpeed(windSpeed);

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<>(new WeatherDataDto(DARKSKY_SERVICENAME, response.getBody()), response.getStatusCode());
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
    @Cacheable("darkSkyHumidity")
    public ResponseEntity<WeatherDataDto> getHumidity(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            String humidity = String.valueOf(jsonObject
                    .getJSONObject("currently")
                    .getDouble("humidity")*100);

            WeatherDataDto weatherDataDto = new WeatherDataDto();
            weatherDataDto.setServiceName(DARKSKY_SERVICENAME);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setHumidity(humidity);

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<>(new WeatherDataDto(DARKSKY_SERVICENAME, response.getBody()), response.getStatusCode());
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
    @Cacheable("darkSkyFullWeather")
    public ResponseEntity<WeatherDataDto> getFullWeather(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            String temperature = String.valueOf(jsonObject
                    .getJSONObject("currently")
                    .getDouble("temperature"));
            String pressure = String.valueOf(jsonObject
                    .getJSONObject("currently")
                    .getDouble("pressure"));
            String windSpeed = String.valueOf(jsonObject
                    .getJSONObject("currently")
                    .getDouble("windSpeed"));
            String humidity = String.valueOf(jsonObject
                    .getJSONObject("currently")
                    .getDouble("humidity")*100);

            WeatherDataDto weatherDataDto = new WeatherDataDto();
            weatherDataDto.setServiceName(DARKSKY_SERVICENAME);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setTemperature(temperature);
            weatherDataDto.setPressure(pressure);
            weatherDataDto.setWindSpeed(windSpeed);
            weatherDataDto.setHumidity(humidity);

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<>(new WeatherDataDto(DARKSKY_SERVICENAME, response.getBody()), response.getStatusCode());
        }
    }

    private ResponseEntity<String> getWeather(@NonNull String city, @NonNull String countryCode) {
        long unixTime = System.currentTimeMillis() / 1000L;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(headers);

        double[] cord;
        try {
            cord = cityCoordinatesService.getCityCoordinates(city, countryCode);
        } catch (JSONException | IllegalArgumentException e) {
            return new ResponseEntity<String>("City not found", HttpStatus.BAD_REQUEST);
        } catch (HttpServerErrorException e) {
            return new ResponseEntity<String>("City Coordinates Server Exception: " + e.toString(), HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class,
                apiKey, cord[0], cord[1], unixTime
        );

        return response;
    }
}
