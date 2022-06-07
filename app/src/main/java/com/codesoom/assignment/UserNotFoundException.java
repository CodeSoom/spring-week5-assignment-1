package com.codesoom.assignment;

/**
 * User 탐색 실패 예외 처리
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found: " + id);
    }
}
