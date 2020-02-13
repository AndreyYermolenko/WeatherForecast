package ua.sumdu.yermolenko.model.weatherstack;

import lombok.Data;

/**
 * Class WeatherStackData is used for parsing WeatherStack API data using Jackson.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
@Data
public class WeatherStackData {
    private Request request;
    private Location location;
    private Current current;
}
