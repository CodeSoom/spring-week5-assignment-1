package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

/**
 * 404 Not Found 상태와 관련된 예외를 처리한다.
 */
@RestControllerAdvice
public class NotFoundControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ProductNotFoundException.class,
                       UserNotFoundException.class})
    public ErrorResponse handleNotFound(HttpClientErrorException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
