package ua.sumdu.yermolenko.services.interfaces;

import lombok.NonNull;

public interface OpenWeatherMapService {
    String openWeatherMapCurrentWeather(@NonNull String city, @NonNull String countryCode);
}
