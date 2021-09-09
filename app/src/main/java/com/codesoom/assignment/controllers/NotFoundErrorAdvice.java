package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.UserNotFoundException;
import com.codesoom.assignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * HTTP 요청이 404에러로 실패하는 경우를 처리합니다
 */
@ControllerAdvice
public class NotFoundErrorAdvice {
    /**
     * 존재하지 않는 상품에 대한 에러응답을 리턴합니다
     * @return 에러 응답
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorResponse handleProductTaskNotFound() {
        return new ErrorResponse("Product not found");
    }

    /**
     * 존재하지 않는 사용자에 대한 에러응답을 리턴합니다
     * @param e 사용자 찾을 수 없음 예외
     * @return 에러 응답
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFound(UserNotFoundException e) {
        return new ErrorResponse("User " + e.getId() + " not found");
    }
}
