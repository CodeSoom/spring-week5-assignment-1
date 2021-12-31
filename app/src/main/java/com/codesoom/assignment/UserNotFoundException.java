package com.codesoom.assignment;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super(String.format("[%d]에 대한 회원을 찾을 수 없습니다. " +
                            "회원의 아이디를 확인하여 요청해주세요.", id));
    }
}
