package com.codesoom.assignment;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long id) {
        this("사용자를 찾을 수 없습니다.\nid :  " + id);
    }
}
