package com.codesoom.assignment.user.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

public class UserUpdate {
    private Long id;

    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("email")
    private String email;

    @NotBlank
    @Mapping("password")
    private String password;

    @Builder
    public UserUpdate(Long id, @NotBlank String name, @NotBlank String email, @NotBlank String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
