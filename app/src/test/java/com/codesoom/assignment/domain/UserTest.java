package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    @Test
    void creationWithBuilder() {
        User user = User.builder()
                .id(1L)
                .name("삼돌이")
                .email("jihwooon@gmail.com")
                .password("1234")
                .build();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("삼돌이");
        assertThat(user.getEmail()).isEqualTo("jihwooon@gmail.com");
        assertThat(user.getPassword()).isEqualTo(1234);
    }

    @Test
    void changeWithUser() {
        User user = User.builder()
                .id(1L)
                .name("삼돌이")
                .email("jihwooon@gmail.com")
                .password("1234")
                .build();

        user.changeWithUser(User.builder()
                .name("삼순이")
                .email("jung@gmail.com")
                .password("12345")
                .build());

        assertThat(user.getName()).isEqualTo("삼순이");
        assertThat(user.getEmail()).isEqualTo("jung@gmail.com");
        assertThat(user.getPassword()).isEqualTo(12345);
    }
}

