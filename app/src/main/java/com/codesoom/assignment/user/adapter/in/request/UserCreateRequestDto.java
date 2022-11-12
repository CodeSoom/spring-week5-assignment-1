package com.codesoom.assignment.user.adapter.in.request;

import com.codesoom.assignment.user.application.in.command.UserCreateRequest;
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
}
