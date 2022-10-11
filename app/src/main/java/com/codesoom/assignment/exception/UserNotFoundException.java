package com.codesoom.assignment.exception;

/**
 * 회원을 찾지 못하는 예외 발생시 던집니다.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
