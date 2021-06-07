package com.codesoom.assignment.dto;

import lombok.Getter;

/**
 * 에러가 발생한 경우의 응답
 */
@Getter
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
