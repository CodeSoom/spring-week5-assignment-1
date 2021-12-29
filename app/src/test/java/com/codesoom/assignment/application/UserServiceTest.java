package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("UserService")
public class UserServiceTest {
    private static final String EXISTED_USER_NAME = "홍길동";
    private static final String EXISTED_USER_EMAIL = "hong@gmail.com";
    private static final String EXISTED_USER_PASSWORD = "password";
    private static final Long NOT_EXISTED_USER_ID = 0L;

    User exitedUser;
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void prepareUserService() {
        userRepository.deleteAll();
        userService = new UserService(userRepository);
    }

    void prepareExitedUser() {
        User user = User.builder()
                .name(EXISTED_USER_NAME)
                .email(EXISTED_USER_EMAIL)
                .password(EXISTED_USER_PASSWORD)
                .build();
        exitedUser = userRepository.save(user);
    }

    @Nested
    @DisplayName("findAllUsers는")
    class Describe_findAllUsers {
        List<User> subject() {
            return userService.findAllUsers();
        }

        @Nested
        @DisplayName("등록된 유저가 있다면")
        class Context_with_existed_user {
            @BeforeEach
            void prepare() {
                prepareExitedUser();
            }

            @Test
            @DisplayName("등록된 모든 유저들의 목록을 리턴한다.")
            void it_returns_list_of_exited_users() {
                List<User> result = subject();
                assertThat(result).hasSize(1);
                assertThat(result.get(0).getName()).isEqualTo(EXISTED_USER_NAME);
                assertThat(result.get(0).getEmail()).isEqualTo(EXISTED_USER_EMAIL);
                assertThat(result.get(0).getPassword()).isEqualTo(EXISTED_USER_PASSWORD);
            }
        }
    }

    @Nested
    @DisplayName("findUserByID는")
    class Describe_findUserById {
        User subject(Long id) {
            return userService.findUserById(id);
        }

        @Nested
        @DisplayName("등록된 유저의 id가 주어진다면")
        class Context_with_existed_user_id {
            @BeforeEach
            void prepare() {
                prepareExitedUser();
            }

            @Test
            @DisplayName("해당 id의 유저를 리턴한다.")
            void it_returns_user() {
                User result = subject(exitedUser.getId());
                assertThat(result.getName()).isEqualTo(exitedUser.getName());
                assertThat(result.getEmail()).isEqualTo(exitedUser.getEmail());
                assertThat(result.getPassword()).isEqualTo(exitedUser.getPassword());
            }
        }

        @Nested
        @DisplayName("등록되지않은 유저의 id가 주어진다면")
        class Context_with_not_existed_user_id {
            @Test
            @DisplayName("'유저를 찾을수 없다'는 예외를 던진다.")
            void it_throws_not_found_user_exception() {
                assertThatThrownBy(() -> subject(NOT_EXISTED_USER_ID)).isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("createUser는")
    class Describe_createUser {
        User subject(User user) {
            return userService.createUser(user);
        }

        @Nested
        @DisplayName("유저가 주어진다면")
        class Context_with_user {
            private static final String NEW_USER_NAME = "new_홍길동";
            private static final String NEW_USER_EMAIL = "new_hong@gmail.com";
            private static final String NEW_USER_PASSWORD = "new_password";

            User newUser;

            @BeforeEach
            void prepareNewUser() {
                newUser = User.builder()
                        .name(NEW_USER_NAME)
                        .email(NEW_USER_EMAIL)
                        .password(NEW_USER_PASSWORD)
                        .build();
            }

            @Test
            @DisplayName("유저를 생성하고 리턴한다.")
            void it_returns_created_user() {
                User result = subject(newUser);
                assertThat(result.getName()).isEqualTo(newUser.getName());
                assertThat(result.getEmail()).isEqualTo(newUser.getEmail());
                assertThat(result.getPassword()).isEqualTo(newUser.getPassword());
            }
        }
    }

    @Nested
    @DisplayName("updateUser는")
    class Describe_updateUser {
        private static final String UPDATE_USER_NAME = "new_홍길동";
        private static final String UPDATE_USER_EMAIL = "new_hong@gmail.com";
        private static final String UPDATE_USER_PASSWORD = "new_password";

        User updateUser;

        User subject(Long id, User source) {
            return userService.updateUser(id, source);
        }

        @BeforeEach
        void prepareUpdateUser() {
            updateUser = User.builder()
                    .name(UPDATE_USER_NAME)
                    .email(UPDATE_USER_EMAIL)
                    .password(UPDATE_USER_PASSWORD)
                    .build();
        }

        @BeforeEach
        void prepare() {
            prepareExitedUser();
        }

        @Nested
        @DisplayName("등록된 유저 id가 주어진다면")
        class Context_with_exited_user_id {
            @Test
            @DisplayName("해당 id의 유저를 주어진 유저와 일치하도록 변경하고 리턴한다.")
            void it_returns_updated_user() {
                User result = subject(exitedUser.getId(), updateUser);
                assertThat(result.getName()).isEqualTo(updateUser.getName());
                assertThat(result.getEmail()).isEqualTo(updateUser.getEmail());
                assertThat(result.getPassword()).isEqualTo(updateUser.getPassword());
            }
        }

        @Nested
        @DisplayName("등록되지않은 유저의 id가 주어진다면")
        class Context_with_not_existed_user_id {
            @Test
            @DisplayName("'유저를 찾을수 없다'는 예외를 던진다.")
            void it_throws_not_found_user_exception() {
                assertThatThrownBy(() -> subject(NOT_EXISTED_USER_ID, updateUser)).isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteUser는")
    class Describe_deleteUser {
        void subject(Long id) {
            userService.deleteUser(id);
        }

        @BeforeEach
        void prepare() {
            prepareExitedUser();
        }

        @Nested
        @DisplayName("등록된 유저 id가 주어진다면")
        class Context_exited_user_id {
            @Test
            @DisplayName("해당 id의 유저를 삭제한다.")
            void it_deletes_user() {
                subject(exitedUser.getId());
                assertThat(userRepository.findById(exitedUser.getId()).isEmpty()).isTrue();
            }
        }

        @Nested
        @DisplayName("등록되지않은 유저의 id가 주어진다면")
        class Context_with_not_existed_user_id {
            @Test
            @DisplayName("'유저를 찾을수 없다'는 예외를 던진다.")
            void it_throws_not_found_user_exception() {
                assertThatThrownBy(() -> subject(NOT_EXISTED_USER_ID)).isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
