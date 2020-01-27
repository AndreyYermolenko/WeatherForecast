package ua.sumdu.yermolenko.services.interfaces;

import lombok.NonNull;

public interface DarkSkyService {
    String darkSkyCurrentWeather(@NonNull String city, @NonNull String countryCode);
}
