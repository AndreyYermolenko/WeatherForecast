package ua.sumdu.yermolenko.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ua.sumdu.yermolenko.services.ServiceName;

/**
 * Class WeatherDataDto stores weather data.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private String sunrise;
    private String temperatureFeelsLike;
    private String directionWind;
    private String weatherDescription;
    private String exceptionMessage;

    public WeatherDataDto() {
    }

    public WeatherDataDto(ServiceName serviceName, String exceptionMessage) {
        this.serviceName = serviceName.toString();
        this.exceptionMessage = exceptionMessage;
    }

    public void setServiceName(ServiceName serviceName) {
        this.serviceName = serviceName.toString();
    }
}
