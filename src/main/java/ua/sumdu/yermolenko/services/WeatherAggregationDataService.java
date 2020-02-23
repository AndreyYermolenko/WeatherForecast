package ua.sumdu.yermolenko.services;

import lombok.NonNull;
import ua.sumdu.yermolenko.model.WeatherDataDto;

import java.util.ArrayList;

/**
 * Interface WeatherAggregationDataService  is implemented by the class, which implements
 * a mechanism for aggregating weather data from different APIs.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
public interface WeatherAggregationDataService {
    /**
     * Method WeatherAggregationData returns temperature data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    ArrayList<WeatherDataDto> temperatureAggregation(@NonNull String city, @NonNull String countryCode);

    /**
     * Method WeatherAggregationData returns city coordinates from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    ArrayList<WeatherDataDto> cityCoordinatesAggregation(@NonNull String city, @NonNull String countryCode);

    /**
     * Method WeatherAggregationData returns pressure data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    ArrayList<WeatherDataDto> pressureAggregation(@NonNull String city, @NonNull String countryCode);

    /**
     * Method WeatherAggregationData returns wind speed data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    ArrayList<WeatherDataDto> windSpeedAggregation(@NonNull String city, @NonNull String countryCode);

    /**
     * Method WeatherAggregationData returns humidity data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    ArrayList<WeatherDataDto> humidityAggregation(@NonNull String city, @NonNull String countryCode);

    /**
     * Method WeatherAggregationData returns weather data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    ArrayList<WeatherDataDto> fullWeatherAggregation(@NonNull String city, @NonNull String countryCode);

    /**
     * Method WeatherAggregationData returns city sunrise time from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    ArrayList<WeatherDataDto> sunriseTimeAggregation(@NonNull String city, @NonNull String countryCode);

    /**
     * Method WeatherAggregationData returns direction wind from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    ArrayList<WeatherDataDto> directionWindAggregation(@NonNull String city, @NonNull String countryCode);

    /**
     * Method WeatherAggregationData returns temperature how does it feel from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    ArrayList<WeatherDataDto> feelsLikeTemperatureAggregation(@NonNull String city, @NonNull String countryCode);

    /**
     * Method WeatherAggregationData returns weather description from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return ArrayList<WeatherDataDto>
     */
    ArrayList<WeatherDataDto> weatherDescriptionAggregation(@NonNull String city, @NonNull String countryCode);
}
