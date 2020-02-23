package ua.sumdu.yermolenko.services;

/**
 * Enum ServiceName contains service names.
 *
 * @author Andrey
 * Created on 22.02.2020
 */
public enum ServiceName {
    DARK_SKY("dark_sky"),
    OPEN_WEATHER_MAP("open_weather_map"),
    WEATHER_BIT("weather_bit"),
    WEATHER_STACK("weather_stack");

    private String name;

    /**
     * Constructor ServiceName creates a new ServiceName instance.
     *
     * @param name of type String
     */
    ServiceName(String name) {
        this.name = name;
    }

    /**
     * Method toString returns name;
     * @return String
     */
    @Override
    public String toString() {
        return name;
    }
}
