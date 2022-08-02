package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.UserData;

public class TestUserDataFactory {

    static public UserData createValidUserData() {
        return validUserDataBuilder().build();
    }

    static public UserData createValidUserData(Long id) {
        return validUserDataBuilder().id(id)
                .build();
    }

    static private UserData.UserDataBuilder validUserDataBuilder() {
        return UserData.builder()
                .name("name")
                .email("email")
                .password("password");
    }
}
