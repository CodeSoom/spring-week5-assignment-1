package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    private UserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
        user = userService.createUser(User.builder()
            .name("name")
            .email("email")
            .password("password")
            .build());
    }

    @Nested
    @DisplayName("updateUser 메서드는")
    class Describe_updateUser {

        private final User source = User.builder()
            .name("name2")
            .email("email2")
            .password("password2")
            .build();
        private Long id;

        @BeforeEach
        void setUp() {
            id = user.getId();
        }

        @Nested
        @DisplayName("존재하는 id인 경우")
        class Context_existId {

            @BeforeEach
            void setUp() {
                assertThat(userRepository.existsById(id))
                    .isTrue();
            }

            @Test
            @DisplayName("수정한 유저를 리턴한다")
            void it_returns_updated_user() {
                User updatedUser = userService.updateUser(id, source);

                assertThat(updatedUser.getName()).isEqualTo("name2");
                assertThat(updatedUser.getEmail()).isEqualTo("email2");
                assertThat(updatedUser.getPassword()).isEqualTo("password2");
            }
        }

        @Nested
        @DisplayName("존재하지 않는 id인 경우")
        class Context_notExistId {

            @BeforeEach
            void setUp() {
                userRepository.deleteAll();

                assertThat(userRepository.existsById(id))
                    .isFalse();
            }

            @Test
            @DisplayName("에러를 던진다")
            void it_throws() {
                assertThatThrownBy(() -> {
                    userService.updateUser(id, source);
                }).isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteUser 메서드는")
    class Describe_deleteUser {

        private Long id;

        @BeforeEach
        void setUp() {
            id = user.getId();
        }

        @Nested
        @DisplayName("존재하는 회원인 경우")
        class Context_findUser {

            @BeforeEach
            void setUp() {
                assertThat(userRepository.existsById(id)).isTrue();
            }

            @Test
            @DisplayName("삭제한다")
            void it_delete() {
                userService.deleteUser(id);

                assertThat(userRepository.existsById(id))
                    .isFalse();
            }
        }

        @Nested
        @DisplayName("회원을 찾지 못한 경우")
        class Context_notFoundUser {

            @BeforeEach
            void setUp() {
                userRepository.deleteAll();
            }

            @Test
            @DisplayName("에러를 던진다")
            void it_throws() {
                assertThatThrownBy(() ->
                    userService.deleteUser(id)
                ).isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
