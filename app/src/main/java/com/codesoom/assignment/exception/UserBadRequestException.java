package com.codesoom.assignment.exception;

public class UserBadRequestException extends RuntimeException {
    public UserBadRequestException(String value, String expectedValue) {
        super(String.format("User bad request: %s 는 %s 이어야 한다", value, expectedValue));
    }

    public UserBadRequestException(String value){
        super(String.format("%s 값은 필수입니다", value));
    }
}
