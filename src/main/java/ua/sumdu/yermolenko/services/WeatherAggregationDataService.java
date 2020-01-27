package ua.sumdu.yermolenko.services;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class WeatherAggregationDataService {
    public String WeatherAggregationData(@NonNull ArrayList<String> list) {
        StringBuilder resultJson = new StringBuilder();
        resultJson.append("{");
        resultJson.append(list.get(0), 1, list.get(0).length() - 1);
        for (int i = 1; i < list.size(); i++) {
            resultJson.append(",");
            resultJson.append(list.get(i), 1, list.get(i).length() - 1);
        }
        resultJson.append("}");
        return resultJson.toString();
    }
}
