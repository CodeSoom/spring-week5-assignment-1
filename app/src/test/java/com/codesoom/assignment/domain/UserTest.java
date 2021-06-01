package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void creationBuilder(){
        User user = User.builder()
                .id(1L)
                .name("RoDaJu")
                .email("ironman@marvel.com")
                .password("3000").build();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("RoDaJu");
        assertThat(user.getEmail()).isEqualTo("ironman@marvel.com");
        assertThat(user.getPassword()).isEqualTo("3000");
    }
}