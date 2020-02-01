package ua.sumdu.yermolenko.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.sumdu.yermolenko.model.WeatherStackData;
import ua.sumdu.yermolenko.model.WeatherDataDto;

/**
 * Class WeatherDataConverter is used for parsing weather API data.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
@Component
public class WeatherDataConverter {
    @Value("${servicename.weatherstack}")
    private String serviceName;

    /**
     * Method toJsonWeatherStackDataConvert converts WeatherStack API data.
     *
     * @param inputData of type WeatherStackData
     * @return WeatherDataDto
     */
    public WeatherDataDto toJsonWeatherStackDataConvert(WeatherStackData inputData) {
        WeatherDataDto outputData = new WeatherDataDto();
        double windSpeedMetersPerSecond = Double.parseDouble(inputData.getCurrent().getWindSpeed())/3.6;

        outputData.setServiceName(serviceName);
        outputData.setName(inputData.getLocation().getName());
        outputData.setCountry(inputData.getLocation().getCountry());
        outputData.setTemperature(inputData.getCurrent().getTemperature());
        outputData.setPressure(inputData.getCurrent().getPressure());
        outputData.setWindSpeed(String.valueOf(windSpeedMetersPerSecond));
        outputData.setLatitude(inputData.getLocation().getLat());
        outputData.setLongitude(inputData.getLocation().getLon());

        return outputData;
    }
}
