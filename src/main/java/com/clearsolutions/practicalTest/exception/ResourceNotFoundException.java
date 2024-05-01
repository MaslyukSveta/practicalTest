package com.clearsolutions.practicalTest.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested resource is not found.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)  // Marks the response status as 404 Not Found when this exception is thrown.
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     * @param message the detail message.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message and cause.
     * @param message the detail message.
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new ResourceNotFoundException with the specified cause.
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method), may be null.
     */
    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
