package com.codesoom.assignment.user.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

public class UserUpdate {

    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("password")
    private String password;

    @Builder
    public UserUpdate(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
