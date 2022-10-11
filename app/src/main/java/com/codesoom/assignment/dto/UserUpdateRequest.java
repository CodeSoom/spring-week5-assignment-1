package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 회원 수정 요청.
 */
@Getter
public class UserUpdateRequest {

    private String name;
    private String email;
    private String password;

    @Builder
    public UserUpdateRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
