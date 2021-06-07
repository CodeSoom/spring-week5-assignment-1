package com.codesoom.assignment.dto;

import lombok.Builder;

/**
 * 에러 메세지 응답객체 입니다.
 */
@Builder
public class ErrorResponse<T> {
    private T message;

    public ErrorResponse(T message) {
        this.message = (T) message;
    }

    public T getMessage() {
        return message;
    }
}
