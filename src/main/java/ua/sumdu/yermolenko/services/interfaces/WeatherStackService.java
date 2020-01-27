package ua.sumdu.yermolenko.services.interfaces;

import lombok.NonNull;

public interface WeatherStackService {
    String weatherStackCurrentWeatherThread(@NonNull String city, @NonNull String countryCode);
    String currentWeather(@NonNull String city, @NonNull String countryCode);
}
