package com.codesoom.assignment.user.controllers;

import com.codesoom.assignment.user.application.UserNotFoundException;
import com.codesoom.assignment.user.application.UserService;
import com.codesoom.assignment.user.dto.UserResponse;
import com.codesoom.assignment.user.dto.UserSaveRequestDto;
import com.codesoom.assignment.user.dto.UserUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("UserController 클래스")
class UserMockMvcControllerTest {
    private static final Long USER_ID = 1L;
    private static final Long NOT_EXIST_ID = -1L;

    private static final String USER_NAME = "test";
    private static final String USER_PASSWORD = "pass";
    private static final String USER_EMAIL = "test@test.com";

    private static final String UPDATE_NAME = "new_test";
    private static final String UPDATE_PASSWORD = "new_pass";
    private static final String UPDATE_EMAIL = "new@test.com";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Nested
    @DisplayName("GET /users는")
    class Describe_getUsers {
        @Nested
        @DisplayName("등록된 사용자가 있으면")
        class Context_with_users {
            List<UserResponse> users;

            @BeforeEach
            void setUp() {
                UserResponse responseDto = UserResponse.builder()
                        .id(USER_ID)
                        .name(USER_NAME)
                        .email(USER_EMAIL)
                        .password(USER_PASSWORD)
                        .build();

                users = Collections.singletonList(responseDto);

                given(userService.getUsers())
                        .willReturn(users);
            }

            @DisplayName("200 OK 상태와 사용자 목록을 응답한다.")
            @Test
            void It_responds_ok_with_users() throws Exception {
                mockMvc.perform(get("/users"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(objectMapper.writeValueAsString(users)));
            }
        }

        @Nested
        @DisplayName("등록된 사용자가 없으면")
        class Context_without_users {

            @DisplayName("200 OK 상태와 비어있는 사용자 목록을 응답한다.")
            @Test
            void It_responds_ok_with_empty_users() throws Exception {
                mockMvc.perform(get("/users"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("[]")));
            }
        }
    }

    @Nested
    @DisplayName("GET /users/{id} 는")
    class Describe_getUser {

        @Nested
        @DisplayName("등록된 사용자가 없으면")
        class Context_without_user {

            @BeforeEach
            void setUp() {
                given(userService.getUser(NOT_EXIST_ID))
                        .willThrow(new UserNotFoundException(NOT_EXIST_ID));
            }

