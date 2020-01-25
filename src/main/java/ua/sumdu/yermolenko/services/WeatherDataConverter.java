package ua.sumdu.yermolenko.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.sumdu.yermolenko.model.WeatherStackData;
import ua.sumdu.yermolenko.model.WeatherDataDto;

@Component
public class WeatherDataConverter {
    @Value("${servicename.weatherstack}")
    private String serviceName;

    public WeatherDataDto toJsonWeatherStackDataConvert(WeatherStackData inputData) {
        WeatherDataDto outputData = new WeatherDataDto();

        outputData.setServiceName(serviceName);
        outputData.setName(inputData.getLocation().getName());
        outputData.setCountry(inputData.getLocation().getCountry());
        outputData.setTemperature(inputData.getCurrent().getTemperature());

        return outputData;
    }
}
