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
import ua.sumdu.yermolenko.model.WeatherStackData;
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

    private final ResponseEntity<WeatherDataDto> RESPONSE_FAILED = new ResponseEntity<WeatherDataDto>(
            new WeatherDataDto(OPENWEATHERMAP_SERVICENAME,
                    "Response Failed. Server error."),
            HttpStatus.INTERNAL_SERVER_ERROR);
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
     * @return String
     */
    @Cacheable("weatherStackTemperature")
    public ResponseEntity<WeatherDataDto> getTemperature(@NonNull String city, @NonNull String countryCode) {
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

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody()), response.getStatusCode());
        }
    }

    /**
     * Method getCityCoordinates executes an API request to obtain city coordinates.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    /**
     * Method getCityCoordinates executes an API request to obtain data
     * on the coordinates of the city.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    @Cacheable("weatherStackCityCoordinates")
    public ResponseEntity<WeatherDataDto> getCityCoordinates(@NonNull String city, @NonNull String countryCode) {
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

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody()), response.getStatusCode());
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
    @Cacheable("weatherStackPressure")
    public ResponseEntity<WeatherDataDto> getPressure(@NonNull String city, @NonNull String countryCode) {
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

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody()), response.getStatusCode());
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
    @Cacheable("weatherStackWindSpeed")
    public ResponseEntity<WeatherDataDto> getWindSpeed(@NonNull String city, @NonNull String countryCode) {
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

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody()), response.getStatusCode());
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
    @Cacheable("weatherStackHumidity")
    public ResponseEntity<WeatherDataDto> getHumidity(@NonNull String city, @NonNull String countryCode) {
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

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody()), response.getStatusCode());
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
    @Cacheable("weatherStackFullWeather")
    public ResponseEntity<WeatherDataDto> getFullWeather(@NonNull String city, @NonNull String countryCode) {
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

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody()), response.getStatusCode());
        }
    }

    /**
     * Method getSunRiseTime executes an API request to obtain city sunrise time.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    @Override
    @Cacheable("weatherStackSunriseTime")
    public ResponseEntity<WeatherDataDto> getSunriseTime(@NonNull String city, @NonNull String countryCode) {
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

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody()), response.getStatusCode());
        }
    }

    /**
     * Method getFeelsLikeTemperature executes an API request to obtain temperature how does it feel.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    @Cacheable("weatherStackFeelsLikeTemperature")
    public ResponseEntity<WeatherDataDto> getFeelsLikeTemperature(@NonNull String city, @NonNull String countryCode) {
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

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody()), response.getStatusCode());
        }
    }

    /**
     * Method getDirectionWind executes an API request to obtain direction wind data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    @Cacheable("weatherStackDirectionWind")
    public ResponseEntity<WeatherDataDto> getDirectionWind(@NonNull String city, @NonNull String countryCode) {
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

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody()), response.getStatusCode());
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
    public ResponseEntity<WeatherDataDto> getWeatherDescription(@NonNull String city, @NonNull String countryCode) {
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

            return new ResponseEntity<>(weatherDataDto, response.getStatusCode());
        } else {
            return new ResponseEntity<WeatherDataDto>(new WeatherDataDto(WEATHERSTACK_SERVICENAME, response.getBody()), response.getStatusCode());
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
