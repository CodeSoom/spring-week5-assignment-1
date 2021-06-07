package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private final String USER_NAME = "user";
    private final String USER_EMAIL = "email@aaa.bbb";
    private final String USER_PASSWORD = "password";

    @Nested
    @DisplayName("User 는")
    class DescribeUser {

        @Nested
        @DisplayName("빌더 패턴으로 만들면")
        class ContextWithBuilder {

            User user;

            @BeforeEach
            void prepareUserWithBuilder() {
                user = User.builder()
                           .name(USER_NAME)
                           .email(USER_EMAIL)
                           .password(USER_PASSWORD)
                           .build();
            }

            @Test
            @DisplayName("사용자 객체를 리턴한다")
            void itReturnsUser() {
                assertThat(user.getName()).isEqualTo(USER_NAME);
                assertThat(user.getEmail()).isEqualTo(USER_EMAIL);
                assertThat(user.getPassword()).isEqualTo(USER_PASSWORD);
            }
        }
    }
}
