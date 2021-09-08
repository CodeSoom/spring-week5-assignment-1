package com.codesoom.assignment.controllers;

import javax.servlet.http.HttpServletRequest;

import com.codesoom.assignment.dto.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * MethodArgumentNotValidException 예외를 처리한다.
 */
@ControllerAdvice
public class ArgumentNotValidAdvice {
    /**
     * 던져진 "잘못된 메소드 입력값 에외"를 받아 에러에 대한 응답을 리턴한다.
     *
     * @return 예외에 대한 내용이 담긴 응답
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleNotFound(
        final HttpServletRequest request,
        final MethodArgumentNotValidException exception
    ) {
        return new ErrorResponse(
            request.getRequestURI(),
            request.getMethod(),
            "잘못된 입력값입니다.",
            exception.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage()
        );
    }

}
