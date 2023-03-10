package com.codesoom.assignment.fixture;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;

public enum UserFixture {

    UPDATE_USER(1L, "앙김홍집","hongzip@naver.com","123123");

    private final Long id;
    private final String name;
    private final String email;
    private final String password;

    private UserFixture(Long id, String name, String email, String password){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserData getUserData(){
        return new UserData(id, name, email, password);
    }

}
