package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.UserData;

public interface UserConstants {
    final Long ID = 1L;
    final String NAME = "테스트 유저";
    final String PASSWORD = "password";
    final String EMAIL = "test@test.com";
    final User USER = User.builder()
        .email(EMAIL)
        .name(NAME)
        .password(PASSWORD)
        .build();
    final UserData USER_DATA = UserData.builder()
        .email(EMAIL)
        .name(NAME)
        .password(PASSWORD)
        .build();
}
