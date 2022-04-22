package com.codesoom.assignment.application;

import org.springframework.http.HttpStatus;

/** 회원을 찾지 못하면 던집니다. */
public class UserNotFoundException extends RuntimeException {

    private final String code;

    public UserNotFoundException(String code, Long id) {
        super(String.format("%s에 해당하는 회원 정보를 찾지 못했습니다.", id));
        this.code = code;
    }

    public UserNotFoundException(Long id) {
        this(String.valueOf(HttpStatus.NOT_FOUND), id);
    }

    public String getCode() {
        return code;
    }

}