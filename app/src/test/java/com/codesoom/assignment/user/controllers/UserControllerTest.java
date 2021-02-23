package com.codesoom.assignment.user.controllers;

import com.codesoom.assignment.user.application.UserNotFoundException;
import com.codesoom.assignment.user.application.UserService;
import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.dto.UserResponseDto;
import com.codesoom.assignment.user.dto.UserSaveRequestDto;
import com.codesoom.assignment.user.dto.UserUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DisplayName("UserController 클래스")
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private static final Long USER1_ID = 1L;
    private static final String USER1_NAME = "user1";
    private static final String USER1_PASSWORD = "pass1";
    private static final String USER1_EMAIL = "user1@test.com";

    private static final Long USER2_ID = 2L;
    private static final String USER2_NAME = "user2";
    private static final String USER2_PASSWORD = "pass2";
    private static final String USER2_EMAIL = "user2@test.com";

    private static final Long NOT_EXIST_ID = -1L;

    private User user1;
    private User user2;
    private List<User> users;
    UserSaveRequestDto saveRequestDto;
    UserUpdateRequestDto updateRequestDto;
    private List<UserResponseDto> usersResponses;
    private UserResponseDto userResponseDto1;
    private UserResponseDto userResponseDto2;

    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController(userService);
        setUpFixtures();
    }

    @Test
    @DisplayName("전체 사용자를 조회하고 사용자 목록을 확인한다.")
    void getUsers() {
        given(userService.getUsers()).willReturn(usersResponses);

        List<UserResponseDto> users = userController.getUsers();
        assertThat(users).isNotEmpty();
        assertThat(users).containsExactly(userResponseDto1, userResponseDto2);

        verify(userService).getUsers();
    }

    @Test
    @DisplayName("특정 사용자를 조회하고 사용자 정보를 확인한다.")
    void getUser() {
        given(userService.getUser(anyLong())).willReturn(userResponseDto1);
        UserResponseDto user = userController.getUser(USER1_ID);

        assertThat(user.getId()).isEqualTo(USER1_ID);
        assertThat(user.getEmail()).isEqualTo(USER1_EMAIL);
        assertThat(user.getPassword()).isEqualTo(USER1_PASSWORD);
        assertThat(user.getName()).isEqualTo(USER1_NAME);

        verify(userService).getUser(anyLong());
    }

    @Test
    @DisplayName("특정 사용자를 조회하고 존재하지 않으면 예외를 던진다.")
    void getNotExistedUser() {
        given(userService.getUser(NOT_EXIST_ID))
                .willThrow(new UserNotFoundException(NOT_EXIST_ID));

        assertThatThrownBy(() -> userService.getUser(NOT_EXIST_ID))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("사용자를 새로 등록할 수 있다.")
    void createUser() {
        given(userService.createUser(saveRequestDto))
                .willReturn(userResponseDto1);

        UserResponseDto actual = userController.createUser(saveRequestDto);

        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(USER1_NAME),
                () -> assertThat(actual.getPassword()).isEqualTo(USER1_PASSWORD),
                () -> assertThat(actual.getEmail()).isEqualTo(USER1_EMAIL)
        );
        verify(userService).createUser(any(UserSaveRequestDto.class));
    }

    @Test
    @DisplayName("특정 사용자 정보를 갱신할 수 있다.")
    void updateUser() {
        given(userService.updateUser(USER1_ID, updateRequestDto))
                .willReturn(userResponseDto2);

        UserResponseDto actual = userController.updateUser(USER1_ID, updateRequestDto);

        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(USER2_NAME),
                () -> assertThat(actual.getPassword()).isEqualTo(USER2_PASSWORD),
                () -> assertThat(actual.getEmail()).isEqualTo(USER2_EMAIL)
        );
        verify(userService).updateUser(anyLong(), any(UserUpdateRequestDto.class));
    }

    void setUpFixtures() {
        user1 = User.builder()
                .id(USER1_ID)
                .name(USER1_NAME)
                .email(USER1_EMAIL)
                .password(USER1_PASSWORD)
                .build();

        user2 = User.builder()
                .id(USER2_ID)
                .name(USER2_NAME)
                .email(USER2_EMAIL)
                .password(USER2_PASSWORD)
                .build();

        saveRequestDto = UserSaveRequestDto.builder()
                .email(USER1_EMAIL)
                .name(USER1_NAME)
                .password(USER1_PASSWORD)
                .build();

        updateRequestDto = UserUpdateRequestDto.builder()
                .email(USER2_EMAIL)
                .name(USER2_NAME)
                .password(USER2_PASSWORD)
                .build();

        users = Arrays.asList(user1, user2);
        userResponseDto1 = UserResponseDto.of(user1);
        userResponseDto2 = UserResponseDto.of(user2);
        usersResponses = Arrays.asList(userResponseDto1, userResponseDto2);
    }
}
