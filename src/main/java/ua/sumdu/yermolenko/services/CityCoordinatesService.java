package ua.sumdu.yermolenko.services;

import lombok.NonNull;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * Interface CityCoordinatesService is implemented by the class, which realizes the mechanism
 * for obtaining the coordinates of the city.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
public interface CityCoordinatesService {
    /**
     * Method getCityCoordinates returns city coordinates.
     *
     * @param city of type String
     * @param countryCode of type String
     * @return double[]
     * @throws HttpStatusCodeException when
     */
    double[] getCityCoordinates(@NonNull String city, @NonNull String countryCode) throws HttpStatusCodeException;
}
