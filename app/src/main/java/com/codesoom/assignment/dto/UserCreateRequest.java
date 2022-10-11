package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Getter;

/**
 * 회원 생성 요청.
 */
@Getter
public class UserCreateRequest {

    @Mapping("name")
    private String name;

    @Mapping("email")
    private String email;

    @Mapping("password")
    private String password;

    @Builder
    public UserCreateRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
