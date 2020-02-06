package ua.sumdu.yermolenko.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class WeatherDataArr stores weather data.
 *
 * @author AndreyYermolenko
 * Created on 07.02.2020
 */
@Data
@XmlRootElement(name = "weatherDataArr")
public class WeatherDataArr {
    private WeatherDataDto[] weatherDataDto;

    public WeatherDataArr() {
    }

    public WeatherDataArr(WeatherDataDto[] weatherDataDto) {
        this.weatherDataDto = weatherDataDto;
    }
}
