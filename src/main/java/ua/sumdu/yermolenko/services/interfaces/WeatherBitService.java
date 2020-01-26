package ua.sumdu.yermolenko.services.interfaces;

import lombok.NonNull;

import java.util.concurrent.Future;

public interface WeatherBitService {
    Future<String> currentWeather(@NonNull String city, @NonNull String countryCode);
}