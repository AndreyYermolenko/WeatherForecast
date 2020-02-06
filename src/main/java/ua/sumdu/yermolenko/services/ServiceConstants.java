package ua.sumdu.yermolenko.services;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ServiceConstants {
    public static final String DARKSKY_SERVICENAME;
    public static final String TIMEOUT_EXCEPTION_MESSAGE = "Server response timed out.";
    public static final String OPENWEATHERMAP_SERVICENAME;
    public static final String WEATHERBIT_SERVICENAME;
    public static final String WEATHERSTACK_SERVICENAME;
    public static final int API_TIMEOUT;

    static {
        File file = new File("src" + File.separator
                + "main" + File.separator
                + "resources" + File.separator
                + "application.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DARKSKY_SERVICENAME = properties.getProperty("darksky.servicename");
        OPENWEATHERMAP_SERVICENAME = properties.getProperty("openweathermap.servicename");
        WEATHERBIT_SERVICENAME = properties.getProperty("weatherbit.servicename");
        WEATHERSTACK_SERVICENAME = properties.getProperty("weatherstack.servicename");
        API_TIMEOUT = Integer.parseInt(properties.getProperty("api.timeout"));
    }
}
