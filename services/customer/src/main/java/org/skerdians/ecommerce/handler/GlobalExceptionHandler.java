package org.skerdians.ecommerce.handler;

import org.skerdians.ecommerce.exception.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling exceptions across the whole application.
 * This class provides methods to handle specific exceptions and return appropriate HTTP responses.
 */
// Indicates that this class provides global exception handling for all controllers
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link CustomerNotFoundException} and returns a 404 Not Found response.
     *
     * @param exception the exception to handle
     * @return a {@link ResponseEntity} containing the exception message and a 404 status code
     */
    @ExceptionHandler(CustomerNotFoundException.class) // Specifies the type of exception to handle
    public ResponseEntity<String> handle(CustomerNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // Sets the HTTP status to 404 Not Found
                .body(exception.getMessage()); // Returns the exception message in the response body
    }

    /**
     * Handles {@link MethodArgumentNotValidException} and returns a 400 Bad Request response.
     * This method extracts validation errors and returns them in a structured format.
     *
     * @param exception the exception to handle
     * @return a {@link ResponseEntity} containing the validation errors and a 400 status code
     */
    // Specifies the type of exception to handle
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(
                error -> {
                    // Extracts the field name that caused the error
                    String fieldName = ((FieldError) error).getField();
                    // Extracts the default error message
                    String errorMessage = error.getDefaultMessage();
                    // Adds the field name and error message to the errors map
                    errors.put(fieldName, errorMessage);
                }
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // Sets the HTTP status to 400 Bad Request
                .body(new ErrorResponse(errors)); // Returns the errors map in the response body
    }
}

