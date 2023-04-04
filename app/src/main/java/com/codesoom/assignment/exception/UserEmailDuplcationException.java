package com.codesoom.assignment.exception;

public class UserEmailDuplcationException extends RuntimeException{
    public UserEmailDuplcationException(String email) {
        super("User email is already existed" + email);
    }
}
