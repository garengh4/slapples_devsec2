package com.slapples.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO: unused class?
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message, Throwable t){ super(message, t); }
    public BadRequestException(String message) {
        super(message);
    }

}
