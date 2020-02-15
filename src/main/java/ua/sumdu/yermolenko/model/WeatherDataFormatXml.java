package ua.sumdu.yermolenko.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Class WeatherDataArr stores weather data.
 *
 * @author AndreyYermolenko
 * Created on 07.02.2020
 */
@Data
@XmlRootElement(name = "weatherData")
public class WeatherDataFormatXml implements WeatherDataFormat {
    private WeatherDataDto[] weatherAPI;

    public WeatherDataFormatXml() {
    }

    public WeatherDataFormatXml(ArrayList<WeatherDataDto> weatherDataList) {
        this.weatherAPI = listToArr(weatherDataList);
    }

    private WeatherDataDto[] listToArr(ArrayList<WeatherDataDto> list) {
        WeatherDataDto[] arr = new WeatherDataDto[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }
}
