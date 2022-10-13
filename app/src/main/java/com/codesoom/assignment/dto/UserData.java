package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
public class UserData {
    @NotBlank(message = "{User.usernameNotBlank}")
    @NotNull(message = "{User.usernameNotNull}")
    @Id
    private String username;

    @NotBlank(message = "{User.passwordNotBlank}")
    @NotNull(message = "{User.passwordNotNull}")
    private String password;

    @NotBlank(message = "{User.emailNotBlank}")
    @NotNull(message = "{User.emailNotNull}")
    @Email
    private String email;

    @Builder
    public UserData(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserData() {
    }
}
