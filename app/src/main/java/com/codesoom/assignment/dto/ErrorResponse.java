package com.codesoom.assignment.dto;

/**
 * 에러 응답 정보.
 */
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
