package com.codesoom.assignment.exception;

/**
 * 회원을 찾을 수 없을 때 던집니다.
 */
public final class UserNotFoundException extends IllegalStateException {

    private static final String MESSAGE = "User Not Found";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
