package com.vinay.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class APIException extends RuntimeException {
    private final String message;

    public APIException(String message) {
        super(message);
        this.message = message;
    }
}
