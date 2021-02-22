package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Test
    void creationWithBuilder() {
        User user = User.builder()
                .id(1L)
                .name("newoo")
                .email("newoo@codesoom.com")
                .password("codesoom123")
                .build();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("newoo");
        assertThat(user.getEmail()).isEqualTo("newoo@codesoom.com");
        assertThat(user.getPassword()).isEqualTo("codesoom123");
    }
}