package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserCreateRequest;
import com.codesoom.assignment.dto.UserUpdateRequest;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

    private UserUpdateRequest userUpdateRequest;
    private UserCreateRequest userCreateRequest;

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        userService = new UserService(userRepository, mapper);

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

        userCreateRequest = userCreateRequest.builder()
                .name("새 이름")
                .email("newEmail@example.com")
                .password("12345678")
                .build();

        userUpdateRequest = userUpdateRequest.builder()
                .name("이름수정")
                .password("87654321")
                .build();

        given(userRepository.findById(existingId))
                .willReturn(Optional.of(user1));

        given(userRepository.findById(notExistingId))
                .willReturn(Optional.empty());
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
            @Test
            @DisplayName("'회원을 찾을 수 없다' 는 예외가 발생한다.")
            void it_throws_exception() {
                assertThrows(UserNotFoundException.class,
                        () -> userService.getUser(notExistingId));

                verify(userRepository).findById(notExistingId);
            }
        }
    }

    @Nested
    @DisplayName("createUser")
    class Describe_createUser {
        @BeforeEach
        void setUp() {
            given(userRepository.save(any(User.class)))
                    .will(invocation -> invocation.<User>getArgument(0));
        }

        @Test
        @DisplayName("생성된 회원을 리턴한다.")
        void it_return_the_created_user() {
            User createdUser = userService.createUser(userCreateRequest);

            assertThat(createdUser.getName()).isEqualTo(userCreateRequest.getName());
            assertThat(createdUser.getEmail()).isEqualTo(userCreateRequest.getEmail());
            assertThat(createdUser.getPassword()).isEqualTo(userCreateRequest.getPassword());
        }
    }

    @Nested
    @DisplayName("updateUser")
    class Describe_updateUser {
        @Nested
        @DisplayName("존재하는 회원 id가 주어진다면")
        class Context_with_an_existing_user_id {
            @Test
            @DisplayName("수정된 회원을 리턴한다.")
            void it_returns_the_updated_user() {
                User updatedUser = userService.updateUser(existingId, userUpdateRequest);

                assertThat(updatedUser.getName()).isEqualTo(userUpdateRequest.getName());
                assertThat(updatedUser.getPassword()).isEqualTo(userUpdateRequest.getPassword());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 회원 id가 주어진다면")
        class Context_with_not_existing_user_id {
            @Test
            @DisplayName("'회원을 찾을 수 없다' 는 예외가 발생한다.")
            void it_throws_exception() {
                assertThrows(UserNotFoundException.class,
                        () -> userService.updateUser(notExistingId, userUpdateRequest));

                verify(userRepository).findById(notExistingId);
            }
        }
    }

    @Nested
    @DisplayName("deleteUser")
    class Describe_deleteUser {
        @Nested
        @DisplayName("존재하는 회원 id가 주어진다면")
        class Context_with_an_existing_user_id {
            @Test
            @DisplayName("회원을 삭제한다.")
            void it_deletes_the_user() {
                userService.deleteUser(existingId);

                verify(userRepository).delete(any(User.class));
            }
        }

        @Nested
        @DisplayName("존재하지 않는 회원 id가 주어진다면")
        class Context_with_not_existing_user_id {
            @Test
            @DisplayName("'회원을 찾을 수 없다' 는 예외가 발생한다.")
            void it_throws_exception() {
                assertThrows(UserNotFoundException.class,
                        () -> userService.deleteUser(notExistingId));

                verify(userRepository).findById(notExistingId);
            }
        }
    }

}
