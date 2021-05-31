package com.codesoom.assignment;

/**
 * 사용자를 찾을 수 없다는 예외.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found: " + id);
    }
}
