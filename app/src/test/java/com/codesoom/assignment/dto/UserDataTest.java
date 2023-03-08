package com.codesoom.assignment.dto;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


class UserDataTest {

    @Test
    void builder() {
        UserData userData = UserData.builder()
                .id(1L)
                .name("name")
                .email("email@naver.com")
                .password("password")
                .build();

        String userDataToString = UserData.builder()
                .id(1L)
                .name("name")
                .email("email@naver.com")
                .password("password")
                .toString();

        assertThat(userData.getId()).isEqualTo(1L);
        assertThat(userData.getName()).isEqualTo("name");
        assertThat(userData.getEmail()).isEqualTo("email@naver.com");
        assertThat(userData.getPassword()).isEqualTo("password");
    }
}