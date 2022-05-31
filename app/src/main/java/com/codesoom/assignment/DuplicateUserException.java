package com.codesoom.assignment;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException(String message) {
        super(message);
    }

    public DuplicateUserException() {
        this("이미 저장된 사용자입니다.");
    }
}
