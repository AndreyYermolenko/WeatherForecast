package ua.sumdu.yermolenko.constants;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Class ServiceConstants contains constants for the application.
 *
 * @author AndreyYermolenko
 * Created on 07.02.2020
 */
public class ServiceConstants {
    /** Field DARKSKY_SERVICENAME is the name of the DarkSky service.*/
    public static final String DARKSKY_SERVICENAME;
    /** Field TIMEOUT_EXCEPTION_MESSAGE is timeout exception message. */
    public static final String TIMEOUT_EXCEPTION_MESSAGE = "Server response timed out.";
    /** Field OPENWEATHERMAP_SERVICENAME is the name of the OpenWeatherMap service. */
    public static final String OPENWEATHERMAP_SERVICENAME;
    /** Field WEATHERBIT_SERVICENAME is the name of the WeatherBit service. */
    public static final String WEATHERBIT_SERVICENAME;
    /** Field WEATHERSTACK_SERVICENAME is the name of the WeatherStack service. */
    public static final String WEATHERSTACK_SERVICENAME;
    /** Field API_TIMEOUT is the API timeout value in seconds. */
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
