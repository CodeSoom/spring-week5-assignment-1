package com.codesoom.assignment.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * 직접 생성한 Exception 객체들의 공통 부모입니다.
 * 자식 Exception으로부터 넘겨받은 status와 message를 RuntimeException으로 전달합니다.
 */
@Getter
public class CommonException extends RuntimeException {
    private final HttpStatus status;
    private final LocalDateTime occuredTime = LocalDateTime.now();

    /**
     * 논리적인 예외를 던져야 할 때 CommonException을 상속받아 예외를 던집니다.
     * @param message 반환할 예외 응답 메시지
     * @param status 반환할 예외 응답 코드
     */
    public CommonException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }
}
