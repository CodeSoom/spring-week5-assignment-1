package com.codesoom.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .name("name")
            .email("email")
            .password("password")
            .build();
    }

    @Test
    void createUser() {
        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getEmail()).isEqualTo("email");
        assertThat(user.getPassword()).isEqualTo("password");
    }

    @Test
    void changeInfo() {
        User source = User.builder()
            .name("name2")
            .email("email2")
            .password("password2")
            .build();

        User updatedUser = user.changeInfo(source);

        assertThat(updatedUser.getName())
            .isEqualTo("name2");
        assertThat(updatedUser.getEmail())
            .isEqualTo("email2");
        assertThat(updatedUser.getPassword())
            .isEqualTo("password2");
    }
}
