package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.userdata.UserCreateData;
import com.codesoom.assignment.dto.userdata.UserUpdateData;
import com.codesoom.assignment.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@Nested
@DisplayName("UserService 클래스의")
class UserServiceTest {
    private UserService userService;

    private final UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void userServiceSetUp() {
        this.userService = new UserService(this.userRepository);
    }

    @Nested
    @DisplayName("getUsers 메소드는")
    class DescribeGetUsers {

        private List<User> users;

        @Nested
        @DisplayName("유저가 있을 때")
        class ContextWithUsers {

            @BeforeEach
            void userListSetUp() {
                User user1 = User.builder()
                        .name("Jack")
                        .email("jack@email.com")
                        .password("qwer1234")
                        .build();

                User user2 = User.builder()
                        .name("Wilson")
                        .email("wilson@email.com")
                        .password("1234qwer")
                        .build();

                users = new ArrayList<>();
                users.add(user1);
                users.add(user2);

                given(userRepository.findAll())
                        .willReturn(users);
            }

            @Test
            @DisplayName("모든 유저를 포함한 목록을 반환한다")
            void ItReturnsUserList() {
                assertThat(userService.getUsers())
                        .isEqualTo(users);
            }
        }

        @Nested
        @DisplayName("유저가 없을 때")
        class ContextWithNoUser {

            @BeforeEach
            void emptyListSetUp() {
                users = new ArrayList<>();

                given(userRepository.findAll())
                        .willReturn(users);
            }

            @Test
            @DisplayName("빈 목록을 반환한다")
            void ItReturnsEmptyList() {
                assertThat(userService.getUsers())
                        .isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("getUser 메소드는")
    class DescribeGetUser {

        @Nested
        @DisplayName("유저를 찾았을 경우")
        class ContextWithId {

            private User user;

            @BeforeEach
            void userSetUp() {
                this.user = User.builder()
                        .id(1L)
                        .name("Jack")
                        .email("jack@email.com")
                        .password("qwer1234")
                        .build();

                given(userRepository.findById(1L))
                        .willReturn(java.util.Optional.ofNullable(this.user));
            }

            @Test
            @DisplayName("유저를 반환합니다")
            void ItReturnsUser() {
                assertThat(userService.getUser(1L))
                        .isEqualTo(this.user);
            }
        }

        @Nested
        @DisplayName("유저를 찾지 못하는 경우")
        class ContextWithoutUser {

            @BeforeEach
            void notFoundSetUp() {
                given(userRepository.findById(1L))
                        .willThrow(UserNotFoundException.class);
            }

            @Test
            @DisplayName("UserNotFoundException을 던진다")
            void ItThrowsUserNotFoundException() {
                assertThatThrownBy(() -> userService.getUser(1L))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("createUser 메소드는")
    class DescribeCreateUser {

        private User user;
        private UserCreateData userCreateData;

        @Nested
        @DisplayName("유저의 정보가 주어졌을 때")
        class ContextWithUserInfo {

            @BeforeEach
            void userSetUp() {
                userCreateData = UserCreateData.builder()
                        .name("Jack")
                        .email("jack@email.com")
                        .password("qwer1234")
                        .build();
                user = User.builder()
                        .name(userCreateData.getName())
                        .email(userCreateData.getEmail())
                        .password(userCreateData.getPassword())
                        .build();
                given(userRepository.save(any(User.class)))
                        .willReturn(user);
            }

            @Test
            @DisplayName("정보를 가진 유저를 생성하고 반환합니다")
            void ItReturnsUser() {
                assertThat(userService.createUser(userCreateData))
                        .isEqualTo(user);
            }
        }
    }

    @Nested
    @DisplayName("updateUser 메소드는")
    class DescribeUpdateUser {

        @Nested
        @DisplayName("유저를 찾았을 경우")
        class ContextWithUser {

            private User user;
            private User editedUser;
            private UserUpdateData userUpdateData;

            @BeforeEach
            void userSetUp() {
                this.userUpdateData = UserUpdateData.builder()
                        .name("Wilson")
                        .password("qwer1234")
                        .build();
                this.user = User.builder()
                        .id(1L)
                        .name("Jack")
                        .email("jack@email.com")
                        .password("qwer1234")
                        .build();
                this.editedUser = User.builder()
                        .id(1L)
                        .name(this.userUpdateData.getName())
                        .email(this.userUpdateData.getEmail())
                        .password(this.userUpdateData.getPassword())
                        .build();
                given(userRepository.findById(1L))
                        .willReturn(java.util.Optional.ofNullable(this.user));
                given(userRepository.save(any(User.class)))
                        .willReturn(this.editedUser);
            }

            @Test
            @DisplayName("유저 정보를 수정하고 반환합니다")
            void ItReturnsEditedUser() {
                assertThat(userService.updateUser(1L, this.userUpdateData))
                        .isEqualTo(this.editedUser);
            }
        }

        @Nested
        @DisplayName("유저를 찾지 못한 경우")
        class ContextWithoutUser {

            @BeforeEach
            void notFoundSetUp() {
                given(userRepository.findById(1L))
                        .willThrow(UserNotFoundException.class);
            }

            @Test
            @DisplayName("UserNotFoundException을 던진다")
            void ItThrowsUserNotFoundException() {
                assertThatThrownBy(() -> userService.getUser(1L))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteUser 메소드는")
    class DescribeDeleteUser {

        @Nested
        @DisplayName("유저를 찾은 경우")
        class ContextWithUser {

            private User user;

            @BeforeEach
            void userSetUp() {
                this.user = User.builder()
                        .id(1L)
                        .name("Jack")
                        .email("jack@email.com")
                        .password("qwer1234")
                        .build();

                given(userRepository.findById(1L))
                        .willReturn(java.util.Optional.ofNullable(this.user));
            }

            @Test
            @DisplayName("유저를 삭제하고 반환합니다")
            void ItReturnsUser() {
                assertThat(userService.deleteUser(1L))
                        .isEqualTo(this.user);
            }
        }

        @Nested
        @DisplayName("유저를 찾지 못한 경우")
        class ContextWithoutUser {

            @BeforeEach
            void notFoundSetUp() {
                given(userRepository.findById(1L))
                        .willThrow(UserNotFoundException.class);
            }

            @Test
            @DisplayName("UserNotFoundException을 던진다")
            void ItThrowsUserNotFoundException() {
                assertThatThrownBy(() -> userService.getUser(1L))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
