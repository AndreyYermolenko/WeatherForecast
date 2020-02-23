package ua.sumdu.yermolenko.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.model.weatherstack.WeatherStackData;
import ua.sumdu.yermolenko.services.ServiceName;
import ua.sumdu.yermolenko.services.WeatherService;
import ua.sumdu.yermolenko.tools.WeatherDataConverter;

import java.io.IOException;

/**
 * Class WeatherStackServiceImpl implements interface WeatherStackService.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
@Service
public class WeatherStackWeatherServiceImpl implements WeatherService {
    private final static Logger LOGGER = LogManager.getLogger(WeatherStackWeatherServiceImpl.class);
    private final WeatherDataDto RESPONSE_FAILED = new WeatherDataDto(ServiceName.WEATHER_STACK,
                    "Response Failed. Server error.");

    @Value("${weatherstack.api.key}")
    private String apiKey;
    @Value("${weatherstack.url}")
    private String url;
    private final WeatherDataConverter weatherDataConverter;

    public WeatherStackWeatherServiceImpl(WeatherDataConverter weatherDataConverter) {
        this.weatherDataConverter = weatherDataConverter;
    }

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
                weatherDataDto = weatherDataConverter.toDTOTemperatureConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                LOGGER.error("Input parameters: " + city + ", " + countryCode + ".", e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_STACK, response.getBody());
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
                weatherDataDto = weatherDataConverter.toDTOCityCoordinatesConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                LOGGER.error("Input parameters: " + city + ", " + countryCode + ".", e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_STACK, response.getBody());
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
                weatherDataDto = weatherDataConverter.toDTOPressureConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                LOGGER.error("Input parameters: " + city + ", " + countryCode + ".", e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_STACK, response.getBody());
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
                weatherDataDto = weatherDataConverter.toDTOWindSpeedConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                LOGGER.error("Input parameters: " + city + ", " + countryCode + ".", e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_STACK, response.getBody());
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
                weatherDataDto = weatherDataConverter.toDTOHumidityConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                LOGGER.error("Input parameters: " + city + ", " + countryCode + ".", e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_STACK, response.getBody());
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
                weatherDataDto = weatherDataConverter.toDTOFullWeatherConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                LOGGER.error("Input parameters: " + city + ", " + countryCode + ".", e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_STACK, response.getBody());
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
                weatherDataDto = weatherDataConverter.toDTOSunriseTimeConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                LOGGER.error("Input parameters: " + city + ", " + countryCode + ".", e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_STACK, response.getBody());
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
                LOGGER.error("Input parameters: " + city + ", " + countryCode + ".", e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_STACK, response.getBody());
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
                LOGGER.error("Input parameters: " + city + ", " + countryCode + ".", e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_STACK, response.getBody());
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
                weatherDataDto = weatherDataConverter.toDTOWeatherDescriptionConvert(weatherData);
                weatherDataDto.setCountry(countryCode);
            } catch (IOException e) {
                LOGGER.error("Input parameters: " + city + ", " + countryCode + ".", e);
                return RESPONSE_FAILED;
            }

            return weatherDataDto;
        } else {
            return new WeatherDataDto(ServiceName.WEATHER_STACK, response.getBody());
        }
    }

    /**
     * Method getServiceName returns the serviceName of this WeatherService object.
     *
     * @return the serviceName (type ServiceName) of this WeatherService object.
     */
    @Override
    public ServiceName getServiceName() {
        return ServiceName.WEATHER_STACK;
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
