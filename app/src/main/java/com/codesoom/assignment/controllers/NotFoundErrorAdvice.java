package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.dto.ErrorResponse;
import com.codesoom.assignment.exception.NotFoundUserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 무언가를 찾지 못했을 경우 던져진 예외를 처리합니다.
 */
@ControllerAdvice
public class NotFoundErrorAdvice {

    /**
     * 상품을 찾지 못했다는 예외가 던져지면, 404 상태코드와 예외 메시지를 응답합니다.
     *
     * @return 상품을 찾지 못했다는 예외 메시지
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorResponse handleProductTaskNotFound() {
        return new ErrorResponse("Product not found");
    }

    /**
     * 유저를 찾지 못했다는 예외가 던져지면, 404 상태코드와 예외 메시지를 응답합니다.
     *
     * @param e 던져진 예외 객체
     * @return 유저를 찾지 못했다는 예외 메시지
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundUserException.class)
    public ErrorResponse handleUserNotFound(Exception e) {
        return new ErrorResponse(e.getMessage());
    }
}
