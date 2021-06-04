package com.codesoom.assignment.dto;

/**
 * HTTP 응답할 에러 내용을 저장하는 객체
 */
public class ErrorResponse {
    private String message;

    /**
     * 에러 내용을 저장하는 객체를 생성한다.
     *
     * @param message 에러 내용
     */
    public ErrorResponse(String message) {
        this.message = message;
    }

    /**
     * 저장된 에러 내용을 반환한다.
     *
     * @return 저장된 에러 내용
     */
    public String getMessage() {
        return message;
    }
}
