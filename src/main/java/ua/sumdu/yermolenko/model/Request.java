package ua.sumdu.yermolenko.model;

import lombok.Data;

/**
 * Class Request is used for parsing WeatherStack API data using Jackson.
 *
 * @author AndreyYermolenko
 * Created on 07.02.2020
 */
@Data
public class Request {
    private String type;
    private String query;
    private String language;
    private String unit;
}
