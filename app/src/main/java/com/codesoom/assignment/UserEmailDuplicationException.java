package com.codesoom.assignment;

import com.codesoom.assignment.domain.User;

/**
 * User 탐색 실패 예외.
 */
public class UserEmailDuplicationException extends RuntimeException {
    public UserEmailDuplicationException(User user) {
        super(String.format("User email already exists - id: %d, email: %s",
                user.getId(), user.getEmail()));
    }
}
