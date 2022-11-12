package com.codesoom.assignment.user.adapter.in.web.dto.request;

import com.codesoom.assignment.user.application.in.command.UserCreateRequest;
import com.codesoom.assignment.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreateRequestDto implements UserCreateRequest {
    private String name;
    private String email;
    private String password;

    @Builder
    public UserCreateRequestDto(String name, String email, String password) {
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
