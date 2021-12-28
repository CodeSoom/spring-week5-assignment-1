package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UserData {

    @Setter
    private Long id;

    @Setter
    @NotBlank
    private String name;

    @NotNull
    private String password;

    @Email
    private String email;

    @Builder
    public UserData(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
