package ua.sumdu.yermolenko.services;

import org.springframework.http.HttpStatus;

public class WeatherForecastException extends RuntimeException {
    private HttpStatus statusCode;

    public WeatherForecastException() {
        super();
    }

    public WeatherForecastException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public WeatherForecastException(String message) {
        super(message);
    }

    public WeatherForecastException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeatherForecastException(Throwable cause) {
        super(cause);
    }

    protected WeatherForecastException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
