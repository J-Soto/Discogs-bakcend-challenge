package com.discogs.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles DiscogsApiException.
     *
     * @param e the exception
     * @return the response entity with error message and HTTP status
     */
    @ExceptionHandler(DiscogsApiException.class)
    public ResponseEntity<String> handleDiscogsApiException(DiscogsApiException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
    }

    /**
     * Handles ResourceNotFoundException.
     *
     * @param e the exception
     * @return the response entity with error message and HTTP status
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Handles InvalidInputException.
     *
     * @param e the exception
     * @return the response entity with error message and HTTP status
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInputException(InvalidInputException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * Handles all other exceptions.
     *
     * @param ex the exception
     * @return the response entity with error message and HTTP status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}