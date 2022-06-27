package com.codesoom.assignment.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(id + " 인 유저는 존재하지 않습니다.");
    }
}
