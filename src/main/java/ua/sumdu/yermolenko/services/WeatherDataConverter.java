package ua.sumdu.yermolenko.services;

import org.springframework.stereotype.Component;
import ua.sumdu.yermolenko.model.WeatherData;
import ua.sumdu.yermolenko.model.WeatherDataDto;

@Component
public class WeatherDataConverter {
    public WeatherDataDto toJsonWeatherDataConverter(WeatherData inputData) {
        WeatherDataDto outputData = new WeatherDataDto();

        outputData.setName(inputData.getLocation().getName());
        outputData.setCountry(inputData.getLocation().getCountry());
        outputData.setTemperature(inputData.getCurrent().getTemperature());

        return outputData;
    }
}