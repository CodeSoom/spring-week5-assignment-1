package com.codesoom.assignment.common.exceptions;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException(String message) {
        super(message);
    }

    public DuplicateUserException() {
        this("중복된 회원 입니다.");
    }

}
