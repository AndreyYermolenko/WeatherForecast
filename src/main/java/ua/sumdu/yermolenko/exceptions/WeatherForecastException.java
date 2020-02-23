package ua.sumdu.yermolenko.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Class WeatherForecastException is exception that signals a service error.
 *
 * @author Andrey
 * Created on 22.02.2020
 */
public class WeatherForecastException extends RuntimeException {
    private HttpStatus statusCode;

    /**
     * Constructor WeatherForecastException creates a new WeatherForecastException instance.
     */
    public WeatherForecastException() {
        super();
    }

    /**
     * Constructor WeatherForecastException creates a new WeatherForecastException instance.
     *
     * @param message of type String
     * @param statusCode of type HttpStatus
     */
    public WeatherForecastException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * Constructor WeatherForecastException creates a new WeatherForecastException instance.
     *
     * @param message of type String
     */
    public WeatherForecastException(String message) {
        super(message);
    }

    /**
     * Constructor WeatherForecastException creates a new WeatherForecastException instance.
     *
     * @param message of type String
     * @param cause of type Throwable
     */
    public WeatherForecastException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor WeatherForecastException creates a new WeatherForecastException instance.
     *
     * @param cause of type Throwable
     */
    public WeatherForecastException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor WeatherForecastException creates a new WeatherForecastException instance.
     *
     * @param message of type String
     * @param cause of type Throwable
     * @param enableSuppression of type boolean
     * @param writableStackTrace of type boolean
     */
    protected WeatherForecastException(String message, Throwable cause,
                                       boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Method getStatusCode returns the statusCode of this WeatherForecastException object.
     *
     *
     *
     * @return the statusCode (type HttpStatus) of this WeatherForecastException object.
     */
    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
