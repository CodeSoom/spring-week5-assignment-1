package com.codesoom.assignment.controllers;

import javax.servlet.http.HttpServletRequest;

import com.codesoom.assignment.NotFoundException;
import com.codesoom.assignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 컨트롤러에서 발생하는 에러를 처리한다.
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    /**
     * 던져진 "id에대한 값을 찾을수 없는 예외"를 받아 에러에 대한 응답을 리턴한다.
     *
     * @return 예외에 대한 내용이 담긴 응답
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFound(
        final HttpServletRequest request,
        final NotFoundException exception
    ) {
        return ErrorResponse.builder()
            .url(request.getRequestURI())
            .method(request.getMethod())
            .error(exception.getMessage()).build();
    }

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
        return ErrorResponse.builder()
            .url(request.getRequestURI())
            .method(request.getMethod())
            .error(
                exception.getBindingResult()
                    .getAllErrors()
                    .get(0)
                    .getDefaultMessage()
            ).build();
    }
}
