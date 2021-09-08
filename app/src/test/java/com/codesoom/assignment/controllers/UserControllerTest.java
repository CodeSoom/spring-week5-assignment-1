package com.codesoom.assignment.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.CreateUserDto;
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
