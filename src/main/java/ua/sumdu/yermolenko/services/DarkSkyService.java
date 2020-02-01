package ua.sumdu.yermolenko.services;

import lombok.NonNull;

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
    String getTemperatureThread(@NonNull String city, @NonNull String countryCode);
    /**
     * Method getCityCoordinatesThread executes an API request in a separate thread
     * to obtain data on the coordinates of the city.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    String getCityCoordinatesThread(@NonNull String city, @NonNull String countryCode);
    /**
     * Method getFullWeatherThread executes an API request in a separate thread
     * to obtain current weather data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    String getFullWeatherThread(@NonNull String city, @NonNull String countryCode);
    /**
     * Method getFullWeather executes an API request to obtain current weather data.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    String getFullWeather(@NonNull String city, @NonNull String countryCode);
}
