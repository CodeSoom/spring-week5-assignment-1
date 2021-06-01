package com.codesoom.assignment.dto;

import lombok.Getter;

/**
 * 응답을 위한 에러 메시지.
 */
@Getter
public class ErrorResponse {
    private String message;

    /**
     * ErrorResponse 생성자.
     *
     * @param message 에러 메시지.
     */
    public ErrorResponse(String message) {
        this.message = message;
    }
}
