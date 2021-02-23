package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("UserService 클래스")
class UserServiceTest {
    final String NAME = "My Name";
    final String EMAIL = "my@gmail.com";
    final String PASSWORD = "My Password";

    final String UPDATE_NAME = "Your Name";
    final String UPDATE_EMAIL = "user@gmail.com";
    final String UPDATE_PASSWORD = "Your Password";

    @Autowired
    private UserService userService;

    //subject
    User createUser() {
        return User.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    @AfterEach
    void tearDown() {
        userService.clearData();
    }

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

    @DisplayName("update()")
    @Nested
    class Describe_update {
        User subject(Long id, User source) {
            return userService.update(id, source);
        }

        @Nested
        @DisplayName("존재하는 user id와 user가 주어진다면")
        class Context_with_exist_user_id {
            Long givenId;
            User source;

            @BeforeEach
            void setUp() {
                User givenUser = createUser();
                givenId = userService.create(givenUser).getId();
                source = User.builder()
                        .name(UPDATE_NAME)
                        .email(UPDATE_EMAIL)
                        .password(UPDATE_PASSWORD)
                        .build();
            }

            @DisplayName("수정된 user를 반환한다")
            @Test
            void it_returns_user() {
                User user = subject(givenId, source);

                assertThat(user.getName()).isEqualTo(UPDATE_NAME);
                assertThat(user.getEmail()).isEqualTo(UPDATE_EMAIL);
                assertThat(user.getPassword()).isEqualTo(UPDATE_PASSWORD);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 user id와 user가 주어진다면")
        class Context_with_not_exist_user_id {

        }
    }
}
