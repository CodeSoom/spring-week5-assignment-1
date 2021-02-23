package com.codesoom.assignment.user.controllers;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.user.application.UserService;
import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.dto.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    private User user1;
    private User user2;
    private List<User> users;
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

        given(userService.getUsers()).willReturn(usersResponses);
    }

    @Test
    @DisplayName("전체 사용자를 조회하고 사용자 목록을 확인한다.")
    void getUsers() {
        List<UserResponseDto> users = userController.getUsers();
        assertThat(users).isNotEmpty();
        assertThat(users).containsExactly(userResponseDto1, userResponseDto2);

        verify(userService).getUsers();
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

        users = Arrays.asList(user1, user2);
        userResponseDto1 = UserResponseDto.of(user1);
        userResponseDto2 = UserResponseDto.of(user2);
        usersResponses = Arrays.asList(userResponseDto1, userResponseDto2);
    }
}
