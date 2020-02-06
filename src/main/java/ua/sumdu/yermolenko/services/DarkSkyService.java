package ua.sumdu.yermolenko.services;

import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import ua.sumdu.yermolenko.model.WeatherDataDto;

/**
 * Interface DarkSkyService is implemented by the class, which realizes
 * a mechanism for receiving data from DarkSky API.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
public interface DarkSkyService {
    /**
     * Method getTemperatureThread executes an API request in a separate thread
     * to obtain temperature data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    ResponseEntity<WeatherDataDto> getTemperature(@NonNull String city, @NonNull String countryCode);
    /**
     * Method getCityCoordinatesThread executes an API request in a separate thread
     * to obtain data on the coordinates of the city.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    ResponseEntity<WeatherDataDto> getCityCoordinates(@NonNull String city, @NonNull String countryCode);
    /**
     * Method getFullWeather executes an API request to obtain current weather data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    ResponseEntity<WeatherDataDto> getFullWeather(@NonNull String city, @NonNull String countryCode);

    ResponseEntity<WeatherDataDto> getPressure(@NonNull String city, @NonNull String countryCode);

    ResponseEntity<WeatherDataDto> getWindSpeed(@NonNull String city, @NonNull String countryCode);

    ResponseEntity<WeatherDataDto> getHumidity(@NonNull String city, @NonNull String countryCode);
}
