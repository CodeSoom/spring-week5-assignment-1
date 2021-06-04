package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * API 요청 처리 중 유효성 검증에 실패할 경우 발생한 예외를 담당합니다.
 */
@RestControllerAdvice
public class NotValidControllerAdvice {
    /**
     * 유효하지 않은 데이터가 전달될 경우 에러를 응답합니다.
     *
     * @param exception 발생한 예외
     * @return 에러 응답
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleInvalidData(MethodArgumentNotValidException exception) {
        return new ErrorResponse(exception.getBindingResult()
                                          .getFieldError()
                                          .getDefaultMessage());
    }
}
