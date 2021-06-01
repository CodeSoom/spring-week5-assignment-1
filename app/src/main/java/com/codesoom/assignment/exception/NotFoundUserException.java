package com.codesoom.assignment.exception;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException(Long id) {
        super("Cannot find user " + id);
    }
}
