package com.codesoom.assignment;

/**
 * 사용자를 찾을 수 없는 경우에 던집니다.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found: " + id);
    }
}
