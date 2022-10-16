package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.User;
import lombok.Getter;

/**
 * 회원 응답.
 */
@Getter
public class UserResponse {
    private final Long id;
    private final String name;
    private final String email;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
