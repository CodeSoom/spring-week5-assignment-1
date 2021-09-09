package com.codesoom.assignment.dto;

/**
 * 에러 상황의 정보를 표현합니다
 */
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    /**
     * 에러 메세지를 반환합니다
     * @return 에러메세지
     */
    public String getMessage() {
        return message;
    }
}
