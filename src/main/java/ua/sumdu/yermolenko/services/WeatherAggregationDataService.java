package ua.sumdu.yermolenko.services;

import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class WeatherAggregationDataService {
    public String WeatherAggregationData(@NonNull String...arg) {
        StringBuilder resultJson = new StringBuilder();
        resultJson.append("{");
        resultJson.append(arg[0], 1, arg[0].length() - 1);
        for (int i = 1; i < arg.length; i++) {
            resultJson.append(",");
            resultJson.append(arg[i], 1, arg[i].length() - 1);
        }
        resultJson.append("}");
        return resultJson.toString();
    }
}
