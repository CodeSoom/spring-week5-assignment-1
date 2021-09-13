package com.codesoom.assignment.exceptions;

public class AccountUpdateFailedException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "공백 데이터는 업데이트 할 수 없습니다.";

    public AccountUpdateFailedException() {
        super(DEFAULT_MESSAGE);
    }
}
