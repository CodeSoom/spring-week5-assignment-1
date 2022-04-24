package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName("UserCommandService 에서")
class UserCommandServiceTest {
    private final static String USERNAME = "username1";
    private final static String EMAIL = "example@example.com";
    private final static String PASSWORD = "password";

    @Autowired
    private UserRepository userRepository;
    private UserCommandService userCommandService;

    @BeforeEach
    void setUp() {
        this.userCommandService = new UserCommandService(userRepository);
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
                User user = userCommandService.createUser(userData);

                assertThat(user).isNotNull();
            }
        }
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_of_delete_user {

        @Nested
        @DisplayName("삭제할 수 있는 ID가 주어지면")
        class Context_with_valid_id {
            private Long userId;

            @BeforeEach
            void setUp() {
                UserData userData = UserData.builder()
                        .username(USERNAME)
                        .email(EMAIL)
                        .password(PASSWORD)
                        .build();
                User user = userRepository.save(userData);
                userId = user.getId();
            }

            @Test
            @DisplayName("ID에 해당하는 사용자를 삭제한다")
            void it_delete_user() {
                userCommandService.delete(userId);

                assertThatThrownBy(() -> userRepository.findById(userId))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

}