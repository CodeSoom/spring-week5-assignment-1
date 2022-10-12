package com.codesoom.assignment.controllers;

import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.dto.ErrorResponse;
import com.codesoom.assignment.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 조회 실패시 응답을 관리합니다.
 */
@RestControllerAdvice
public class NotFoundErrorAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ProductNotFoundException.class, UserNotFoundException.class})
    public ErrorResponse handleNotFound(Exception e) {
        return new ErrorResponse(e.getMessage());
    }
}
