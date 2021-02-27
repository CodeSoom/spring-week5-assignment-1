package com.codesoom.assignment;

public class UserNotfoundException extends RuntimeException{
    public UserNotfoundException(Long id) {
        super("User not found: " + id);
    }
}
