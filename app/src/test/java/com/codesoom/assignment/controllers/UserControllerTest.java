package com.codesoom.assignment.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static com.codesoom.assignment.constants.UserConstants.ID;
import static com.codesoom.assignment.constants.UserConstants.USER;
import static com.codesoom.assignment.constants.UserConstants.USER_DATA;

import com.codesoom.assignment.NotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

@Nested
@DisplayName("UserController 클래스")
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private UserService verifyService(final int invokeCounts) {
        return verify(userService, times(invokeCounts));
    }

    @Nested
    @DisplayName("create 메서드는")
    public class Describe_create {
        private User subject() {
            return userController.create(USER_DATA);
        }

        private OngoingStubbing<User> mockCreateUser() {
            return when(userService.createUser(any(UserData.class)));
        }

        @Nested
        @DisplayName("User 생성에 성공한 경우")
        public class Context_service_creates_a_user {
            @BeforeEach
            private void beforeEach() {
                mockCreateUser()
                    .thenReturn(USER);
            }

            @AfterEach
            private void afterEach() {
                verifyService(1)
                    .createUser(any(UserData.class));
            }

            @Test
            @DisplayName("생성한 User를 리턴한다.")
            public void it_returns_a_created_user() {
                assertThat(subject())
                    .isInstanceOf(User.class);
            }
        }
    }

    @Nested
    @DisplayName("destroy 메서드는")
    public class Describe_destroy {
        private void subject() {
            userController.destroy(ID);
        }

        @AfterEach
        private void afterEach() {
            verifyService(1)
                .deleteUser(anyLong());
        }

        @Nested
        @DisplayName("삭제할 User를 찾지 못한 경우")
        public class Context_service_throws_an_exception {
            @BeforeEach
            private void beforeEach() {
                doThrow(new NotFoundException(ID, User.class.getSimpleName()))
                    .when(userService).deleteUser(anyLong());
            }

            @Test
            @DisplayName("NotFoundException을 던진다.")
            public void it_does_not_catch_exceptions() {
                assertThatThrownBy(() -> subject())
                    .isInstanceOf(NotFoundException.class);
            }
        }

        @Test
        @DisplayName("User를 삭제한다.")
        public void it_deletes_a_user() {
            subject();
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    public class Describe_update {
        private User subject() {
            return userController.update(ID, USER_DATA);
        }

        private OngoingStubbing<User> mockUpdateUser() {
            return when(userService.updateUser(anyLong(), any(UserData.class)));
        }

        @Nested
        @DisplayName("수정할 User를 찾지 못한 경우")
        public class Context_service_throws_an_exception {
            @BeforeEach
            private void beforeEach() {
                mockUpdateUser()
                    .thenThrow(new NotFoundException(ID, User.class.getSimpleName()));
            }

            @AfterEach
            private void afterEach() {
                verifyService(1)
                    .updateUser(anyLong(), any(UserData.class));
            }

            @Test
            @DisplayName("NotFoundExceptio을 던진다.")
            public void it_does_not_catch_exceptions() {
                assertThatThrownBy(() -> subject())
                    .isInstanceOf(NotFoundException.class);
            }
        }

        @Nested
        @DisplayName("User 데이터 수정에 성공하면")
        public class Context_service_update_a_user {
            @BeforeEach
            private void beforeEach() {
                mockUpdateUser()
                    .thenReturn(USER);
            }

            @AfterEach
            private void afterEach() {
                verifyService(1)
                    .updateUser(anyLong(), any(UserData.class));
            }

            @Test
            @DisplayName("수정한 User를 리턴한다.")
            public void it_returns_a_updated_user() {
                assertThat(subject())
                    .isInstanceOf(User.class);
            }
        }
    }
}
