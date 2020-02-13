package ua.sumdu.yermolenko.model;

import lombok.Data;

import java.util.ArrayList;

/**
 * Class WeatherDataArr stores weather data.
 *
 * @author AndreyYermolenko
 * Created on 07.02.2020
 */
@Data
public class WeatherDataJson extends WeatherDataFormat {
    private ArrayList<WeatherDataDto> weatherDataList;

    public WeatherDataJson() {
    }

    public WeatherDataJson(ArrayList<WeatherDataDto> weatherDataList) {
        this.weatherDataList = weatherDataList;
    }
}
