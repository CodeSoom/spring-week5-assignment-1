package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.beans.ConstructorProperties;

/**
 * 회원 수정 요청.
 */
@Getter
public class UserUpdateRequest {

    @NotBlank(message = "{user.name.not.blank}")
    private String name;

    private String email;

    @NotBlank(message = "{user.password.not.blank}")
    private String password;

    @Builder
    @ConstructorProperties({"name", "email", "password"})
    public UserUpdateRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
