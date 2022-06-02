package com.codesoom.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void creationWithBuilder() {
        User user = User.builder()
                .id(1L)
                .name("이호경")
                .password("12345#")
                .email("test@naver.com")
                .build();

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("이호경");
        assertThat(user.getPassword()).isEqualTo("12345#");
        assertThat(user.getEmail()).isEqualTo("test@naver.com");
    }
}
