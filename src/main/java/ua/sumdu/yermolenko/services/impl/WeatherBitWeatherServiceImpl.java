package ua.sumdu.yermolenko.services.impl;

import lombok.NonNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.sumdu.yermolenko.constants.ServiceConstants;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.services.ServiceName;
import ua.sumdu.yermolenko.services.WeatherService;

/**
 * Class WeatherBitServiceImpl implements interface WeatherBitService.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
@Service
public class WeatherBitWeatherServiceImpl implements WeatherService {
    @Value("${weatherbit.api.key}")
    private String apiKey;
    @Value("${weatherbit.url}")
    private String url;
    private final ServiceConstants constants;

    public WeatherBitWeatherServiceImpl(ServiceConstants constants) {
        this.constants = constants;
    }

    /**
     * Method getTemperature executes an API request to obtain temperature data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Cacheable("weatherBitTemperature")
    public WeatherDataDto getTemperature(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            JSONObject jsonObject = new JSONObject(response.getBody());
            String temperature = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("temp"));

            weatherDataDto.setServiceName(ServiceName.WEATHER_BIT);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setTemperature(temperature);

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_BIT, response.getBody());
        }
    }

    /**
     * Method getCityCoordinates executes an API request to obtain city coordinates.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Cacheable("weatherBitCityCoordinates")
    public WeatherDataDto getCityCoordinates(@NonNull String city, @NonNull String countryCode) {
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

            weatherDataDto.setServiceName(ServiceName.WEATHER_BIT);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setLatitude(latitude);
            weatherDataDto.setLongitude(longitude);

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_BIT, response.getBody());
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
    @Cacheable("weatherBitCityPressure")
    public WeatherDataDto getPressure(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            JSONObject jsonObject = new JSONObject(response.getBody());
            String pressure = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("pres"));

            weatherDataDto.setServiceName(ServiceName.WEATHER_BIT);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setPressure(pressure);

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_BIT, response.getBody());
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
    @Cacheable("weatherBitWindSpeed")
    public WeatherDataDto getWindSpeed(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            JSONObject jsonObject = new JSONObject(response.getBody());
            String windSpeed = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("wind_spd"));

            weatherDataDto.setServiceName(ServiceName.WEATHER_BIT);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setWindSpeed(windSpeed);

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_BIT, response.getBody());
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
    @Cacheable("weatherBitCityHumidity")
    public WeatherDataDto getHumidity(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();

            weatherDataDto.setServiceName(ServiceName.WEATHER_BIT);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setHumidity(constants.getMessageDoesNotSupportField());

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_BIT, response.getBody());
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
    @Cacheable("weatherBitFullWeather")
    public WeatherDataDto getFullWeather(@NonNull String city, @NonNull String countryCode) {
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

            weatherDataDto.setServiceName(ServiceName.WEATHER_BIT);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setTemperature(temperature);
            weatherDataDto.setPressure(pressure);
            weatherDataDto.setWindSpeed(windSpeed);
            weatherDataDto.setHumidity(constants.getMessageDoesNotSupportField());

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_BIT, response.getBody());
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
    @Cacheable("weatherBitSunriseTime")
    public WeatherDataDto getSunriseTime(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            JSONObject jsonObject = new JSONObject(response.getBody());
            String sunrise = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getString("sunrise"));

            weatherDataDto.setServiceName(ServiceName.WEATHER_BIT);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setSunrise(sunrise);

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_BIT, response.getBody());
        }
    }

    /**
     * Method getFeelsLikeTemperature executes an API request to obtain temperature how does it feel.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Cacheable("weatherBitFeelsLikeTemperature")
    public WeatherDataDto getFeelsLikeTemperature(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            JSONObject jsonObject = new JSONObject(response.getBody());
            String temperatureFeelsLike = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getDouble("app_temp"));

            weatherDataDto.setServiceName(ServiceName.WEATHER_BIT);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setSunrise(temperatureFeelsLike);

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_BIT, response.getBody());
        }
    }

    /**
     * Method getDirectionWind executes an API request to obtain direction wind data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Cacheable("weatherBitDirectionWind")
    public WeatherDataDto getDirectionWind(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            JSONObject jsonObject = new JSONObject(response.getBody());
            String windDir = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getString("wind_cdir_full"));

            weatherDataDto.setServiceName(ServiceName.WEATHER_BIT);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setDirectionWind(windDir);

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_BIT, response.getBody());
        }
    }

    /**
     * Method getWeatherDescription executes an API request to obtain weather description.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Cacheable("weatherBitWeatherDescription")
    public WeatherDataDto getWeatherDescription(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            WeatherDataDto weatherDataDto = new WeatherDataDto();
            JSONObject jsonObject = new JSONObject(response.getBody());
            String weatherDescription = String.valueOf(jsonObject.getJSONArray("data")
                    .getJSONObject(0)
                    .getJSONObject("weather")
                    .getString("description"));

            weatherDataDto.setServiceName(ServiceName.WEATHER_BIT);
            weatherDataDto.setName(city);
            weatherDataDto.setCountry(countryCode);
            weatherDataDto.setWeatherDescription(weatherDescription);

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_BIT, response.getBody());
        }
    }

    /**
     * Method getServiceName returns the serviceName of this WeatherService object.
     *
     * @return the serviceName (type ServiceName) of this WeatherService object.
     */
    @Override
    public ServiceName getServiceName() {
        return ServiceName.WEATHER_BIT;
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
