package ua.sumdu.yermolenko.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.model.weatherstack.WeatherStackData;
import ua.sumdu.yermolenko.services.WeatherStackService;
import ua.sumdu.yermolenko.tools.WeatherDataConverter;

import java.io.IOException;

import static ua.sumdu.yermolenko.services.ServiceConstants.OPENWEATHERMAP_SERVICENAME;
import static ua.sumdu.yermolenko.services.ServiceConstants.WEATHERSTACK_SERVICENAME;

/**
 * Class WeatherStackServiceImpl implements interface WeatherStackService.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
@Service
public class WeatherStackServiceImpl implements WeatherStackService {
    private final static Logger logger = LogManager.getLogger(WeatherStackServiceImpl.class);

    private final WeatherDataDto RESPONSE_FAILED = new WeatherDataDto(OPENWEATHERMAP_SERVICENAME,
                    "Response Failed. Server error.");
    private final String PROBLEM_MESSAGE = "OpenWeatherMapService problem";

    @Value("${weatherstack.api.key}")
    private String apiKey;
    @Value("${weatherstack.url}")
    private String url;
    @Autowired
    private WeatherDataConverter weatherDataConverter;

    /**
     * Method getTemperature executes an API request to obtain temperature data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Cacheable("weatherStackTemperature")
    public WeatherDataDto getTemperature(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherStackData weatherData;
            WeatherDataDto weatherDataDto;
            try {
                weatherData = objectMapper.readValue(response.getBody(), WeatherStackData.class);
                weatherDataDto = weatherDataConverter.toJsonTemperatureConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                logger.error(PROBLEM_MESSAGE, e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody());
        }
    }

    /**
     * Method getCityCoordinates executes an API request to obtain city coordinates.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Cacheable("weatherStackCityCoordinates")
    public WeatherDataDto getCityCoordinates(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherStackData weatherData;
            WeatherDataDto weatherDataDto;
            try {
                weatherData = objectMapper.readValue(response.getBody(), WeatherStackData.class);
                weatherDataDto = weatherDataConverter.toJsonCityCoordinatesConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                logger.error(PROBLEM_MESSAGE, e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody());
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
    @Cacheable("weatherStackPressure")
    public WeatherDataDto getPressure(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherStackData weatherData;
            WeatherDataDto weatherDataDto;
            try {
                weatherData = objectMapper.readValue(response.getBody(), WeatherStackData.class);
                weatherDataDto = weatherDataConverter.toJsonPressureConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                logger.error(PROBLEM_MESSAGE, e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody());
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
    @Cacheable("weatherStackWindSpeed")
    public WeatherDataDto getWindSpeed(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherStackData weatherData;
            WeatherDataDto weatherDataDto;
            try {
                weatherData = objectMapper.readValue(response.getBody(), WeatherStackData.class);
                weatherDataDto = weatherDataConverter.toJsonWindSpeedConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                logger.error(PROBLEM_MESSAGE, e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody());
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
    @Cacheable("weatherStackHumidity")
    public WeatherDataDto getHumidity(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherStackData weatherData;
            WeatherDataDto weatherDataDto;
            try {
                weatherData = objectMapper.readValue(response.getBody(), WeatherStackData.class);
                weatherDataDto = weatherDataConverter.toJsonHumidityConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                logger.error(PROBLEM_MESSAGE, e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody());
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
    @Cacheable("weatherStackFullWeather")
    public WeatherDataDto getFullWeather(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherStackData weatherData;
            WeatherDataDto weatherDataDto;
            try {
                weatherData = objectMapper.readValue(response.getBody(), WeatherStackData.class);
                weatherDataDto = weatherDataConverter.toJsonFullWeatherConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                logger.error(PROBLEM_MESSAGE, e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody());
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
    @Cacheable("weatherStackSunriseTime")
    public WeatherDataDto getSunriseTime(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherStackData weatherData;
            WeatherDataDto weatherDataDto;
            try {
                weatherData = objectMapper.readValue(response.getBody(), WeatherStackData.class);
                weatherDataDto = weatherDataConverter.toJsonSunriseTimeConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                logger.error(PROBLEM_MESSAGE, e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody());
        }
    }

    /**
     * Method getFeelsLikeTemperature executes an API request to obtain temperature how does it feel.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Cacheable("weatherStackFeelsLikeTemperature")
    public WeatherDataDto getFeelsLikeTemperature(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherStackData weatherData;
            WeatherDataDto weatherDataDto;
            try {
                weatherData = objectMapper.readValue(response.getBody(), WeatherStackData.class);
                weatherDataDto = weatherDataConverter.toJsonTemperatureFeelsLikeConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                logger.error(PROBLEM_MESSAGE, e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody());
        }
    }

    /**
     * Method getDirectionWind executes an API request to obtain direction wind data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return WeatherDataDto
     */
    @Cacheable("weatherStackDirectionWind")
    public WeatherDataDto getDirectionWind(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherStackData weatherData;
            WeatherDataDto weatherDataDto;
            try {
                weatherData = objectMapper.readValue(response.getBody(), WeatherStackData.class);
                weatherDataDto = weatherDataConverter.toJsonDirectionWindConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                logger.error(PROBLEM_MESSAGE, e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody());
        }
    }

    /**
     * Method getWeatherDescription executes an API request to obtain weather description.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    @Cacheable("weatherStackWeatherDescription")
    public WeatherDataDto getWeatherDescription(@NonNull String city, @NonNull String countryCode) {
        ResponseEntity<String> response = getWeather(city, countryCode);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherStackData weatherData;
            WeatherDataDto weatherDataDto;
            try {
                weatherData = objectMapper.readValue(response.getBody(), WeatherStackData.class);
                weatherDataDto = weatherDataConverter.toJsonWeatherDescriptionConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                logger.error(PROBLEM_MESSAGE, e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody());
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
                apiKey, city, countryCode
        );
        return response;
    }
}
