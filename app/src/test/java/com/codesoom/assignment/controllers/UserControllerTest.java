package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@DisplayName("UserController 클래스")
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private UserRepository userRepository;

    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    private final Long USER_ID = 1L;
    private final String USER_NAME = "양홍석";
    private final String USER_EMAIL = "davidyang2149@gmail.com";
    private final String USER_PASSWORD = "YouHaveNoIdea";

    private final String NEW_USER_NAME = "제임스 고슬링";
    private final String NEW_USER_EMAIL = "JamesGosling@gmail.com";
    private final String NEW_USER_PASSWORD = "IamOrigin";

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(USER_ID)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();
    }

    @Nested
    @DisplayName("create 메소드는")
    class Describe_create {

        @Nested
        @DisplayName("회원 등록에 필요한 정보가 주어지면")
        class Context_valid {
            private UserData userData;

            @BeforeEach
            void setUp() {
                userData = UserData.builder()
                    .name(USER_NAME)
                    .email(USER_EMAIL)
                    .password(USER_PASSWORD)
                    .build();
            }

            @Test
            @DisplayName("회원을 등록하고 리턴한다")
            void It_save_and_return_user() {
                userController.create(userData);

                verify(userService).createUser(any(UserData.class));
            }
        }
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {

        @Nested
        @DisplayName("회원 수정에 필요한 정보가 주어지면")
        class Context_valid {
            private UserData userData;

            @BeforeEach
            void setUp() {
                userData = UserData.builder()
                        .name(NEW_USER_NAME)
                        .email(NEW_USER_EMAIL)
                        .password(NEW_USER_PASSWORD)
                        .build();
            }

            @Test
            @DisplayName("회원 정보를 수정하고 리턴한다")
            void It_update_and_return_user() {
                userController.update(USER_ID, userData);

                verify(userService).updateUser(eq(USER_ID), any(UserData.class));
            }
        }
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {

        @Nested
        @DisplayName("회원 삭제에 필요한 정보가 주어지면")
        class Context_valid {
            private final Long TARGET_USER_ID = 1L;

            @Test
            @DisplayName("회원 정보를 삭제한다")
            void It_delete_user() {
                userController.delete(TARGET_USER_ID);

                verify(userService).deleteUser(eq(TARGET_USER_ID));
            }
        }
    }
}
