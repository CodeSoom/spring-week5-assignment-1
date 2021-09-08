package com.codesoom.assignment;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super(String.format("id %s에 대한 유저를 찾을 수 없습니다.", id));
    }
}
