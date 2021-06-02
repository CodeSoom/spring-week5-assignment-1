package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

/**
 * 사용자 API 요청 처리 중에 발생한 예외 처리를 담당합니다.
 */
@RestControllerAdvice
public class UserControllerAdvice {
    /**
     * 사용자를 찾을 수 없는 경우
     * 404 Not Found 상태와 사용자를 찾을 수 없다는 메시지를 응답합니다.
     *
     * @param exception 발생한 예외
     * @return 에러 응답
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFound(HttpClientErrorException exception) {
        return new ErrorResponse(exception.getMessage());
    }
}
