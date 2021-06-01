package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User 엔티티")
class UserTest {

    @Test
    @DisplayName("유저를 빌더로 생성할 수 있다")
    void it_can_build_with_builder() {
        User user = User.builder()
                .id(1L)
                .name("name")
                .email("email")
                .password("password")
                .build();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getEmail()).isEqualTo("email");
        assertThat(user.getPassword()).isEqualTo("password");
    }
}
