package com.codesoom.assignment.exception;

public final class UserNotFoundException extends IllegalStateException {

    private static final String MESSAGE = "User Not Found";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
