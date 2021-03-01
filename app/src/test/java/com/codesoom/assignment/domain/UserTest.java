package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    final String NAME = "User";
    final String EMAIL = "User@gmail.com";
    final String PASSWORD = "1234";

    @DisplayName("주어진 유저 정보로 유저를 생성합니다.")
    @Test
    void testCreateUserWithBuilder() {
        User user = User.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        assertThat(user.getName()).isEqualTo(NAME);
        assertThat(user.getEmail()).isEqualTo(EMAIL);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
    }
}
