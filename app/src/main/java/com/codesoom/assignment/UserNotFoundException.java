package com.codesoom.assignment;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Product not found: " + id);
    }
}
