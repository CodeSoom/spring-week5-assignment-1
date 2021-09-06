package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJdbcTest
@DisplayName("UserService 테스트")
class UserServiceTest {

    private final String NAME = "이름1";
    private final String PASSWORD = "비밀번호1";
    private final String EMAIL = "이메일1";

    private User user;
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
        user = User.builder()
                .name(NAME)
                .password(PASSWORD)
                .email(EMAIL).build();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Nested
    @DisplayName("create 메소드")
    class Describe_create {

        @Test
        @DisplayName("생성된 유저를 반환합니다.")
        void it_return_created_user() {
            User createdUser = userService.create(user);

            assertThat(createdUser.getName()).isEqualTo(NAME);
            assertThat(createdUser.getPassword()).isEqualTo(PASSWORD);
            assertThat(createdUser.getEmail()).isEqualTo(EMAIL);
        }
    }

    @Nested
    @DisplayName("update 메소드")
    class Describe_update {

        private User newUser;
        private final String NEW_NAME = "이름2";
        private final String NEW_PASSWORD = "비밀번호2";
        private final String NEW_EMAIL = "이메일2";

        @BeforeEach
        void prepare() {
            newUser = User.builder()
                    .name(NEW_NAME)
                    .password(NEW_PASSWORD)
                    .email(NEW_EMAIL).build();
        }

        @Nested
        @DisplayName("저장된 유저의 id가 주어지면")
        class Context_with_existing_user_id {

            private Long valid_id;

            @BeforeEach
            void prepare() {
                User givenUser = userRepository.save(user);
                valid_id = givenUser.getId();
            }

            @Test
            @DisplayName("변경된 유저를 반환합니다.")
            void it_return_created_user() {
                User updatedUser = userService.update(valid_id, newUser);

                assertThat(updatedUser.getName()).isEqualTo(NEW_NAME);
                assertThat(updatedUser.getPassword()).isEqualTo(NEW_PASSWORD);
                assertThat(updatedUser.getEmail()).isEqualTo(NEW_EMAIL);
            }
        }

        @Nested
        @DisplayName("저장되지 않는 유저의 id가 주어지면")
        class Context_with_not_existing_user_id {

            private Long invalid_id;

            @BeforeEach
            void prepare() {
                userRepository.deleteAll();
                invalid_id = 1L;
            }

            @Test
            @DisplayName("UserNotFoundException을 던집니다.")
            void it_throw_UserNotFoundException() {
                assertThatThrownBy(()->userService.update(invalid_id, newUser))
                        .isInstanceOf(UserNotFoundException(invalid_id));
            }
        }
    }

    @Nested
    @DisplayName("delete 메소드")
    class Describe_delete {

        @Nested
        @DisplayName("저장되지 않는 유저의 id가 주어지면")
        class Context_with_not_existing_user_id {

            private Long invalid_id;

            @BeforeEach
            void prepare() {
                userRepository.deleteAll();
                invalid_id = 1L;
            }

            @Test
            @DisplayName("UserNotFoundException을 던집니다.")
            void it_throw_UserNotFoundException() {
                assertThatThrownBy(()->userService.delete(invalid_id))
                        .isInstanceOf(UserNotFoundException(invalid_id));
            }
        }
    }
}