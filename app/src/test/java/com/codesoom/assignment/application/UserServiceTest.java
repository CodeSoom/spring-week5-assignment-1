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

    @Test
    void create() {
        User createdUser = userService.createUser(User.builder()
            .name("name")
            .email("email")
            .password("password")
            .build());

        assertThat(createdUser.getName()).isEqualTo("name");
        assertThat(createdUser.getEmail()).isEqualTo("email");
        assertThat(createdUser.getPassword()).isEqualTo("password");
    }

    @Nested
    @DisplayName("updateUser 메서드는")
    class Describe_updateUser {

        @Nested
        @DisplayName("존재하는 id인 경우")
        class Context_existId {

            private Long existId;

            @BeforeEach
            void setUp() {
                assertThat(userRepository.existsById(user.getId()))
                    .isTrue();

                existId = user.getId();
            }

            @Test
            @DisplayName("수정한 유저를 리턴한다")
            void it_returns_updated_user() {
                User source = User.builder()
                    .name("name2")
                    .email("email2")
                    .password("password2")
                    .build();
                User updatedUser = userService.updateUser(existId, source);

                assertThat(updatedUser.getName()).isEqualTo("name2");
                assertThat(updatedUser.getEmail()).isEqualTo("email2");
                assertThat(updatedUser.getPassword()).isEqualTo("password2");
            }
        }

        @Nested
        @DisplayName("존재하지 않는 id인 경우")
        class Context_notExistId {

            private Long notExistId;

            @BeforeEach
            void setUp() {
                userRepository.deleteAll();

                assertThat(userRepository.existsById(user.getId()))
                    .isFalse();

                notExistId = user.getId();
            }

            @Test
            @DisplayName("에러를 던진다")
            void it_throws() {
                User source = User.builder().build();

                assertThatThrownBy(() -> {
                    userService.updateUser(notExistId, source);
                }).isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteUser 메서드는")
    class Describe_deleteUser {

        @Nested
        @DisplayName("존재하는 회원인 경우")
        class Context_findUser {

            private Long findUserId;

            @BeforeEach
            void setUp() {
                Long id = user.getId();

                assertThat(userRepository.existsById(id)).isTrue();

                findUserId = id;
            }

            @Test
            @DisplayName("삭제한다")
            void it_delete() {
                userService.deleteUser(findUserId);

                assertThat(userRepository.existsById(findUserId))
                    .isFalse();
            }
        }

        @Nested
        @DisplayName("회원을 찾지 못한 경우")
        class Context_notFoundUser {

            private Long notFoundUserId;

            @BeforeEach
            void setUp() {
                userRepository.deleteAll();

                notFoundUserId = user.getId();
            }

            @Test
            @DisplayName("에러를 던진다")
            void it_throws() {
                assertThatThrownBy(() ->
                    userService.deleteUser(notFoundUserId)
                ).isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
