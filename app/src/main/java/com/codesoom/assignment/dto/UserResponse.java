package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.User;
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

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
