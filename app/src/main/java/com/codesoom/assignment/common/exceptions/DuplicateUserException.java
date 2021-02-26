package com.codesoom.assignment.common.exceptions;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException() {
        super("중복된 회원 입니다.");
    }

    public DuplicateUserException(String message) {
        super(message);
    }

}
