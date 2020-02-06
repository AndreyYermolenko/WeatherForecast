package ua.sumdu.yermolenko.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.xml.bind.annotation.XmlType;

/**
 * Class WeatherDataDto stores weather data.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlType(propOrder = { "serviceName", "name", "country", "temperature", "latitude",
        "longitude", "pressure", "windSpeed", "humidity", "exceptionMessage" })
public class WeatherDataDto {
    private String serviceName;
    private String name;
    private String country;
    private String temperature;
    private String latitude;
    private String longitude;
    private String pressure;
    private String windSpeed;
    private String humidity;
    private String exceptionMessage;

    public WeatherDataDto() {
    }

    public WeatherDataDto(String serviceName, String exceptionMessage) {
        this.serviceName = serviceName;
        this.exceptionMessage = exceptionMessage;
    }
}
