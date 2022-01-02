package com.codesoom.assignment.exception;

public class UserEmailDuplicationException extends RuntimeException{

    public UserEmailDuplicationException(String email) {
        super("User email is already existed: " + email);
    }
}
