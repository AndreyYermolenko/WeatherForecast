package ua.sumdu.yermolenko.services;

import lombok.NonNull;
import ua.sumdu.yermolenko.model.WeatherDataFormat;

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
     * @return WeatherDataFormat
     */
    WeatherDataFormat temperatureAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);

//    /**
//     * Method WeatherAggregationData returns city coordinates from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    WeatherDataFormat cityCoordinatesAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);
//
//    /**
//     * Method WeatherAggregationData returns pressure data from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    WeatherDataFormat pressureAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);
//
//    /**
//     * Method WeatherAggregationData returns wind speed data from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    WeatherDataFormat windSpeedAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);
//
//    /**
//     * Method WeatherAggregationData returns humidity data from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    WeatherDataFormat humidityAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);
//
//    /**
//     * Method WeatherAggregationData returns weather data from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    WeatherDataFormat fullWeatherAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);
//
//    /**
//     * Method WeatherAggregationData returns city sunrise time from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    WeatherDataFormat sunriseTimeAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);
//
//    /**
//     * Method WeatherAggregationData returns direction wind from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    WeatherDataFormat directionWindAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);
//
//    /**
//     * Method WeatherAggregationData returns temperature how does it feel from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    WeatherDataFormat feelsLikeTemperatureAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);
//
//    /**
//     * Method WeatherAggregationData returns weather description from different APIs.
//     *
//     * @param city of type String
//     * @param countryCode of type String
//     * @param ext of type String
//     * @return WeatherDataFormat
//     */
//    WeatherDataFormat weatherDescriptionAggregation(@NonNull String city, @NonNull String countryCode, @NonNull String ext);
}
