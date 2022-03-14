package com.codesoom.assignment.errors;

public class UserEmailAlreadyExistedException extends RuntimeException{
    public UserEmailAlreadyExistedException(String email) {
        super("User email is already existed: " + email);
    }
}
