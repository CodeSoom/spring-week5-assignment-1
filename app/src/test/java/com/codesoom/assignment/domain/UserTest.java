package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Test
    void creationWithBuilder() {
        User user = User.builder()
                .id(1L)
                .name("홍길")
                .email("test@gmail.com")
                .password("1234")
                .build();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("홍길동");
        assertThat(user.getEmail()).isEquallTo("test@gmail.com");
        assertThat(user.getPassword()).isEquallTo("1234");
    }
}
