package com.codesoom.assignment.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "해당 식별자[%d]의 회원을 찾을 수 없었습니다.";

    public AccountNotFoundException(Long id) {
        super(String.format(DEFAULT_MESSAGE, id));
    }
}
