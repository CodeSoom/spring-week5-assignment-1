package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.users.UserUpdateRequest;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.beans.ConstructorProperties;

@Builder
@Getter
public class UserUpdateRequestData implements UserUpdateRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @Builder
    @ConstructorProperties({"email", "name", "password"})
    public UserUpdateRequestData(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
