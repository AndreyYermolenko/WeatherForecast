package ua.sumdu.yermolenko.services;

import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import ua.sumdu.yermolenko.model.WeatherDataDto;

/**
 * Interface OpenWeatherMapService is implemented by the class, which realizes
 * a mechanism for receiving data from OpenWeatherMap API.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
public interface OpenWeatherMapService {
    /**
     * Method getTemperature executes an API request to obtain temperature data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> getTemperature(@NonNull String city, @NonNull String countryCode);

    /**
     * Method getCityCoordinates executes an API request to obtain city coordinates.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> getCityCoordinates(@NonNull String city, @NonNull String countryCode);

    /**
     * Method getPressure executes an API request to obtain pressure data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> getPressure(@NonNull String city, @NonNull String countryCode);

    /**
     * Method getWindSpeed executes an API request to obtain wind speed data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> getWindSpeed(@NonNull String city, @NonNull String countryCode);

    /**
     * Method getHumidity executes an API request to obtain humidity data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> getHumidity(@NonNull String city, @NonNull String countryCode);

    /**
     * Method getFullWeather executes an API request to obtain current weather data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> getFullWeather(@NonNull String city, @NonNull String countryCode);

    /**
     * Method getSunRiseTime executes an API request to obtain city sunrise time.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> getSunriseTime(@NonNull String city, @NonNull String countryCode);

    /**
     * Method getDirectionWind executes an API request to obtain direction wind data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> getDirectionWind(@NonNull String city, @NonNull String countryCode);

    /**
     * Method getFeelsLikeTemperature executes an API request to obtain temperature how does it feel.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> getFeelsLikeTemperature(@NonNull String city, @NonNull String countryCode);

    /**
     * Method getWeatherDescription executes an API request to obtain weather description.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> getWeatherDescription(@NonNull String city, @NonNull String countryCode);
}
