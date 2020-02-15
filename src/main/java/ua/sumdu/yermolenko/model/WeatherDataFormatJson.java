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
public class WeatherDataFormatJson implements WeatherDataFormat {
    private ArrayList<WeatherDataDto> weatherDataList;

    public WeatherDataFormatJson() {
    }

    public WeatherDataFormatJson(ArrayList<WeatherDataDto> weatherDataList) {
        this.weatherDataList = weatherDataList;
    }
}
