package com.aviatickets.storage.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnsupportedOperationException extends RuntimeException{

    private HttpStatus status;

    public UnsupportedOperationException(String message) {
        super(message);
    }

    public UnsupportedOperationException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
