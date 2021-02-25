package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Email
    @Mapping("email")
    private String email;

    @NotBlank
    @Mapping("password")
    private String password;

    @Builder
    public CreateUserRequest(@NotBlank String name,
                             @NotBlank @Email String email,
                             @NotBlank String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
