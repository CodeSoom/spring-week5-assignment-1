package com.codesoom.assignment;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User Not Found : " + id);
    }
}
