package com.codesoom.assignment.user.adapter.in.web.dto.request;

import com.codesoom.assignment.user.application.in.command.UserUpdateRequest;
import com.codesoom.assignment.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto implements UserUpdateRequest {
    private String name;
    private String email;
    private String password;

    @Builder
    public UserUpdateRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
