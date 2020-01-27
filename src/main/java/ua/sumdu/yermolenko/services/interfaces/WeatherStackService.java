package ua.sumdu.yermolenko.services.interfaces;

import lombok.NonNull;

public interface WeatherStackService {
    String weatherStackCurrentWeather(@NonNull String city, @NonNull String countryCode);
}
