package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserDto;
import com.codesoom.assignment.exception.NotFoundUserException;
import com.github.dozermapper.core.MappingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserController 클래스")
class UserControllerTest {

    private UserService userService;
    private UserController userController;

    final private Long EXISTENT_ID = 1L;
    final private Long NON_EXISTENT_ID = -1L;
    private UserDto sampleUserDto;
    private UserDto updateUserDto;

    private UserDto blankNameUserDto;
    private UserDto blankEmailUserDto;
    private UserDto blankPasswordUserDto;

    private User sampleUser;
    private User updatedUser;

    @BeforeEach
    void setup() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
        setFixture();
    }

    void setFixture() {
        sampleUserDto = UserDto.builder()
                .id(EXISTENT_ID)
                .name("name1")
                .email("email1")
                .password("password1")
                .build();
        sampleUser = User.builder()
                .id(sampleUserDto.getId())
                .name(sampleUserDto.getName())
                .email(sampleUserDto.getEmail())
                .password(sampleUserDto.getPassword())
                .build();
        updateUserDto = UserDto.builder()
                .name("updatedName")
                .email("updatedEmail")
                .password("updatedPassword")
                .build();
        blankNameUserDto = UserDto.builder()
                .id(sampleUserDto.getId())
                .email(sampleUserDto.getEmail())
                .password(sampleUserDto.getPassword())
                .build();
        blankEmailUserDto = UserDto.builder()
                .id(sampleUserDto.getId())
                .name(sampleUserDto.getName())
                .password(sampleUserDto.getPassword())
                .build();
        blankPasswordUserDto = UserDto.builder()
                .id(sampleUserDto.getId())
                .name(sampleUserDto.getName())
                .password(sampleUserDto.getPassword())
                .build();
        updatedUser = User.builder()
                .id(updateUserDto.getId())
                .name(updateUserDto.getName())
                .email(updateUserDto.getEmail())
                .password(updateUserDto.getPassword())
                .build();

        given(userService.createUser(sampleUserDto))
                .willReturn(sampleUser);
        given(userService.createUser(null))
                .willThrow(MappingException.class);
        given(userService.updateUser(EXISTENT_ID, updateUserDto))
                .willReturn(updatedUser);
        given(userService.updateUser(EXISTENT_ID, null))
                .willThrow(MappingException.class);

        willThrow(NotFoundUserException.class)
                .given(userService).updateUser(eq(NON_EXISTENT_ID), any(UserDto.class));
        willThrow(NotFoundUserException.class)
                .given(userService).deleteUser(NON_EXISTENT_ID);
    }

    @Nested
    @DisplayName("create 메소드는")
    class Describe_of_create {

        @Nested
        @DisplayName("유저 정보가 주어지면")
        class Context_of_valid_user_dto {

            @Test
            @DisplayName("유저를 생성하고, 생성한 유저를 반환한다")
            void it_creates_and_returns_user() {
                User user = userController.create(sampleUserDto);

                assertThat(user.getName()).isEqualTo(sampleUserDto.getName());
                verify(userService).createUser(any(UserDto.class));
            }
        }

        @Nested
        @DisplayName("null이 주어지면")
        class Context_of_null {

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> userController.create(null))
                        .isInstanceOf(MappingException.class);
            }
        }
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_of_update {

        @Nested
        @DisplayName("존재하는 유저 id와 유효한 유저 정보가 주어지면")
        class Context_of_valid_user_dto {

            private Long givenId;
            private UserDto givenUserDto;

            @BeforeEach
            void setup() {
                this.givenId = EXISTENT_ID;
                this.givenUserDto = updateUserDto;
            }

            @Test
            @DisplayName("유저를 갱신하고, 갱신한 유저를 반환한다")
            void it_updates_and_returns_user() {
                User user = userController.update(givenId, givenUserDto);

                assertThat(user.getName()).isEqualTo(givenUserDto.getName());
                verify(userService).updateUser(eq(givenId), any(UserDto.class));
            }
        }

        @DisplayName("유저 정보로 null이 주어지면")
        class Context_of_null {

            private Long givenId;
            private UserDto givenUserDto;

            @BeforeEach
            void setup() {
                this.givenId = EXISTENT_ID;
                this.givenUserDto = null;
            }

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> userController.update(givenId, givenUserDto))
                        .isInstanceOf(MappingException.class);
            }
        }
    }

    @Nested
    @DisplayName("destroy 메소드는")
    class Describe_of_destroy {

        @Nested
        @DisplayName("존재하는 유저 id가 주어지면")
        class Context_of_existent_id {

            private Long givenId;

            @BeforeEach
            void setup() {
                this.givenId = EXISTENT_ID;
            }

            @Test
            @DisplayName("유저를 삭제한다")
            void it_deletes_user() {
                userController.destroy(givenId);

                verify(userService).deleteUser(givenId);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 유저의 id가 주어지면")
        class Context_of_non_existent_id {

            private Long givenId;

            @BeforeEach
            void setup() {
                this.givenId = NON_EXISTENT_ID;
            }

            @Test
            @DisplayName("유저를 찾을 수 없다는 예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> userController.destroy(givenId))
                        .isInstanceOf(NotFoundUserException.class);
            }
        }
    }
}
