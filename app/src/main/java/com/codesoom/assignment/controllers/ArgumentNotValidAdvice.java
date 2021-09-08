package com.codesoom.assignment.controllers;

import javax.servlet.http.HttpServletRequest;

import com.codesoom.assignment.dto.ErrorResponse;
import com.codesoom.assignment.dto.UserData;

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
     * MethodArgumentNotValidException을 처리한다.
     *
     * @return 어디서(url경로, Http 메서드) 어떤 에러가 발생하였으며, 어떻게 해결할 수 있을지를 리턴한다.
     *
     * @see UserController#create(UserData)
     *
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
