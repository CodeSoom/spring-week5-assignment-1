package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.User;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateUserDto {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    protected UpdateUserDto() {
    }

    @Builder
    public UpdateUserDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
            .name(name)
            .email(email)
            .password(password)
            .build();
    }
}

