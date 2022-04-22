package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.users.User;
import com.codesoom.assignment.domain.users.UserRepository;
import com.codesoom.assignment.domain.users.UserSaveRequest;
import com.codesoom.assignment.domain.users.UserUpdateRequest;
import com.codesoom.assignment.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@DisplayName("UserService 클래스 테스트")
class UserServiceTest {

    private final static String TEST_USER_EMAIL = "test@example.com";
    private final static String TEST_USER_NAME = "test";
    private final static String TEST_USER_PASSWORD = "1234";

    private final static Long TEST_UPDATE_USER_ID = 111L;
    private final static String TEST_UPDATE_USER_EMAIL = "update_test@example.com";
    private final static String TEST_UPDATE_USER_NAME = "update_test";
    private final static String TEST_UPDATE_USER_PASSWORD = "update_1234";

    private final static Long TEST_DELETE_USER_ID = 999L;

    UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
        userRepository.deleteAll();
    }

    @Nested
    @DisplayName("saveUser 메소드는")
    class Describe_saveUser {

        @Nested
        @DisplayName("주어진 저장 요청대로")
        class Context_givenSaveRequest {

            UserSaveRequest saveRequest = new UserSaveRequest() {
                @Override
                public String getEmail() {
                    return TEST_USER_EMAIL;
                }

                @Override
                public String getName() {
                    return TEST_USER_NAME;
                }

                @Override
                public String getPassword() {
                    return TEST_USER_PASSWORD;
                }
            };

            @Test
            @DisplayName("회원을 저장후 저장된 회원을 리턴한다.")
            void it_save_and_return_user() {

                User savedUser = userService.saveUser(saveRequest);

                assertAll(
                        () -> assertThat(savedUser.getId()).isNotNull(),
                        () -> assertThat(savedUser.getEmail()).isEqualTo(TEST_USER_EMAIL),
                        () -> assertThat(savedUser.getName()).isEqualTo(TEST_USER_NAME),
                        () -> assertThat(savedUser.getPassword()).isEqualTo(TEST_USER_PASSWORD)
                );
            }
        }
    }

    @Nested
    @DisplayName("getUser 메소드는")
    class Describe_getUser {

        @Nested
        @DisplayName("주어진 아이디와 일치하는 회원이 있다면")
        class Context_existUserId {

            Long existUserid;

            @BeforeEach
            void setUp() {
                User savedUser = User.builder()
                        .build();
                userRepository.save(savedUser);

                existUserid = savedUser.getId();
            }

            @Test
            @DisplayName("회원을 리턴한다.")
            void it_return_user() {

                User foundUser = userService.getUser(existUserid);

                assertThat(foundUser.getId()).isEqualTo(existUserid);
            }
        }

        @Nested
        @DisplayName("주어진 아이디와 일치하는 회원이 없다면")
        class Context_notExistUserId {

            final Long notExistUserId = 999L;

            @BeforeEach
            void setUp() {
                userRepository.findById(notExistUserId)
                        .ifPresent(user -> userRepository.deleteById(user.getId()));
            }

            @Test
            @DisplayName("예외를 던진다")
            void it_throw_exception() {
                assertThatThrownBy(() -> userService.getUser(notExistUserId))
                        .isInstanceOf(UserNotFoundException.class)
                        .hasMessageContaining(notExistUserId.toString());
            }
        }
    }

    @Nested
    @DisplayName("updateUser 메소드는")
    class Describe_updateUser {

        final User user = User.builder()
                .id(TEST_UPDATE_USER_ID)
                .email(TEST_USER_EMAIL)
                .name(TEST_USER_NAME)
                .password(TEST_USER_PASSWORD)
                .build();

        @Nested
        @DisplayName("주어진 수정 요청대로")
        class Context_givenUpdateRequest {

            UserUpdateRequest updateRequest = new UserUpdateRequest() {
                @Override
                public String getEmail() {
                    return TEST_UPDATE_USER_EMAIL;
                }

                @Override
                public String getName() {
                    return TEST_UPDATE_USER_NAME;
                }

                @Override
                public String getPassword() {
                    return TEST_UPDATE_USER_PASSWORD;
                }
            };

            @Test
            @DisplayName("회원을 수정하고 수정된 회원을 리턴한다.")
            void it_update_and_user_return() {

                final User expectedUser = User.builder()
                        .id(TEST_UPDATE_USER_ID)
                        .email(TEST_UPDATE_USER_EMAIL)
                        .name(TEST_UPDATE_USER_NAME)
                        .password(TEST_UPDATE_USER_PASSWORD)
                        .build();

                final User updatedUser = userService.updateUser(user, updateRequest);

                assertThat(updatedUser).isEqualTo(expectedUser);
            }
        }
    }

    @Nested
    @DisplayName("deleteUser 메소드는")
    class Describe_deleteUser {

        User user = User.builder()
                .id(TEST_DELETE_USER_ID)
                .build();

        @Nested
        @DisplayName("주어진 회원이 저장소에 있다면")
        class Context_existUser {

            @BeforeEach
            void setUp() {
                userRepository.save(user);
            }

            void subject() {
                userService.deleteUser(user);
            }

            @Test
            @DisplayName("회원을 삭제한다.")
            void it_delete() {

                subject();

                Optional<User> foundUser = userRepository.findById(TEST_DELETE_USER_ID);

                assertThat(foundUser.isEmpty()).isTrue();
            }
        }
    }
}
