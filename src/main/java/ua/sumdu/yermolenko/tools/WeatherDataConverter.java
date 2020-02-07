package ua.sumdu.yermolenko.tools;

import org.springframework.stereotype.Component;
import ua.sumdu.yermolenko.model.WeatherDataDto;
import ua.sumdu.yermolenko.model.WeatherStackData;

import static ua.sumdu.yermolenko.services.ServiceConstants.WEATHERSTACK_SERVICENAME;

/**
 * Class WeatherDataConverter is used for parsing weather API data.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
@Component
public class WeatherDataConverter {

    /**
     * Method toJsonTemperatureConvert Method toJsonHumidityConvert converts temperature data from WeatherStack API.
     *
     * @param inputData of type WeatherStackData
     * @return WeatherDataDto
     */
    public WeatherDataDto toJsonTemperatureConvert(WeatherStackData inputData) {
        WeatherDataDto outputData = new WeatherDataDto();

        outputData.setServiceName(WEATHERSTACK_SERVICENAME);
        outputData.setName(inputData.getLocation().getName());
        outputData.setCountry(inputData.getLocation().getCountry());
        outputData.setTemperature(inputData.getCurrent().getTemperature());

        return outputData;
    }

    /**
     * Method toJsonCityCoordinatesConvert Method toJsonHumidityConvert converts city coordinates data from WeatherStack API.
     *
     * @param inputData of type WeatherStackData
     * @return WeatherDataDto
     */
    public WeatherDataDto toJsonCityCoordinatesConvert(WeatherStackData inputData) {
        WeatherDataDto outputData = new WeatherDataDto();

        outputData.setServiceName(WEATHERSTACK_SERVICENAME);
        outputData.setName(inputData.getLocation().getName());
        outputData.setCountry(inputData.getLocation().getCountry());
        outputData.setLatitude(inputData.getLocation().getLat());
        outputData.setLongitude(inputData.getLocation().getLon());

        return outputData;
    }

    /**
     * Method toJsonPressureConvert Method toJsonHumidityConvert converts pressure data from WeatherStack API.
     *
     * @param inputData of type WeatherStackData
     * @return WeatherDataDto
     */
    public WeatherDataDto toJsonPressureConvert(WeatherStackData inputData) {
            WeatherDataDto outputData = new WeatherDataDto();

            outputData.setServiceName(WEATHERSTACK_SERVICENAME);
            outputData.setName(inputData.getLocation().getName());
            outputData.setCountry(inputData.getLocation().getCountry());
            outputData.setPressure(inputData.getCurrent().getPressure());

            return outputData;
    }

    /**
     * Method toJsonWindSpeedConvert Method toJsonHumidityConvert converts wind speed data from WeatherStack API.
     *
     * @param inputData of type WeatherStackData
     * @return WeatherDataDto
     */
    public WeatherDataDto toJsonWindSpeedConvert(WeatherStackData inputData) {
        WeatherDataDto outputData = new WeatherDataDto();
        double windSpeedMetersPerSecond = Double.parseDouble(inputData.getCurrent().getWindSpeed()) / 3.6;

        outputData.setServiceName(WEATHERSTACK_SERVICENAME);
        outputData.setName(inputData.getLocation().getName());
        outputData.setCountry(inputData.getLocation().getCountry());
        outputData.setWindSpeed(String.valueOf(windSpeedMetersPerSecond));

        return outputData;
    }

    /**
     * Method toJsonHumidityConvert converts humidity data from WeatherStack API.
     *
     * @param inputData of type WeatherStackData
     * @return WeatherDataDto
     */
    public WeatherDataDto toJsonHumidityConvert(WeatherStackData inputData) {
        WeatherDataDto outputData = new WeatherDataDto();

        outputData.setServiceName(WEATHERSTACK_SERVICENAME);
        outputData.setName(inputData.getLocation().getName());
        outputData.setCountry(inputData.getLocation().getCountry());
        outputData.setHumidity(inputData.getCurrent().getHumidity());

        return outputData;
    }

    /**
     * Method toJsonFullWeatherConvert converts full weather from WeatherStack API.
     *
     * @param inputData of type WeatherStackData
     * @return WeatherDataDto
     */
    public WeatherDataDto toJsonFullWeatherConvert(WeatherStackData inputData) {
        WeatherDataDto outputData = new WeatherDataDto();
        double windSpeedMetersPerSecond = Double.parseDouble(inputData.getCurrent().getWindSpeed()) / 3.6;

        outputData.setServiceName(WEATHERSTACK_SERVICENAME);
        outputData.setName(inputData.getLocation().getName());
        outputData.setCountry(inputData.getLocation().getCountry());
        outputData.setTemperature(inputData.getCurrent().getTemperature());
        outputData.setPressure(inputData.getCurrent().getPressure());
        outputData.setWindSpeed(String.valueOf(windSpeedMetersPerSecond));
        outputData.setHumidity(inputData.getCurrent().getHumidity());

        return outputData;
    }

    /**
     * Method toJsonSunriseTimeConvert converts sunrise time from WeatherStack API.
     *
     * @param inputData of type WeatherStackData
     * @return WeatherDataDto
     */
    public WeatherDataDto toJsonSunriseTimeConvert(WeatherStackData inputData) {
        WeatherDataDto outputData = new WeatherDataDto();

        outputData.setServiceName(WEATHERSTACK_SERVICENAME);
        outputData.setName(inputData.getLocation().getName());
        outputData.setCountry(inputData.getLocation().getCountry());
        outputData.setSunrise(inputData.getCurrent().getObservationTime());

        return outputData;
    }

    /**
     * Method toJsonTemperatureFeelsLikeConvert converts temperature how does it feel from WeatherStack API.
     *
     * @param inputData of type WeatherStackData
     * @return WeatherDataDto
     */
    public WeatherDataDto toJsonTemperatureFeelsLikeConvert(WeatherStackData inputData) {
        WeatherDataDto outputData = new WeatherDataDto();

        outputData.setServiceName(WEATHERSTACK_SERVICENAME);
        outputData.setName(inputData.getLocation().getName());
        outputData.setCountry(inputData.getLocation().getCountry());
        outputData.setTemperatureFeelsLike(inputData.getCurrent().getFeelslike());

        return outputData;
    }

    /**
     * Method toJsonDirectionWindConvert converts direction wind from WeatherStack API.
     *
     * @param inputData of type WeatherStackData
     * @return WeatherDataDto
     */
    public WeatherDataDto toJsonDirectionWindConvert(WeatherStackData inputData) {
        WeatherDataDto outputData = new WeatherDataDto();

        outputData.setServiceName(WEATHERSTACK_SERVICENAME);
        outputData.setName(inputData.getLocation().getName());
        outputData.setCountry(inputData.getLocation().getCountry());
        outputData.setDirectionWind(inputData.getCurrent().getWindDir());

        return outputData;
    }

    /**
     * Method toJsonWeatherDescriptionConvert converts direction wind from WeatherStack API.
     *
     * @param inputData of type WeatherStackData
     * @return WeatherDataDto
     */
    public WeatherDataDto toJsonWeatherDescriptionConvert(WeatherStackData inputData) {
        WeatherDataDto outputData = new WeatherDataDto();

        outputData.setServiceName(WEATHERSTACK_SERVICENAME);
        outputData.setName(inputData.getLocation().getName());
        outputData.setCountry(inputData.getLocation().getCountry());
        outputData.setWeatherDescription(inputData.getCurrent().getWeatherDescriptions()[0]);

        return outputData;
    }
}
