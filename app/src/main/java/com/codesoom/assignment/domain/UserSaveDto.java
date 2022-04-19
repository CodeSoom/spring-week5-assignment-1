package com.codesoom.assignment.domain;

import com.codesoom.assignment.application.UserSaveRequest;

/**
 * 사용자 등록 시 사용할 DTO 클래스 입니다.
 * 사용자 정보를 받기 위해 컨트롤러에서만 사용합니다.
 */
public class UserSaveDto implements UserSaveRequest {

    private String name;
    private String email;
    private String password;

    public UserSaveDto() {
    }

    public UserSaveDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

}
