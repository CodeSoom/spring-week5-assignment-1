package com.codesoom.assignment.application;

<<<<<<< HEAD
import com.codesoom.assignment.exception.UserNotFoundException;
=======
import com.codesoom.assignment.UserNotFoundException;
>>>>>>> 22c8bd2609d69f3ecd808fca4d6127023b3f2971
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository;
    private String userNotFoundMessage = "User Not Found Id : ";

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    UserData setUserData(Long id) {
        return UserData.builder()
                .id(id)
                .name("test" + id)
                .email("test" + id + "@test.com")
                .password("testPw" + id)
                .build();
    }

    Optional<User> setUserByUserData(UserData userData) {
        return Optional.of(User.builder()
                .id(userData.getId())
                .name(userData.getName())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .build());
    }

    @Nested
    @DisplayName("createUser 메서드는")
    class Describe_CreateUser {
        @Nested
<<<<<<< HEAD
        @DisplayName("유저 정보에 대한 모든 정보가 입력 된다면")
=======
        @DisplayName("모든 값이 입력 된다면")
>>>>>>> 22c8bd2609d69f3ecd808fca4d6127023b3f2971
        class Context_Valid_Create_User {
            private Long id = 1L;
            private UserData userData;

            @BeforeEach
            void setUpValidCreateUser() {
                userData = setUserData(id);
                given(userRepository.save(any(User.class)))
                        .will(invocation -> {
                            return invocation.getArgument(0);
                        });
            }

            @Test
            @DisplayName("저장한 유저 정보를 반환한다.")
            void validCreateUser() {
                UserData savedUser = userService.createUser(userData);
                assertAll(
                        () -> {
                            Assertions.assertThat(savedUser)
                                    .extracting("email")
                                    .isEqualTo(userData.getEmail());
                        },
                        () -> {
                            Assertions.assertThat(savedUser)
                                    .extracting("name")
                                    .isEqualTo(userData.getName());
                        },
                        () -> {
                            Assertions.assertThat(savedUser.getPassword())
                                    .isEqualTo(userData.getPassword());
                        }
                );
                then(userRepository).should(times(1))
                        .save(any(User.class));
            }
        }
    }

    @Nested
    @DisplayName("updateUser 메소드는")
    class Describe_Update_User {
        @Nested
<<<<<<< HEAD
        @DisplayName("요청한 아이디로 유저를 찾을 수 있다면")
=======
        @DisplayName("목록에 유저가 있다면")
>>>>>>> 22c8bd2609d69f3ecd808fca4d6127023b3f2971
        class Context_Valid_Update_User {
            private Long id = 1L;
            private UserData userData;
            private Optional<User> foundUser;

            @BeforeEach
            void setUpContextValidUpdateUser() {
                userData = setUserData(id);
                foundUser = setUserByUserData(userData);
                given(userRepository.findUserById(eq(id))).willReturn(foundUser);
                given(userRepository.save(any(User.class)))
                        .willReturn(foundUser.get());
            }

            @Test
            @DisplayName("유저 정보를 수정한다.")
            void valid_update_user() {
                UserData updateUserData = userService.updateUser(id, userData);
                assertAll(
                        () -> {
                            Assertions.assertThat(updateUserData)
                                    .extracting("id")
                                    .isEqualTo(id);
                        },
                        () -> {
                            Assertions.assertThat(updateUserData)
                                    .extracting("email")
                                    .isEqualTo(userData.getEmail());
                        },
                        () -> {
                            Assertions.assertThat(updateUserData)
                                    .extracting("name")
                                    .isEqualTo(userData.getName());
                        },
                        () -> {
                            Assertions.assertThat(updateUserData)
                                    .extracting("password")
                                    .isEqualTo(userData.getPassword());
                        }
                );
                then(userRepository).should(times(1))
                        .findUserById(eq(id));
                then(userRepository).should(times(1))
                        .save(any(User.class));
            }
        }

        @Nested
<<<<<<< HEAD
        @DisplayName("요청한 아이디로 유저를 찾을 수 없다면")
=======
        @DisplayName("목록에 유저가 없다면")
>>>>>>> 22c8bd2609d69f3ecd808fca4d6127023b3f2971
        class Context_Invalid_Update_User {
            private Long id = 100L;
            private UserData userData;
            private String errorMessage = userNotFoundMessage + id;

            @BeforeEach
            void setUpInvalidUpdateUser() {
                userData = setUserData(id);
                given(userRepository.findUserById(eq(id)))
                        .willThrow(new UserNotFoundException(id));

            }

            @Test
<<<<<<< HEAD
            @DisplayName("UserNotFoundException을 던진다.")
=======
            @DisplayName("UserNotFoundException이 발생한다.")
>>>>>>> 22c8bd2609d69f3ecd808fca4d6127023b3f2971
            void invalid_update_user_exception() {
                Assertions.assertThatThrownBy(
                        () -> {
                            userService.updateUser(id, userData);
                        }
                ).isInstanceOf(UserNotFoundException.class);
            }

            @Test
            @DisplayName("특정 에러 메시지를 반환한다.")
            void invalid_update_user_exception_message() {
                Throwable throwable = catchThrowable(
                        () -> userService.updateUser(id, userData));
                Assertions.assertThat(throwable.getMessage())
                        .isEqualTo(errorMessage);
            }
        }
    }

    @Nested
    @DisplayName("deleteUser 메소드는")
    class Describe_Delete_User {
        @Nested
<<<<<<< HEAD
        @DisplayName("요청한 회원 아이디로 유저를 찾을 수 있다면")
=======
        @DisplayName("목록에 유저가 있다면")
>>>>>>> 22c8bd2609d69f3ecd808fca4d6127023b3f2971
        class Context_Valid_Delete_User {
            private Long id = 1L;
            private UserData userData;
            private Optional<User> foundUser;

            @BeforeEach
            void setUpValidDeleteUser() {
                userData = setUserData(id);
                foundUser = setUserByUserData(userData);
                given(userRepository.findUserById(eq(id))).willReturn(foundUser);
                doNothing().when(userRepository).deleteUserById(eq(id));
            }

            @Test
            @DisplayName("유저를 삭제한다")
            void valid_delete_user() {
                userService.deleteUser(id);
                then(userRepository).should(times(1))
                        .findUserById(eq(id));
                then(userRepository).should(times(1))
                        .deleteUserById(eq(id));
            }
        }

        @Nested
<<<<<<< HEAD
        @DisplayName("요청한 회원 아이디로 유저를 찾을 수 없다면")
=======
        @DisplayName("목록에 유자가 없다면")
>>>>>>> 22c8bd2609d69f3ecd808fca4d6127023b3f2971
        class Conetxt_Invalid_Delete_User {
            private Long id = 100L;
            private UserData userData;
            private String errorMessage = userNotFoundMessage + id;

            @BeforeEach
            void setUpInvalidDeleteUser() {
                userData = setUserData(id);
                given(userRepository.findUserById(eq(id)))
                        .willThrow(new UserNotFoundException(id));
            }

            @Test
<<<<<<< HEAD
            @DisplayName("UserNotFoundException을 던진다.")
=======
            @DisplayName("UserNotFoundException을 발생한다.")
>>>>>>> 22c8bd2609d69f3ecd808fca4d6127023b3f2971
            void invalid_delete_user_exception() {
                Throwable throwable = catchThrowable(
                        () -> userService.deleteUser(id));
                throwable.getClass()
                        .isAssignableFrom(UserNotFoundException.class);
            }

            @Test
            @DisplayName("특정 에러 메시지를 반환한다.")
            void invalid_delete_user_exception_message(){
                Throwable throwable = catchThrowable(
                        () -> userService.deleteUser(id));
                Assertions.assertThat(throwable.getMessage())
                        .isEqualTo(errorMessage);
            }
        }

    }

}
