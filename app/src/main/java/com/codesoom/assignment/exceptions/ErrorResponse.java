package com.codesoom.assignment.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * 에러의 응답 body를 공통 포맷에 맞게 변경하여 리턴합니다.
 */
@Getter
@Builder
@Slf4j
public class ErrorResponse {
    private final LocalDateTime occuredTime;
    private final String message;

    public static <T extends Exception> ErrorResponse from(final T exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .occuredTime(LocalDateTime.now())
                .build();
    }
}
