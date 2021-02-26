package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("회원을 생성한다.")
    void createUser() {
        Long givenId = 1L;
        String givenName = "mikeKang47";
        String givenEmail = "test@github.com";
        String givenPassword = "qwer1234";

        User user = User.builder()
                .id(givenId)
                .name(givenName)
                .email(givenEmail)
                .password(givenPassword)
                .build();

        assertAll(
                () -> assertThat(user.getId()).isEqualTo(givenId),
                () -> assertThat(user.getName()).isEqualTo(givenName),
                () -> assertThat(user.getEmail()).isEqualTo(givenEmail),
                () -> assertThat(user.getPassword()).isEqualTo(givenPassword)
        );
    }

}