package com.codesoom.assignment;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        this("사용자를 찾을 수 없습니다.");
    }
}
