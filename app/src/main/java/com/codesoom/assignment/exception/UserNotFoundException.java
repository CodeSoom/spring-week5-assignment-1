package com.codesoom.assignment.exception;

// TODO: NotFoundException 추상황
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found: " + id);
    }
}
