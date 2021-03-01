package com.codesoom.assignment.common.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id) {
        super("User not found: " + id);
    }
}
