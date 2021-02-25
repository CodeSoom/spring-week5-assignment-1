package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductBadRequestException;
import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 상품과 사용자의 예외 상황을 처리한다.
 */
@ControllerAdvice
public class ErrorAdvice {
    /** 상품을 찾을 수 없다는 메세지를 리턴한다. */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorResponse handleProductNotFoundException(ProductNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    /** 사용자를 찾을 수 없다는 메세지를 리턴한다 */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    /** 상품 생성 요청 정보가 잘못 되었다는 메세지를 리턴한다. */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleProductBadRequestException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return new ErrorResponse(message);
    }

    /** 상품 생성 수행 정보가 잘못 되었다는 메세지를 리턴한다. */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductBadRequestException.class)
    public ErrorResponse handleProductBadRequestException(ProductBadRequestException e) {
        return new ErrorResponse(e.getMessage());
    }
}
