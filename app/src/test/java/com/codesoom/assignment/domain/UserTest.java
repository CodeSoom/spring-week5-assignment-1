package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Test
    void creationWithBuilder() {
        User user = User.builder()
                .id(1L)
                .name("johndoe")
                .email("johndoe@gmail.com")
                .password("verysecret")
                .build();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("johndoe");
        assertThat(user.getEmail()).isEqualTo("johndoe@gmail.com");
        assertThat(user.getPassword()).isEqualTo("verysecret");
        assertThat(User.builder().toString())
                .isEqualTo("User.UserBuilder(id=null, name=null, email=null, password=null)");
    }

    @Test
    void change() {
        User user = User.builder()
                .id(1L)
                .name("johndoe")
                .email("johndoe@gmail.com")
                .password("verysecret")
                .build();

        user.update("janedoe", "janedoe@gmail.com", "verysecret!");
        assertThat(user.getName()).isEqualTo("janedoe");
        assertThat(user.getEmail()).isEqualTo("janedoe@gmail.com");
        assertThat(user.getPassword()).isEqualTo("verysecret!");
    }
}