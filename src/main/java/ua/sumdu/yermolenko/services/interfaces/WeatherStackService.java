package ua.sumdu.yermolenko.services.interfaces;

import lombok.NonNull;

public interface WeatherStackService {
    String currentWeather(@NonNull String city);
}
