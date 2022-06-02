package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    @Test
    void creationWithBuilder() {
        User user = User.builder()
                .id(1L)
                .name("caoyu")
                .email("choyumin01@gmail.com")
                .password("1234!@#$")
                .build();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("caoyu");
        assertThat(user.getEmail()).isEqualTo("choyumin01@gmail.com");
        assertThat(user.getPassword()).isEqualTo("1234!@#$");
    }

    @Test
    void update() {
        User user = User.builder()
                .id(1L)
                .name("caoyu")
                .email("choyumin01@gmail.com")
                .password("1234!@#$")
                .build();

        user.update("애옹이", "choyumin01@gmail.com", "!@#$1234");

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("애옹이");
        assertThat(user.getEmail()).isEqualTo("choyumin01@gmail.com");
        assertThat(user.getPassword()).isEqualTo("!@#$1234");
    }
}
