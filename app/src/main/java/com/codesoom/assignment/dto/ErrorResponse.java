package com.codesoom.assignment.dto;

/**
 * HTTP 응답할 에러 내용을 저장합니다.
 */
public class ErrorResponse {
    private String message;

    /**
     * 생성자.
     *
     * @param message 에러 메시지
     */
    public ErrorResponse(String message) {
        this.message = message;
    }

    /**
     * 저장된 에러 메시지를 반환한다.
     *
     * @return 저장된 에러 메시지
     */
    public String getMessage() {
        return message;
    }
}
