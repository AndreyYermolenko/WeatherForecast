package ua.sumdu.yermolenko.services.interfaces;

import lombok.NonNull;

public interface WeatherBitService {
    String weatherBitCurrentWeatherThread(@NonNull String city, @NonNull String countryCode);
    String currentWeather(@NonNull String city, @NonNull String countryCode);
}
