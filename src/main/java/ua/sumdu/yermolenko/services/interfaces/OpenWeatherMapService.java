package ua.sumdu.yermolenko.services.interfaces;

import lombok.NonNull;

public interface OpenWeatherMapService {
    String openWeatherMapCurrentWeatherThread(@NonNull String city, @NonNull String countryCode);
    String currentWeather(@NonNull String city, @NonNull String countryCode);
}
