package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User")
public class UserTest {
    @Nested
    @DisplayName("User 빌더는")
    class Describe_UserBuilder {
        @Test
        @DisplayName("User를 생성한다.")
        void it_returns_user() {
            User user = User.builder()
                    .id(1L)
                    .email("honggd@gmail.com")
                    .name("홍길동")
                    .password("password")
                    .build();

            assertThat(user.getId()).isEqualTo(1L);
            assertThat(user.getEmail()).isEqualTo("honggd@gmail.com");
            assertThat(user.getName()).isEqualTo("홍길동");
            assertThat(user.getPassword()).isEqualTo("password");
        }
    }
}
