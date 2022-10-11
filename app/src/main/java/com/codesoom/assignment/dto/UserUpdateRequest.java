package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.beans.ConstructorProperties;

/**
 * 회원 수정 요청.
 */
@Getter
public class UserUpdateRequest {

    @NotEmpty
    private String name;

    private String email;

    @NotEmpty
    private String password;

    @Builder
    @ConstructorProperties({"name", "email", "password"})
    public UserUpdateRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
