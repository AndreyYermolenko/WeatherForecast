package ua.sumdu.yermolenko.model;

import lombok.Data;

/**
 * Class WeatherDataDto stores weather data.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
@Data
public class WeatherDataDto {
    private String serviceName;
    private String name;
    private String country;
    private String temperature;
    private String latitude;
    private String longitude;
    private String pressure;
    private String windSpeed;

    /**
     * Method toJsonTemperature returns temperature data in the Json format.
     * @return String
     */
    public String toJsonTemperature() {
        return "{\"" + serviceName + "\":" +
                "{" +
                "\"name\": " + "\"" + name + "\"," +
                "\"country\":" + "\"" + country + "\"," +
                "\"temperature\":" + "\"" + temperature + "\"" +
                "}" +
                "}";
    }

    /**
     * Method toJsonCoordinates returns city coordinates in the Json format.
     * @return String
     */
    public String toJsonCoordinates() {
        return "{\"" + serviceName + "\":" +
                "{" +
                "\"name\": " + "\"" + name + "\"," +
                "\"country\":" + "\"" + country + "\"," +
                "\"latitude\":" + "\"" + latitude + "\"," +
                "\"longitude\":" + "\"" + longitude + "\"" +
                "}" +
                "}";
    }

    /**
     * Method toJsonFullWeather returns full weather data in the Json format.
     * @return String
     */
    public String toJsonFullWeather() {
        return "{\"" + serviceName + "\":" +
                "{" +
                "\"name\": " + "\"" + name + "\"," +
                "\"country\":" + "\"" + country + "\"," +
                "\"temperature\":" + "\"" + temperature + "\"," +
                "\"pressure\":" + "\"" + pressure + "\"," +
                "\"wind_speed\":" + "\"" + windSpeed + "\"" +
                "}" +
                "}";
    }
}
