package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
public class UserData {
    @NotBlank(message = "{username.notBlank}")
    @NotNull(message = "{username.notNull}")
    @Id
    private String username;

    @NotBlank(message = "{password.notBlank}")
    @NotNull(message = "{password.notNull}")
    private String password;

    @NotBlank(message = "{email.notBlank}")
    @NotNull(message = "{email.notNull}")
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
