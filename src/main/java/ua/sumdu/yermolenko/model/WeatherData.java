package ua.sumdu.yermolenko.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class WeatherData {
    private Map<String, String> request;
    private Map<String, String> location;
    private Current current;

    @Data
    @JsonIgnoreProperties(ignoreUnknown=true)
    class Current {
        private String observation_time;
        private String temperature;
        private String wind_speed;
        private String wind_degree;
        private String pressure;
    }
}
