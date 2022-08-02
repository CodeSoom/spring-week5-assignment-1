package com.codesoom.assignment.application;

import com.codesoom.assignment.dto.UserData;

public class UserService {
    public UserData createUser(UserData userData) {
        // TODO: 실제 데이터 저장 후 반환 필요
        return UserData.builder()
                .id(1L)
                .name(userData.getName())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .build();
    }
}
