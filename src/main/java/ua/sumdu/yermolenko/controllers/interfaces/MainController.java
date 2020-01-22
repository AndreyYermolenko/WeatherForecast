package ua.sumdu.yermolenko.controllers.interfaces;

import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface MainController {

    @RequestMapping(path = "/weatherStack", method = RequestMethod.GET, params = "city")
    String WeatherStackService(@NonNull String city);
}
