package com.codesoom.assignment;

/**
 * 회원을 찾지 못할 시 던집니다.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super(String.format("id %s에 대한 회원을 찾을 수 없습니다.", id));
    }
}
