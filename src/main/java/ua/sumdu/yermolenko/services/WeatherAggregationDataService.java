package ua.sumdu.yermolenko.services;

import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import ua.sumdu.yermolenko.model.WeatherDataDto;

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
     * @param ext of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> temperatureAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);

    /**
     * Method WeatherAggregationData returns city coordinates from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> cityCoordinatesAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);

    /**
     * Method WeatherAggregationData returns pressure data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> pressureAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);

    /**
     * Method WeatherAggregationData returns wind speed data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> windSpeedAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);

    /**
     * Method WeatherAggregationData returns humidity data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> humidityAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);

    /**
     * Method WeatherAggregationData returns weather data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> fullWeatherAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);

    /**
     * Method WeatherAggregationData returns city sunrise time from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> sunriseTimeAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);

    /**
     * Method WeatherAggregationData returns direction wind from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> directionWindAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);

    /**
     * Method WeatherAggregationData returns temperature how does it feel from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> feelsLikeTemperatureAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);

    /**
     * Method WeatherAggregationData returns weather description from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @param ext of type String
     * @return ResponseEntity<WeatherDataDto>
     */
    ResponseEntity<WeatherDataDto> weatherDescriptionAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);
}
