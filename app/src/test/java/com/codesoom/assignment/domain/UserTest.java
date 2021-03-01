package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    Long userId = 1L;
    String userName = "양승인";
    String email = "rhfpdk92@naver.com";
    String password = "1234";
    User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .id(userId)
                .name(userName)
                .email(email)
                .password(password)
                .build();
    }

    @Test
    @DisplayName("getId() 테스트")
    void getId() {
        assertThat(user.getId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("getName() 테스트")
    void getName() {
        assertThat(user.getName()).isEqualTo(userName);
    }

    @Test
    @DisplayName("getEmail() 테스트")
    void getEmail() {
        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("getPassword() 테스트")
    void getPassword() {
        assertThat(user.getPassword()).isEqualTo(password);
    }

    @Test
    @DisplayName("builder 테스트")
    void builder() {
        assertThat(user).isInstanceOf(User.class);
    }
}
