package com.codesoom.assignment;

/**
 * 유저를 찾을 수 없는 경우에 던집니다.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found: " + id);
    }
}
