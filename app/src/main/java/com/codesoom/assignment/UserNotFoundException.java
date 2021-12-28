package com.codesoom.assignment;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super(String.format("[%d]에 대한 회원을 찾을 수 없습니다.", id));
    }
}
