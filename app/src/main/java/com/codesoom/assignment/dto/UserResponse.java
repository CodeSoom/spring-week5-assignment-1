package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {
    private final Long id;

    private final String name;

    private final String email;

    private final String password;

    @Builder
    public UserResponse(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
