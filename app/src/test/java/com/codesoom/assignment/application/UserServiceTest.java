package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserService 클래스")
class UserServiceTest {

    private UserService userService;

    private UserRepository userRepository = mock(UserRepository.class);

    private Long existingId = 1L;
    private Long notExistingId = 100L;

    private List<User> users;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);

        user1 = User.builder()
                .id(1L)
                .name("이름")
                .email("이메일")
                .password("password")
                .build();

        user2 = User.builder()
                .id(2L)
                .name("이름2")
                .email("이메일2")
                .password("password2")
                .build();
    }

    @Nested
    @DisplayName("getUsers")
    class Describe_getUsers {
        @Nested
        @DisplayName("저장된 회원이 여러명 있다면")
        class Context_with_users {
            @BeforeEach
            void setUp() {
                users = List.of(user1, user2);

                given(userRepository.findAll())
                        .willReturn(users);
            }

            @Test
            @DisplayName("모든 회원 목록을 리턴한다.")
            void it_returns_all_user_list() {
                assertThat(userService.getUsers()).hasSize(2);
            }
        }

        @Nested
        @DisplayName("저장된 회원이 없다면")
        class Context_without_users {
            @BeforeEach
            void setUp() {
                given(userRepository.findAll())
                        .willReturn(List.of());
            }

            @Test
            @DisplayName("비어있는 회원 목록을 리턴한다.")
            void it_returns_empty_user_list() {
                assertThat(userService.getUsers()).hasSize(0);
            }
        }
    }

    @Nested
    @DisplayName("getUser")
    class Describe_getUser {
        @Nested
        @DisplayName("존재하는 회원 id가 주어진다면")
        class Context_with_an_existing_user_id {
            @BeforeEach
            void setUp() {
                given(userRepository.findById(existingId))
                        .willReturn(Optional.of(user1));
            }

            @Test
            @DisplayName("찾은 회원을 리턴한다.")
            void it_returns_the_found_user() {
                User foundUser = userService.getUser(existingId);

                verify(userRepository).findById(existingId);

                assertThat(foundUser.getId()).isEqualTo(user1.getId());
                assertThat(foundUser.getName()).isEqualTo(user1.getName());
                assertThat(foundUser.getEmail()).isEqualTo(user1.getEmail());
                assertThat(foundUser.getPassword()).isEqualTo(user1.getPassword());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 회원 id가 주어진다면")
        class Context_with_not_existing_user_id {
            @BeforeEach
            void setUp() {
                given(userRepository.findById(existingId))
                        .willReturn(Optional.empty());
            }

            @Test
            @DisplayName("'회원을 찾을 수 없다' 는 예외가 발생한다.")
            void it_throws_exception() {
                assertThrows(UserNotFoundException.class,
                        () -> userService.getUser(notExistingId));

                verify(userRepository).findById(notExistingId);
            }
        }
    }

}
