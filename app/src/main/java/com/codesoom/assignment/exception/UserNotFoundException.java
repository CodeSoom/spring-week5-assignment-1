package com.codesoom.assignment.exception;

public class UserNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "id가 [%d]인 유저를 찾을 수 없습니다.";

    public UserNotFoundException(Long id) {
        super(String.format(DEFAULT_MESSAGE, id));
    }
}
