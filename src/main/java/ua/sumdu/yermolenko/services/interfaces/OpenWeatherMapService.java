package ua.sumdu.yermolenko.services.interfaces;

import lombok.NonNull;

public interface OpenWeatherMapService {
    String currentWeather(@NonNull String city, @NonNull String countryCode);
}
