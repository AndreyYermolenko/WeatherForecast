package ua.sumdu.yermolenko.model.weatherstack;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Class Location is used for parsing WeatherStack API data using Jackson.
 *
 * @author AndreyYermolenko
 * Created on 07.02.2020
 */
@Data
public class Location {
    private String name;
    private String country;
    private String region;
    private String lat;
    private String lon;
    @JsonProperty("timezone_id")
    private String timezoneId;
    private String localtime;
    @JsonProperty("localtime_epoch")
    private String localtimeEpoch;
    @JsonProperty("utc_offset")
    private String utcOffset;
}