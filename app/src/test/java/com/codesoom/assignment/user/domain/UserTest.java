package com.codesoom.assignment.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UserTest {

    @Test
    @DisplayName("회원을 생성한다.")
    void createWithBuilder() {
        Long givenId = 1L;
        String givenName = "Jamie";
        String givenEmail = "test@codesoom.com";
        String givenPassword = "1234!@#$";

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

    @Test
    @DisplayName("회원을 수정한다.")
    void updateUser() {
        User user = User.builder()
                .id(1L)
                .name("name")
                .email("email")
                .password("12345678")
                .build();

        User source = User.builder()
                .name("new name")
                .password("87654321")
                .build();

        User changedUser = user.changeWith(source);

        assertAll(
                () -> assertThat(changedUser.getName()).isEqualTo(source.getName()),
                () -> assertThat(changedUser.getPassword()).isEqualTo(source.getPassword())
        );
    }

}