            @DisplayName("404 Not Found 상태를 응답한다.")
            @Test
            void It_responds_not_found() throws Exception {
                mockMvc.perform(get("/users/{id}", NOT_EXIST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("등록된 사용자가 있으면")
        class Context_with_user {
            UserResponse responseDto;

            @BeforeEach
            void setUp() {
                responseDto = UserResponse.builder()
                        .id(USER_ID)
                        .name(USER_NAME)
                        .email(USER_EMAIL)
                        .password(USER_PASSWORD)
                        .build();
                given(userService.getUser(anyLong())).willReturn(responseDto);
            }

            @DisplayName("200 OK 상태와 찾고자 하는 사용자를 응답한다.")
            @Test
            void it_responds_ok_with_user() throws Exception {
                mockMvc.perform(get("/users/{id}", anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string(objectMapper.writeValueAsString(responseDto)))
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("name").exists())
                        .andExpect(jsonPath("email").exists());
            }
        }
    }

    @Nested
    @DisplayName("Patch /users/{id} 는")
    class Describe_updateUser {
        UserUpdateRequestDto requestDto;

        @Nested
        @DisplayName("갱신할 사용자가 없으면")
        class Context_without_user {

            @BeforeEach
            void setUp() {
                requestDto = getUpdateRequest();
                given(userService.updateUser(eq(NOT_EXIST_ID), any(UserUpdateRequestDto.class)))
                        .willThrow(new UserNotFoundException(NOT_EXIST_ID));
            }

            @DisplayName("404 Not Found 상태를 응답한다.")
            @Test
            void It_responds_not_found() throws Exception {
                mockMvc.perform(patch("/users/{id}", NOT_EXIST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto)))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("갱신 정보가 없으면")
        class Context_without_update_data {

            @BeforeEach
            void setUp() {
                requestDto = UserUpdateRequestDto.builder()
                        .build();
            }

            @DisplayName("400 Bad Request 상태를 응답한다.")
            @Test
            void It_responds_bad_request() throws Exception {
                mockMvc.perform(patch("/users/{id}", NOT_EXIST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto)))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("갱신할 사용자가 존재하면")
        class Context_with_user {

            @BeforeEach
            void setUp() {
                requestDto = getUpdateRequest();
                UserResponse responseDto = UserResponse.builder()
                        .id(USER_ID)
                        .name(USER_NAME)
                        .email(USER_EMAIL)
                        .password(USER_PASSWORD)
                        .build();
                given(userService.updateUser(anyLong(), any(UserUpdateRequestDto.class)))
                        .willReturn(responseDto);
            }

            @DisplayName("200 OK 상태와 갱신된 사용자 정보를 응답한다.")
            @Test
            void It_responds_user_id() throws Exception {
                mockMvc.perform(patch("/users/{id}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("name").exists())
                        .andExpect(jsonPath("email").exists());
            }
        }

        @Nested
        @DisplayName("잘못된 이메일이 입력되면")
        class Context_with_invalid_email {
            @BeforeEach
            void setUp() {
                requestDto = UserUpdateRequestDto.builder()
                        .name(USER_NAME)
                        .email("not_email")
                        .password(USER_PASSWORD)
                        .build();
            }

            @DisplayName("400 Bad Request 상태를 응답한다.")
            @Test
            void It_responds_bad_request() throws Exception {
                mockMvc.perform(patch("/users/{id}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto)))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("POST /users는")
    class Describe_createUser {
        UserSaveRequestDto requestDto;

        @Nested
        @DisplayName("등록할 사용자가 주어지면")
        class Context_with_user {
            @BeforeEach
            void setUp() {
                requestDto = UserSaveRequestDto.builder()
                        .name(USER_NAME)
                        .email(USER_EMAIL)
                        .password(USER_PASSWORD)
                        .build();
                UserResponse responseDto = UserResponse.builder()
                        .id(USER_ID)
                        .name(USER_NAME)
                        .email(USER_EMAIL)
                        .password(USER_PASSWORD)
                        .build();
                given(userService.createUser(any(UserSaveRequestDto.class)))
                        .willReturn(responseDto);
            }

            @DisplayName("201 Created 상태를 응답한다.")
            @Test
            void It_responds_user() throws Exception {
                mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("name").exists())
                        .andExpect(jsonPath("email").exists());
            }
        }

        @Nested
        @DisplayName("생성 정보가 없으면")
        class Context_without_save_data {
            @BeforeEach
            void setUp() {
                requestDto = UserSaveRequestDto.builder()
                        .build();
            }

            @DisplayName("400 Bad Request 상태를 응답한다.")
            @Test
            void It_responds_bad_request() throws Exception {
                mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto)))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("잘못된 이메일이 입력되면")
        class Context_with_invalid_email {
            @BeforeEach
            void setUp() {
                requestDto = UserSaveRequestDto.builder()
                        .name(USER_NAME)
                        .email("not_email")
                        .password(USER_PASSWORD)
                        .build();
            }

            @DisplayName("400 Bad Request 상태를 응답한다.")
            @Test
            void It_responds_bad_request() throws Exception {
                mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto)))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("DELETE /users/{id} 는")
    class Describe_deleteUser {

        @Nested
        @DisplayName("삭제 대상 사용자가 없으면")
        class Context_without_user {
            @BeforeEach
            void setUp() {
                given(userService.deleteUser(anyLong()))
                        .willThrow(new UserNotFoundException(NOT_EXIST_ID));
            }

            @DisplayName("404 Not Found 상태를 응답한다.")
            @Test
            void It_responds_not_found() throws Exception {
                mockMvc.perform(delete("/users/{id}", anyLong()))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("삭제 대상 사용자가 존재하면")
        class Context_with_user {

            @BeforeEach
            void setUp() {
                given(userService.deleteUser(anyLong())).willReturn(USER_ID);
            }

            @DisplayName("204 NO CONTENT 상태를 응답한다.")
            @Test
            void It_responds_no_content_with_user() throws Exception {
                mockMvc.perform(delete("/users/{id}", anyLong())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isNoContent());
            }
        }
    }

    private UserResponse getUserResponse() {
        return UserResponse.builder()
                .id(USER_ID)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();
    }

    private UserUpdateRequestDto getUpdateRequest() {
        return UserUpdateRequestDto.builder()
                .name(UPDATE_NAME)
                .email(UPDATE_EMAIL)
                .password(UPDATE_PASSWORD)
                .build();
    }
}
