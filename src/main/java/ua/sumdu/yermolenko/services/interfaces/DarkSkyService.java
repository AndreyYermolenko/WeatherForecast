package ua.sumdu.yermolenko.services.interfaces;

import lombok.NonNull;

public interface DarkSkyService {
    String currentWeather(@NonNull String city, @NonNull String countryCode);
}
