package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private final Long EXISTED_ID = 1L;
    private final Long NOT_EXISTED_ID = -1L;
    private final int INDEX_OF_USERDATA = 1;
    private final String NAME = "name";
    private final String EMAIL = "user@email.com";
    private final String PASSWORD = "password";
    private final String UPDATE_PREFIX = "update";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    @MockBean
    private UserService userService;

    @Nested
    @DisplayName("POST /users 는")
    class DescribeSignUpUser {

        @Nested
        @DisplayName("사용자 등록 요청이 들어오면")
        class ContextWithUserData {

            User user = User.builder()
                            .name(NAME)
                            .email(EMAIL)
                            .password(PASSWORD)
                            .build();

            @BeforeEach
            void prepare() {
                when(userService.signUp(any(UserData.class))).thenReturn(user);
            }

            @Test
            @DisplayName("HttpStatus 201 Created 를 응답한다")
            void itReturnsHttpStatusCreated() throws Exception {
                String content = objectMapper.writeValueAsString(user);
                mockMvc.perform(post("/users")
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(content))
                       .andExpect(status().isCreated())
                       .andExpect(content().string(containsString(NAME)))
                       .andExpect(content().string(containsString(EMAIL)))
                       .andExpect(content().string(containsString(PASSWORD)));
                verify(userService).signUp(any(UserData.class));
            }
        }

        @Nested
        @DisplayName("RequestBody 가 null 인 요청이 들어오면")
        class ContextWithRequestBodyIsNull {

            @Test
            @DisplayName("HttpStatus 4xx Client Error 를 응답한다")
            void itReturnsHttpStatusClientError() throws Exception {
                mockMvc.perform(post("/users"))
                       .andExpect(status().is4xxClientError());
            }
        }
    }

    @Nested
    @DisplayName("PATCH /users/{id} 는")
    class DescribeUpdateUser {

        @Nested
        @DisplayName("만약 존재하는 사용자 id 와 사용자 정보로 요청이 들어오면")
        class ContextWithExistedIdAndUserData {

            UserData userData = UserData.builder()
                                        .name(UPDATE_PREFIX + NAME)
                                        .email(UPDATE_PREFIX + EMAIL)
                                        .password(UPDATE_PREFIX + PASSWORD)
                                        .build();

            @BeforeEach
            void prepareUser() {
                User existedUser = User.builder()
                                       .name(NAME)
                                       .email(EMAIL)
                                       .password(PASSWORD)
                                       .build();

                when(userService.updateUser(eq(EXISTED_ID), any(UserData.class))).then(invocation -> {
                    UserData userData = invocation.getArgument(INDEX_OF_USERDATA);
                    existedUser.modify(mapper, userData);
                    return existedUser;
                });
            }

            @Test
            @DisplayName("HttpStatus 200 OK 를 응답한다")
            void itReturnsHttpStatusOK() throws Exception {
                String content = objectMapper.writeValueAsString(userData);
                mockMvc.perform(patch("/users/" + EXISTED_ID)
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(content))
                       .andExpect(status().isOk())
                       .andExpect(content().string(containsString(UPDATE_PREFIX + NAME)))
                       .andExpect(content().string(containsString(UPDATE_PREFIX + EMAIL)))
                       .andExpect(content().string(containsString(UPDATE_PREFIX + PASSWORD)));
                verify(userService).updateUser(eq(EXISTED_ID), any(UserData.class));
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 사용자 id 로 요청이 들어오면")
        class ContextWithNotExistedId {

            UserData userData = UserData.builder()
                                        .name(NAME)
                                        .email(EMAIL)
                                        .password(PASSWORD)
                                        .build();

            @BeforeEach
            void prepareUser() {
                when(userService.updateUser(eq(NOT_EXISTED_ID), any(UserData.class))).thenThrow(new UserNotFoundException(NOT_EXISTED_ID));
            }

            @Test
            @DisplayName("HttpStatus 404 Not Found 를 응답한다")
            void itReturnsHttpStatusNotFound() throws Exception {
                String content = objectMapper.writeValueAsString(userData);
                mockMvc.perform(patch("/users/" + NOT_EXISTED_ID)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(content))
                       .andExpect(status().isNotFound());
                verify(userService).updateUser(eq(NOT_EXISTED_ID), any(UserData.class));
            }
        }
    }

    @Nested
    @DisplayName("DELETE /users/{id} 는")
    class DescribeDeleteUser {

        @Nested
        @DisplayName("만약 존재하는 사용자 id 로 요청이 들어오면")
        class ContextWithExistedId {

            @BeforeEach
            void prepare() {
                doNothing().when(userService).deleteUser(EXISTED_ID);
            }

            @Test
            @DisplayName("HttpStatus 204 No Content 를 응답한다")
            void itReturnsHttpStatusNoContent() throws Exception {
                mockMvc.perform(delete("/users/" + EXISTED_ID))
                       .andExpect(status().isNoContent());
                verify(userService).deleteUser(EXISTED_ID);
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 사용자 id 로 요청이 들어오면")
        class ContextWithNotExistedId {

            @BeforeEach
            void prepare() {
                doThrow(new UserNotFoundException(NOT_EXISTED_ID)).when(userService).deleteUser(NOT_EXISTED_ID);
            }

            @Test
            @DisplayName("HttpStatus 404 Not Found 를 응답한다")
            void itReturnsHttpStatusNotFound() throws Exception {
                mockMvc.perform(delete("/users/" + NOT_EXISTED_ID))
                       .andExpect(status().isNotFound());
                verify(userService).deleteUser(NOT_EXISTED_ID);
            }
        }
    }
}
