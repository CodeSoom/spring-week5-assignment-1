package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserCommandServiceTest {
    @Autowired
    UserCommandService userCommandService;

    @Nested
    @DisplayName("createUser 메서드는")
    class Describe_createUser {
        @Nested
        @DisplayName("User 가 주어진다면")
        class Context_with_user {
            private UserRequest requestUser;

            @BeforeEach
            void setUp() {
                requestUser = UserRequest.builder()
                        .email("a@a.com")
                        .password("123")
                        .build();
            }

            @AfterEach
            void after() {
                userCommandService.deleteAll();
            }

            @Test
            @DisplayName("User 를 저장하고 리턴한다")
            void it_returns_user() {
                User savedUser = userCommandService.createUser(requestUser);

                assertThat(savedUser.getEmail()).isEqualTo(requestUser.getEmail());
                assertThat(savedUser.getPassword()).isEqualTo(requestUser.getPassword());
            }
        }
    }
}