package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Nested
@DisplayName("UserController 클래스의")
class UserControllerTest {

    private UserController userController;

    private final UserService userService = mock(UserService.class);

    @BeforeEach
    void controllerSetUp() {
        this.userController = new UserController(this.userService);
    }

    @Nested
    @DisplayName("list 메소드는")
    class DescribeList {

        private List<User> users;

        @Nested
        @DisplayName("유저가 존재할 때")
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

                given(userService.getUsers())
                        .willReturn(users);
            }

            @Test
            @DisplayName("유저 목록을 반환합니다")
            void ItReturnsUserList() {
                assertThat(userController.list()).isEqualTo(users);
            }
        }

        @Nested
        @DisplayName("유저가 존재하지 않을 때")
        class ContextWithoutUser {

            @BeforeEach
            void userListSetUp() {
                users = new ArrayList<>();
                given(userService.getUsers())
                        .willReturn(users);
            }

            @Test
            @DisplayName("빈 목록을 반환합니다")
            void ItReturnsEmptyList() {
                assertThat(userController.list()).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("detail 메소드는")
    class DescribeDetail {

        @Nested
        @DisplayName("식별자를 가진 유저가 있을 때")
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

                given(userService.getUser(1L))
                        .willReturn(this.user);
            }

            @Test
            @DisplayName("유저를 반환합니다")
            void ItReturnsUser() {
                assertThat(userController.detail(1L))
                        .isEqualTo(this.user);
            }
        }

        @Nested
        @DisplayName("식별자를 가진 유저가 존재하지 않을 때")
        class ContextWithoutUser {

            @BeforeEach
            void userSetUp() {
                given(userService.getUser(1L))
                        .willThrow(UserNotFoundException.class);
            }

            @Test
            @DisplayName("UserNotFoundException을 던진다")
            void ItThrowsUserNotFoundException() {
                assertThatThrownBy(() -> userController.detail(1L))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("create 메소드는")
    class DescribeCreate {

        private UserCreateData userCreateData;
        private User user;

        @BeforeEach
        void userCreateDataSetUp() {
            this.userCreateData = UserCreateData.builder()
                    .name("Jack")
                    .email("jack@email.com")
                    .password("qwer1234")
                    .build();
            this.user = User.builder()
                    .name(this.userCreateData.getName())
                    .email(this.userCreateData.getEmail())
                    .password(this.userCreateData.getPassword())
                    .build();
            given(userService.createUser(any(UserCreateData.class)))
                    .willReturn(this.user);
        }

        @Test
        @DisplayName("유저를 생성하고 반환합니다")
        void ItReturnsUser() {
            assertThat(userController.create(this.userCreateData))
                    .isEqualTo(this.user);
        }
    }

    @Nested
    @DisplayName("update 메소드는")
    class DescribeUpdate {

        private UserUpdateData userUpdateData;

        @Nested
        @DisplayName("식별자를 가진 유저가 있으면")
        class ContextWithUser {

            private User editedUser;

            @BeforeEach
            void userSetUp() {
                userUpdateData = UserUpdateData.builder()
                        .name("Wilson")
                        .password("qwer1234")
                        .build();
                this.editedUser = User.builder()
                        .id(1L)
                        .name(userUpdateData.getName())
                        .email(userUpdateData.getEmail())
                        .password(userUpdateData.getPassword())
                        .build();
                given(userService.updateUser(eq(1L), any(UserUpdateData.class)))
                        .willReturn(this.editedUser);
            }

            @Test
            @DisplayName("유저 정보를 수정하고 반환한다")
            void ItReturnsEditedUser() {
                assertThat(userController.update(1L, userUpdateData))
                        .isEqualTo(this.editedUser);
            }
        }

        @Nested
        @DisplayName("식별자를 가진 유저가 없으면")
        class ContextWithoutUser {

            @BeforeEach
            void notFoundSetUp() {
                userUpdateData = UserUpdateData.builder()
                        .name("Wilson")
                        .password("qwer1234")
                        .build();
                given(userService.updateUser(eq(1L), any(UserUpdateData.class)))
                        .willThrow(UserNotFoundException.class);
            }

            @Test
            @DisplayName("UserNotFoundException을 던진다")
            void ItThrowsUserNotFoundException() {
                assertThatThrownBy(() -> userController.update(1L, userUpdateData))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("destroy 메소드는")
    class DescribeDestroy {

        @Nested
        @DisplayName("식별자를 가진 유저가 있으면")
        class ContextWithUser {

            @BeforeEach
            void userSetUp() {
                User user = User.builder()
                        .id(1L)
                        .name("Jack")
                        .email("jack@email.com")
                        .password("qwer1234")
                        .build();

                given(userService.deleteUser(1L))
                        .willReturn(user);
            }

            @Test
            @DisplayName("유저를 삭제한다")
            void ItDeletesUser() {
                userController.destroy(1L);
                verify(userService).deleteUser(1L);
            }
        }

        @Nested
        @DisplayName("식별자를 가진 유저가 없으면")
        class ContextWithoutUser {

            @BeforeEach
            void notFoundSetUp() {
                given(userService.deleteUser(1L))
                        .willThrow(UserNotFoundException.class);
            }

            @Test
            @DisplayName("UserNotFoundException을 던진다")
            void ItThrowsUserNotFoundException() {
                assertThatThrownBy(() -> userController.destroy(1L))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
