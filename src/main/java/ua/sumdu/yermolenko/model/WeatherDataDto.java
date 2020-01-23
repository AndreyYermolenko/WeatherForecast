package ua.sumdu.yermolenko.model;

import lombok.Data;

@Data
public class WeatherDataDto {
    private String name;
    private String country;
    private String temperature;

    @Override
    public String toString() {
        return "{" +
                "\"name\": " + "\"" + name + "\"," +
                "\"country\":" + "\"" + country + "\"," +
                "\"temperature\":" + "\"" + temperature + "\"" +
                '}';
    }
}
