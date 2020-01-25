package ua.sumdu.yermolenko.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherStackData {
    private Request request;
    private Location location;
    private Current current;

    @Data
    public class Request {
        private String type;
        private String query;
        private String language;
        private String unit;
    }

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
}
