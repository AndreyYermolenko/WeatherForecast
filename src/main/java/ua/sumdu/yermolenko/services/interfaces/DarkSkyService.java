package ua.sumdu.yermolenko.services.interfaces;

import lombok.NonNull;

public interface DarkSkyService {
    String darkSkyCurrentWeatherThread(@NonNull String city, @NonNull String countryCode);
    String currentWeather(@NonNull String city, @NonNull String countryCode);
}
