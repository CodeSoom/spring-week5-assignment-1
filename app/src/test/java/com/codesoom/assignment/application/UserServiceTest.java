package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.print.attribute.standard.MediaSize;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("UserService 클래스")
class UserServiceTest {
    final String NAME = "My Name";
    final String EMAIL = "name@gmail.com";
    final String PASSWORD = "password";

    @Autowired
    private UserService userService;

    @DisplayName("create()")
    @Nested
    class Describe_create {
        @Nested
        @DisplayName("유저 정보가 주어진다면")
        class Context_with_user {
            User givenUser;

            @BeforeEach
            void setUp() {
                givenUser = User.builder()
                        .name(NAME)
                        .email(EMAIL)
                        .password(PASSWORD)
                        .build();
            }

            User subject() {
                return userService.create(givenUser);
            }

            @DisplayName("생성된 user를 반환한다")
            @Test
            void it_returns_user() {
                User user = subject();

                assertThat(user.getName()).isEqualTo(NAME);
                assertThat(user.getEmail()).isEqualTo(EMAIL);
                assertThat(user.getPassword()).isEqualTo(PASSWORD);
            }
        }
    }
}
