package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    private static final String EMAIL = "abc@hello.com";
    private static final String NAME = "ABC";
    private static final String PASSWORD = "aa!@#5";

    @Test
    void user_generation_test() {
        User user = User.builder()
                .email(EMAIL)
                .name(NAME)
                .password(PASSWORD)
                .build();

        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo(NAME);
        assertThat(user.getEmail()).isEqualTo(EMAIL);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
    }
}
