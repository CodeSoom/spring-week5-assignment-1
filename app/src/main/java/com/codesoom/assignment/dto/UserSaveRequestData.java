package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.users.UserSaveRequest;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.beans.ConstructorProperties;

@Getter
public class UserSaveRequestData implements UserSaveRequest {

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    private final String name;

    @NotBlank
    private final String password;

    @Builder
    @ConstructorProperties({"email", "name", "password"})
    public UserSaveRequestData(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
