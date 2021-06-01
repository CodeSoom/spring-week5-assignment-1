package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 400 Bad Request 상태와 관련된 예외를 처리한다.
 */
@RestControllerAdvice
public class BadRequestControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleInvalidProductData(MethodArgumentNotValidException ex) {
        return new ErrorResponse(ex.getBindingResult()
                                   .getFieldError()
                                   .getDefaultMessage());
    }
}
