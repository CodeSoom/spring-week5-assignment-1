package com.codesoom.assignment;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }

    public InvalidEmailException() {
        this("이메일 형식이 아닙니다.");
    }

}
