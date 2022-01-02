package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UserRegistrationData {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 4, max = 1024)
    private String password;

    @Email
    private String email;

    @Builder
    public UserRegistrationData(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
