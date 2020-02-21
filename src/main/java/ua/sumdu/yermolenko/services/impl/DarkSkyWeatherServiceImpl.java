package ua.sumdu.yermolenko.services.impl;

import lombok.NonNull;
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
import ua.sumdu.yermolenko.services.WeatherForecastService;

import static ua.sumdu.yermolenko.constants.ServiceConstants.DARKSKY_SERVICENAME;

/**
 * Class DarkSkyServiceImpl implements interface DarkSkyService.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
@Service
public class WeatherForecastServiceImpl implements WeatherForecastService {
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
     * @return WeatherDataDto
     */
    @Cacheable("darkSkyTemperature")
    public WeatherDataDto getTemperature(@NonNull String city, @NonNull String countryCode) {
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

            return weatherDataDto;
        } else {
            return new WeatherDataDto(DARKSKY_SERVICENAME, response.getBody());
        }
    }

    /**
     * Method getCityCoordinates executes an API request to obtain city coordinates.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Cacheable("darkSkyCityCoordinates")
    public WeatherDataDto getCityCoordinates(@NonNull String city, @NonNull String countryCode) {
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

            return weatherDataDto;
        } else {
            return new WeatherDataDto(DARKSKY_SERVICENAME, response.getBody());
        }
    }

    /**
     * Method getPressure executes an API request to obtain pressure data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Override
    @Cacheable("darkSkyPressure")
    public WeatherDataDto getPressure(@NonNull String city, @NonNull String countryCode) {
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

            return weatherDataDto;
        } else {
            return new WeatherDataDto(DARKSKY_SERVICENAME, response.getBody());
        }
    }

    /**
     * Method getWindSpeed executes an API request to obtain wind speed data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Override
    @Cacheable("darkSkyWindSpeed")
    public WeatherDataDto getWindSpeed(@NonNull String city, @NonNull String countryCode) {
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

            return weatherDataDto;
        } else {
            return new WeatherDataDto(DARKSKY_SERVICENAME, response.getBody());
        }
    }

    /**
     * Method getHumidity executes an API request to obtain humidity data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Override
    @Cacheable("darkSkyHumidity")
    public WeatherDataDto getHumidity(@NonNull String city, @NonNull String countryCode) {
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

            return weatherDataDto;
        } else {
            return new WeatherDataDto(DARKSKY_SERVICENAME, response.getBody());
        }
    }

    /**
     * Method getFullWeather executes an API request to obtain current weather data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Override
    @Cacheable("darkSkyFullWeather")
    public WeatherDataDto getFullWeather(@NonNull String city, @NonNull String countryCode) {
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

            return weatherDataDto;
        } else {
            return new WeatherDataDto(DARKSKY_SERVICENAME, response.getBody());
        }
    }

    /**
     * Method getSunRiseTime executes an API request to obtain city sunrise time.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Override
    @Cacheable("darkSkySunriseTime")
    public WeatherDataDto getSunriseTime(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            String sunrise = "Api does not support this field.";
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            weatherDataDto.setServiceName(DARKSKY_SERVICENAME);
            weatherDataDto.setName(city);
            weatherDataDto.setSunrise(sunrise);

            return weatherDataDto;
        } else {
            return new WeatherDataDto(DARKSKY_SERVICENAME, response.getBody());
        }
    }

    /**
     * Method getFeelsLikeTemperature executes an API request to obtain temperature how does it feel.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Cacheable("darkSkyFeelsLikeTemperature")
    public WeatherDataDto getFeelsLikeTemperature(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            String temperatureFeelsLike = String.valueOf(jsonObject
                    .getJSONObject("currently")
                    .getDouble("apparentTemperature"));

            WeatherDataDto weatherDataDto = new WeatherDataDto();
            weatherDataDto.setServiceName(DARKSKY_SERVICENAME);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setTemperatureFeelsLike(temperatureFeelsLike);

            return weatherDataDto;
        } else {
            return new WeatherDataDto(DARKSKY_SERVICENAME, response.getBody());
        }
    }

    /**
     * Method getDirectionWind executes an API request to obtain direction wind data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Cacheable("darkSkyDirectionWind")
    public WeatherDataDto getDirectionWind(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            String windDir = "Api does not support this field.";

            WeatherDataDto weatherDataDto = new WeatherDataDto();
            weatherDataDto.setServiceName(DARKSKY_SERVICENAME);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setDirectionWind(windDir);

            return weatherDataDto;
        } else {
            return new WeatherDataDto(DARKSKY_SERVICENAME, response.getBody());
        }
    }

    /**
     * Method getWeatherDescription executes an API request to obtain weather description.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Cacheable("darkSkyWeatherDescription")
    public WeatherDataDto getWeatherDescription(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            String weatherDesc = "Api does not support this field.";
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            weatherDataDto.setServiceName(DARKSKY_SERVICENAME);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setName(city);
            weatherDataDto.setWeatherDescription(weatherDesc);

            return weatherDataDto;
        } else {
            return new WeatherDataDto(DARKSKY_SERVICENAME, response.getBody());
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
            return new ResponseEntity<String>("City Coordinates Server Exception: " + e.toString(),
                    HttpStatus.BAD_REQUEST);
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
