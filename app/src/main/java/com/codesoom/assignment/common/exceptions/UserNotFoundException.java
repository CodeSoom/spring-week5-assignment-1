package com.codesoom.assignment.common.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        this("회원을 찾을 수 없습니다.");
    }

}
