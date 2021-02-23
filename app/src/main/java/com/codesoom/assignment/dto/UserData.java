package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@NoArgsConstructor
public class UserData {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @Builder
    public UserData(@NotBlank String name,
                    @NotBlank @Email String email,
                    @NotBlank String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
