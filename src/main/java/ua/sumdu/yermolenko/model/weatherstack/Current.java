package ua.sumdu.yermolenko.model.weatherstack;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Class Current is used for parsing WeatherStack API data using Jackson.
 *
 * @author AndreyYermolenko
 * Created on 07.02.2020
 */
@Data
public class Current {
    @JsonProperty("observation_time")
    private String observationTime;
    private String temperature;
    @JsonProperty("weather_code")
    private String weatherCode;
    @JsonProperty("weather_icons")
    private String [] weatherIcons;
    @JsonProperty("weather_descriptions")
    private String [] weatherDescriptions ;
    @JsonProperty("wind_speed")
    private String windSpeed;
    @JsonProperty("wind_degree")
    private String windDegree;
    @JsonProperty("wind_dir")
    private String windDir;
    private String pressure;
    private String precip;
    private String humidity;
    private String cloudcover;
    private String feelslike;
    @JsonProperty("uv_index")
    private String uvIndex;
    private String visibility;
    @JsonProperty("is_day")
    private String isDay;
}
