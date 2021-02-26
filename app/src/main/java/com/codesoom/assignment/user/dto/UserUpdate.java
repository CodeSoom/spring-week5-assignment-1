package com.codesoom.assignment.user.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

public class UserUpdate {

    @NotBlank
    @Mapping("name")
    private String name;

    @Mapping("email")
    private String email;

    @NotBlank
    @Mapping("password")
    private String password;

    @Builder
    public UserUpdate(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
