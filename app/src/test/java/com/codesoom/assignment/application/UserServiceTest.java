package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserService 에서")
class UserServiceTest {
    private final static String USERNAME = "username1";
    private final static String EMAIL = "example@example.com";
    private final static String PASSWORD = "password";

    private TestUserServiceImpl userService;

    @BeforeEach
    void setUp() {
        this.userService = new TestUserServiceImpl();
    }

    @Nested
    @DisplayName("createUser 메소드는")
    class Describe_of_create_user {

        @Nested
        @DisplayName("생성할 수 있는 사용자의 데이터가 주어지면")
        class Context_with_valid_user_data {
            private final UserData userData = UserData.builder()
                    .username(USERNAME)
                    .email(EMAIL)
                    .password(PASSWORD)
                    .build();

            @Test
            @DisplayName("사용자를 생성한 후, 생성된 사용자를 리턴한다")
            void it_create_and_return_user() {
                User user = userService.createUser(userData);

                assertThat(user).isNotNull();
            }
        }
    }

}