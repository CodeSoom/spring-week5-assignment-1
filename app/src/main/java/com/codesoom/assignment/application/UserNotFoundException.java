package com.codesoom.assignment.application;

import lombok.Getter;

import java.util.NoSuchElementException;

/**
 * 사용자를 찾을 수 없는 경우 던집니다.
 */
@Getter
public class UserNotFoundException extends NoSuchElementException {
    private final Long id;
    private final String message;

    public UserNotFoundException(Long id, String message) {
        this.id = id;
        this.message = message;
    }
}
