package com.codesoom.assignment.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(String.format("id가 %d 인 User 를 찾을 수 없습니다. 다른 id 를 입력해보세요.", id));
    }
}
