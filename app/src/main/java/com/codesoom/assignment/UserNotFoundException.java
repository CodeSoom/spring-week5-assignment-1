package com.codesoom.assignment;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

/**
 * 사용자를 찾을 수 없는 경우에 던집니다.
 */
public class UserNotFoundException extends HttpClientErrorException {
    public UserNotFoundException(Long id) {
        this("User not found: " + id);
    }

    public UserNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
