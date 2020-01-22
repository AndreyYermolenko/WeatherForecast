package ua.sumdu.yermolenko.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.sumdu.yermolenko.controllers.interfaces.MainController;
import ua.sumdu.yermolenko.services.interfaces.WeatherStack;

@RestController
public class MainControllerImpl implements MainController {
    @Autowired
    private WeatherStack weatherStack;

    @RequestMapping(path = "/weatherStack", method = RequestMethod.GET, params = "city")
    public String WeatherStackService(String city) {
        return weatherStack.currentWeather(city);
    }
}

//@RequestParam(name = "city", defaultValue = "no") String city