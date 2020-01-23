package ua.sumdu.yermolenko.services.interfaces;

import lombok.NonNull;
import org.springframework.web.client.HttpStatusCodeException;

public interface CityCoordinatesService {
    double[] getCityCoordinates(@NonNull String city, @NonNull String countryCode) throws HttpStatusCodeException;
}
