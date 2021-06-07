package com.codesoom.assignment.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserDataTest {

    private final String USER_NAME = "user";
    private final String USER_EMAIL = "email@aaa.bbb";
    private final String USER_PASSWORD = "password";

    @Nested
    @DisplayName("UserData 는")
    class DescribeUserData {

        @Nested
        @DisplayName("사용자 정보가 주어지면")
        class ContextWithValidUserData {

            UserData userData;

            @BeforeEach
            void prepareUserData() {
                userData = UserData.builder()
                                   .name(USER_NAME)
                                   .email(USER_EMAIL)
                                   .password(USER_PASSWORD)
                                   .build();
            }

            @Test
            @DisplayName("사용자 정보 객체를 리턴한다")
            void itReturnsUserData() {
                assertThat(userData.getName()).isEqualTo(USER_NAME);
                assertThat(userData.getEmail()).isEqualTo(USER_EMAIL);
                assertThat(userData.getPassword()).isEqualTo(USER_PASSWORD);
            }
        }
    }
}
