package com.codesoom.assignment.exception;

public class UserBadRequestException extends RuntimeException {
    public UserBadRequestException(String variable, String status) {
        super(String.format("User bad request: %s 는 %s 이어야 한다", variable, status));
    }
    public UserBadRequestException(String variable){
        super(String.format("%s 값은 필수입니다", variable));
    }
}
