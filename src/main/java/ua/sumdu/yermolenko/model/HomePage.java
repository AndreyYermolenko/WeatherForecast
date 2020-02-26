package ua.sumdu.yermolenko.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class HomePage {
    private String title = "start page";
    private ArrayList<ApiInfo> listRequests = new ArrayList<>();
    {
        listRequests.add(new ApiInfo("Request for temperature", "http://localhost:8081/temperature/ua/Sumy"));
        listRequests.add(new ApiInfo("Request for city coordinates", "http://localhost:8081/cityCoordinates/ua/Sumy"));
        listRequests.add(new ApiInfo("Request for pressure", "http://localhost:8081/pressure/ua/Sumy"));
        listRequests.add(new ApiInfo("Request for wind speed", "http://localhost:8081/windSpeed/ua/Sumy"));
        listRequests.add(new ApiInfo("Request for humidity", "http://localhost:8081/humidity/ua/Sumy"));
        listRequests.add(new ApiInfo("Request for full weather", "http://localhost:8081/fullWeather/ua/Sumy"));
        listRequests.add(new ApiInfo("Request for sunrise time", "http://localhost:8081/sunriseTime/ua/Sumy"));
        listRequests.add(new ApiInfo("Request for temperature feels like", "http://localhost:8081/feelsLikeTemperature/ua/Sumy"));
        listRequests.add(new ApiInfo("Request for direction wind", "http://localhost:8081/directionWind/ua/Sumy"));
        listRequests.add(new ApiInfo("Request for weather description", "http://localhost:8081/weatherDescription/ua/Sumy"));
    }
    
    @Data
    class ApiInfo {
        private String description;
        private String example_uri;

        public ApiInfo(String description, String uri) {
            this.description = description;
            this.example_uri = uri;
        }
    }
}
