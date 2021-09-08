package com.codesoom.assignment.controllers;

import javax.servlet.http.HttpServletRequest;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * NotFound 예외 처리를 담당한다.
 */
@ControllerAdvice
public class NotFoundErrorAdvice {
    /**
     * 던져진 "id에대한 값을 찾을수 없는 예외"를 받아 에러에 대한 응답을 리턴한다.
     *
     * @return 예외에 대한 내용이 담긴 응답
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorResponse handleNotFound(final HttpServletRequest request) {
        return new ErrorResponse(
            request.getRequestURI(),
            request.getMethod(),
            "Product를 찾을 수 없습니다.",
            "id를 확인해 주세요."
        );
    }
}
