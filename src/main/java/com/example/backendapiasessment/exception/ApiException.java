package com.example.backendapiasessment.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
public class ApiException extends RuntimeException {
    private HttpStatus error;
    public ApiException(HttpStatus error, String message) {
        this(error, message, null);
    }

    public ApiException(HttpStatus error, String message, Throwable t) {
        super(message, t);
        this.error = error;
    }
}
