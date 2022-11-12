package com.codesoom.assignment.support;

import com.codesoom.assignment.user.adapter.in.request.UserCreateRequestDto;
import com.codesoom.assignment.user.domain.User;

public enum UserFixture {
    USER_1("기범", "dev.gibeom@gmail.com", "비밀번호486"),
    USER_2("Alex", "dev.gibeom@gmail.com", "password486"),
    ;

    private String name;
    private String email;
    private String password;

    UserFixture(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User 회원_엔티티_생성() {
        return 회원_엔티티_생성(null);
    }

    public User 회원_엔티티_생성(Long id) {
        return User.builder()
                .id(id)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .build();
    }

    public UserCreateRequestDto 생성_요청_데이터_생성() {
        return UserCreateRequestDto.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .build();
    }


    public String NAME() {
        return name;
    }

    public String EMAIL() {
        return email;
    }

    public String PASSWORD() {
        return password;
    }
}
