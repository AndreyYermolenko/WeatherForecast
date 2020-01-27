package ua.sumdu.yermolenko.services.interfaces;

import lombok.NonNull;

public interface WeatherBitService {
    String weatherBitCurrentWeather(@NonNull String city, @NonNull String countryCode);
}
