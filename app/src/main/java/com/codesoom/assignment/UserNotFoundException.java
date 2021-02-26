package com.codesoom.assignment;

/**
 * 해당하는 유저가 없을 때 호출되는 예외입니다.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Product not found: " + id);
    }
}
