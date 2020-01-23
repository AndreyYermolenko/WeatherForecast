package ua.sumdu.yermolenko.services.interfaces;

import lombok.NonNull;

public interface CityCoordinatesService {
    double[] getCityCoordinates(@NonNull String city, @NonNull String countryCode);
}
