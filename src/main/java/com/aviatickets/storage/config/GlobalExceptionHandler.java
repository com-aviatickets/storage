package com.aviatickets.storage.config;

import com.aviatickets.storage.exception.UnsupportedExtensionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String DEFAULT_MESSAGE = "Uncaught exception: ";

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<?> handleException(Exception e) {
        log.error(DEFAULT_MESSAGE, e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {UnsupportedExtensionException.class})
    protected ResponseEntity<?> handleUnsupportedExtensionException(UnsupportedExtensionException e) {
        log.error(DEFAULT_MESSAGE, e);
        return buildErrorResponse(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    protected ResponseEntity<?> handleNoSuchElementException(NoSuchElementException e) {
        log.error(DEFAULT_MESSAGE, e);
        return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<?> buildErrorResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(
                new ErrorDto(message, status.getReasonPhrase(), status.value()),
                status
        );
    }

    public record ErrorDto(
            String message,
            String status,
            int code
    ) {
    }

}
