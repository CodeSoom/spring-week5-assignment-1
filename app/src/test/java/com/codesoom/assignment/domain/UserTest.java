package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    @Test
    void createWithBuilder() {
        User user = User.builder()
                .name("김철수")
                .email("kim@gmail.com")
                .password("1111")
                .build();

        assertThat(user.getName()).isEqualTo("김철수");
        assertThat(user.getEmail()).isEqualTo("kim@gmail.com");
        assertThat(user.getPassword()).isEqualTo("1111");
    }

    @Test
    void update() {
        User user = User.builder()
                .name("김철수")
                .email("kim@gmail.com")
                .password("1111")
                .build();

        user.update("김영희", "young@gmail.com", "2222");

        assertThat(user.getName()).isEqualTo("김영희");
        assertThat(user.getEmail()).isEqualTo("young@gmail.com");
        assertThat(user.getPassword()).isEqualTo("2222");
    }
}
