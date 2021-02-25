package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {
    private String name;
    private String email;

    @Builder
    public UserResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
