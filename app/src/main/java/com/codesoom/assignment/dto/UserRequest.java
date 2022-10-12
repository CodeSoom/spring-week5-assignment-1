package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserRequest {

    @NotBlank
    @Mapping("email")
    private String email;

    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("password")
    private String password;

    @Builder
    public UserRequest(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
