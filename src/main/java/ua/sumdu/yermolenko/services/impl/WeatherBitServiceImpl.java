package ua.sumdu.yermolenko.services.impl;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.services.WeatherBitService;

import static ua.sumdu.yermolenko.services.ServiceConstants.WEATHERBIT_SERVICENAME;

/**
 * Class WeatherBitServiceImpl implements interface WeatherBitService.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
@Service
public class WeatherBitServiceImpl implements WeatherBitService {
    private final static Logger logger = LogManager.getLogger(WeatherBitServiceImpl.class);

    @Value("${weatherbit.api.key}")
    private String apiKey;
    @Value("${weatherbit.url}")
    private String url;

    /**
     * Method getTemperature executes an API request to obtain temperature data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Cacheable("weatherBitTemperature")
    public ResponseEntity<WeatherDataDto> getTemperature(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            JSONObject jsonObject = new JSONObject(response.getBody());
            String temperature = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("temp"));

            weatherDataDto.setServiceName(WEATHERBIT_SERVICENAME);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setTemperature(temperature);

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(WEATHERBIT_SERVICENAME, response.getBody()),response.getStatusCode());
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
    @Cacheable("weatherBitCityCoordinates")
    public ResponseEntity<WeatherDataDto> getCityCoordinates(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            JSONObject jsonObject = new JSONObject(response.getBody());
            String latitude = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("lat"));
            String longitude = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("lon"));

            weatherDataDto.setServiceName(WEATHERBIT_SERVICENAME);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setLatitude(latitude);
            weatherDataDto.setLongitude(longitude);

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(WEATHERBIT_SERVICENAME, response.getBody()),response.getStatusCode());
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
    @Cacheable("weatherBitFullWeather")
    public ResponseEntity<WeatherDataDto> getFullWeather(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            JSONObject jsonObject = new JSONObject(response.getBody());
            String temperature = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("temp"));
            String pressure = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("pres"));
            String windSpeed = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("wind_spd"));
            String humidity = "Api does not support this field.";

            weatherDataDto.setServiceName(WEATHERBIT_SERVICENAME);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setTemperature(temperature);
            weatherDataDto.setPressure(pressure);
            weatherDataDto.setWindSpeed(windSpeed);
            weatherDataDto.setHumidity(humidity);

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(WEATHERBIT_SERVICENAME, response.getBody()),response.getStatusCode());
        }
    }

    @Override
    @Cacheable("weatherBitCityPressure")
    public ResponseEntity<WeatherDataDto> getPressure(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            JSONObject jsonObject = new JSONObject(response.getBody());
            String pressure = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("pres"));

            weatherDataDto.setServiceName(WEATHERBIT_SERVICENAME);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setPressure(pressure);

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(WEATHERBIT_SERVICENAME, response.getBody()),response.getStatusCode());
        }
    }

    @Override
    @Cacheable("weatherBitWindSpeed")
    public ResponseEntity<WeatherDataDto> getWindSpeed(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            JSONObject jsonObject = new JSONObject(response.getBody());
            String windSpeed = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("wind_spd"));

            weatherDataDto.setServiceName(WEATHERBIT_SERVICENAME);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setWindSpeed(windSpeed);

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(WEATHERBIT_SERVICENAME, response.getBody()),response.getStatusCode());
        }
    }

    @Override
    @Cacheable("weatherBitCityHumidity")
    public ResponseEntity<WeatherDataDto> getHumidity(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            String humidity = "Api does not support this field.";

            weatherDataDto.setServiceName(WEATHERBIT_SERVICENAME);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setHumidity(humidity);

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(WEATHERBIT_SERVICENAME, response.getBody()),response.getStatusCode());
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
