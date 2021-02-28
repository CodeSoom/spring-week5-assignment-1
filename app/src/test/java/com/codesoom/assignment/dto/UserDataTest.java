package com.codesoom.assignment.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserDataTest {
    private final String SETUP_USER_NAME = "name";
    private final String SETUP_USER_EMAIL = "email";
    private final String SETUP_USER_PASSWORD = "password";

    private UserData setUpUser;

    @BeforeEach
    void setUp() {
        setUpUser = UserData.builder()
                .name("")
                .email("")
                .password("")
                .build();
    }

    @Test
    @DisplayName("사용자의 이름이 비어있는 경우")
    void isNameWrong() {
        assertThat(setUpUser.getName()).isNotNull();
        assertThat(setUpUser.getName()).isEqualTo("");
        assertTrue(setUpUser.isNameWrong());
    }

    @Test
    @DisplayName("사용자의 이메일이 비어있는 경우")
    void isEmailWrong() {
        assertThat(setUpUser.getEmail()).isNotNull();
        assertThat(setUpUser.getEmail()).isEqualTo("");
        assertTrue(setUpUser.isEmailWrong());
    }

    @Test
    @DisplayName("사용자의 비밀번호가 비어있는 경우")
    void isPasswordWrong() {
        assertThat(setUpUser.getPassword()).isNotNull();
        assertThat(setUpUser.getPassword()).isEqualTo("");
        assertTrue(setUpUser.isPasswordWrong());
    }
}
