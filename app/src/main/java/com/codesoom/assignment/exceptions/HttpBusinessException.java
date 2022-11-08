package com.codesoom.assignment.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * 모든 논리적 에러의 공통 부모가 되는 객체입니다.
 * 논리적 에러로 인한 예외 케이스를 만들 때, 해당 객체를 상속받아 예외를 생성합니다.
 * 기본적으로 RuntimeException을 상속받기에, 예외 발생 시 roll-back을 수행합니다.
 */
@Getter
public class HttpBusinessException extends RuntimeException {
    private final HttpStatus status;
    private final LocalDateTime occuredTime = LocalDateTime.now();

    /**
     * 자식 Exception으로부터 넘겨받은 status와 message를 RuntimeException으로 전달합니다.
     *
     * @param message 반환할 예외 응답 메시지
     * @param status  반환할 예외 응답 코드
     */
    public HttpBusinessException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }
}
