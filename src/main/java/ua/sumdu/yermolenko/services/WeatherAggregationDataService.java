package ua.sumdu.yermolenko.services;

import lombok.NonNull;

/**
 * Interface WeatherAggregationDataService  is implemented by the class, which implements
 * a mechanism for aggregating weather data from different APIs.
 *
 * @author AndreyYermolenko
 * Created on 01.02.2020
 */
public interface WeatherAggregationDataService {
    /**
     * Method WeatherAggregationData returns weather data from different APIs.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return String
     */
    String WeatherAggregationData(@NonNull String city, @NonNull String countryCode);
}
