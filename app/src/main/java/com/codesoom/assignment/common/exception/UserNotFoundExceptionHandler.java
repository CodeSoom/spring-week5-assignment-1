package com.codesoom.assignment.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserNotFoundExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductNotFoundExceptionHandler.class);

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void UserNotFoundException(Exception e) {
        LOGGER.error("error log = {}", e.toString());
    }
}
