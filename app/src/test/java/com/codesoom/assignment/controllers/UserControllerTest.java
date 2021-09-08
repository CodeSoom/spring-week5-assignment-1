package com.codesoom.assignment.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.CreateUserDto;
import com.codesoom.assignment.dto.UpdateUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        given(userService.createUser(any(User.class)))
            .will(invocation -> invocation.getArgument(0));

        given(userService.updateUser(eq(1L), any(User.class)))
            .will(invocation -> invocation.getArgument(1));

        given(userService.updateUser(eq(9999L), any(User.class)))
            .willThrow(new UserNotFoundException(9999L));
    }

    @Nested
    @DisplayName("POST /users 요청은")
    class Describe_postUsers {

        @Nested
        @DisplayName("유효한 유저 생성 DTO가 주어질 때")
        class Context_validUser {

            private CreateUserDto validCreateUserDto;

            @BeforeEach
            void setUp() {
                validCreateUserDto = CreateUserDto.builder()
                    .name("name")
                    .email("email")
                    .password("password")
                    .build();
            }

            @Test
            @DisplayName("생성한 유저를 리턴하고 201을 응답한다")
            void it_returns_created_user_and_response_201() throws Exception {
                mockMvc.perform(
                    post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(validCreateUserDto))
                )
                    .andExpect(status().isCreated())
                    .andExpect(content().json(toJson(validCreateUserDto.toEntity())));

                verify(userService).createUser(any(User.class));
            }
        }

        @Nested
        @DisplayName("유효하지 않은 유저 생성 DTO가 주어질 때")
        class Context_invalidCreateUserDto {

            private CreateUserDto invalidCreateUserDto;

            @BeforeEach
            void setUp() {
                invalidCreateUserDto = CreateUserDto.builder()
                    .name("name")
                    .email("email")
                    .build();
            }

            @Test
            @DisplayName("에러를 던지고 400을 응답한다")
            void it_throws_and_response_400() throws Exception {
                mockMvc.perform(
                    post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonWithoutPassword(invalidCreateUserDto))
                )
                    .andExpect(status().isBadRequest());

                verify(userService, never()).createUser(invalidCreateUserDto.toEntity());
            }
        }
    }

    @Nested
    @DisplayName("POST /users/{id} 요청은")
    class Describe_postUsersWithId {

        @Nested
        @DisplayName("유효한 유저 업데이트 DTO가 주어진다면")
        class Context_validUpdateUserDto {

            private UpdateUserDto validUpdateUserDto;

            @BeforeEach
            void setUp() {
                validUpdateUserDto = UpdateUserDto.builder()
                    .name("name")
                    .email("email")
                    .password("password")
                    .build();
            }

            @Nested
            @DisplayName("id가 존재한다면")
            class Context_idExist {

                private Long existId;

                @BeforeEach
                void setUp() {
                    existId = 1L;
                }

                @Test
                @DisplayName("수정된 유저를 리턴하고 200을 응답한다")
                void it_returns_updated_user_and_response_200() throws Exception {
                    mockMvc.perform(
                        post("/users/" + existId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(validUpdateUserDto))
                    )
                        .andExpect(status().isOk())
                        .andExpect(content().json(toJson(validUpdateUserDto.toEntity())));

                    verify(userService).updateUser(eq(existId), any(User.class));
                }
            }

            @Nested
            @DisplayName("id가 존재하지 않는다면")
            class Context_idNotExist {

                private Long notExistId;

                @BeforeEach
                void setUp() {
                    notExistId = 9999L;
                }

                @Test
                @DisplayName("404를 응답한다")
                void it_response_404() throws Exception {
                    mockMvc.perform(
                        post("/users/" + notExistId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(validUpdateUserDto))
                    )
                        .andExpect(status().isNotFound());

                    verify(userService).updateUser(eq(notExistId), any(User.class));
                }
            }
        }
    }

    private String toJson(UpdateUserDto updateUserDto) {
        return String.format(
            "{\"name\": \"%s\","
                + " \"email\": \"%s\","
                + " \"password\": \"%s\"}",
            updateUserDto.getName(),
            updateUserDto.getEmail(),
            updateUserDto.getPassword());
    }

    private String toJson(User user) {
        return String.format(
            "{\"name\": \"%s\","
                + " \"email\": \"%s\","
                + " \"password\": \"%s\"}",
            user.getName(),
            user.getEmail(),
            user.getPassword());
    }

    private String toJson(CreateUserDto createUserDto) {
        return String.format(
            "{\"name\": \"%s\","
                + " \"email\": \"%s\","
                + " \"password\": \"%s\"}",
            createUserDto.getName(),
            createUserDto.getEmail(),
            createUserDto.getPassword());
    }

    private String toJsonWithoutPassword(CreateUserDto createUserDto) {
        return String.format(
            "{\"name\": \"%s\","
                + " \"email\": \"%s\"}",
            createUserDto.getName(),
            createUserDto.getEmail());
    }
}
