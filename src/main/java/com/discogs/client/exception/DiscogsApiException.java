package com.discogs.client.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when there is an error with the Discogs API.
 */
@Getter
public class DiscogsApiException extends RuntimeException {

    private final HttpStatus httpStatus;

    /**
     * Constructs a new DiscogsApiException with the specified detail message and HTTP status.
     *
     * @param message the detail message
     * @param httpStatus the HTTP status
     */
    public DiscogsApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    /**
     * Constructs a new DiscogsApiException with the specified detail message, HTTP status, and cause.
     *
     * @param message the detail message
     * @param httpStatus the HTTP status
     * @param cause the cause
     */
    public DiscogsApiException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }
}