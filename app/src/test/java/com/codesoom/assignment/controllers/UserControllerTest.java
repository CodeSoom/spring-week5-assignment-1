package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserRequest;
import com.codesoom.assignment.dto.UserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("UserController 클래스")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRequest userRequest;
    private UserResponse userResponse;

    private static final Long USER_ID = 1L;
    private static final String NAME = "Min";
    private static final String NEW_NAME = "New Min";
    private static final String EMAIL = "min@gmail.com";
    private static final String NEW_EMAIL = "new_min@gmail.com";
    private static final String PASSWORD = "1q2w3e!";
    private static final String NEW_PASSWORD = "new_1q2w3e!";

    @BeforeEach
    void setUp() {
        userRequest = UserRequest.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        userResponse = UserResponse.builder()
                .id(USER_ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Nested
    @DisplayName("POST /users 요청은")
    class Describe_POST_user {
        @Nested
        @DisplayName("생성할 사용자 정보가 주어지면")
        class Context_with_user {
            @BeforeEach
            void setUp() {
                UserResponse userResponse = UserResponse.builder()
                        .id(USER_ID)
                        .name(NAME)
                        .email(EMAIL)
                        .password(PASSWORD)
                        .build();

                given(userService.createUser(any(UserRequest.class)))
                        .willReturn(userResponse);
            }

            @Test
            @DisplayName("201 코드와 생성된 사용자를 응답한다")
            void it_returns_201_with_created_user() throws Exception {
                mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("password").exists());
            }
        }

        @Nested
        @DisplayName("생성할 사용자 정보가 유효하지 않은 경우")
        class Context_with_empty_value {
            @BeforeEach
            void setUp() {
                userRequest = UserRequest.builder()
                        .name("")
                        .email("")
                        .password("")
                        .build();
            }

            @DisplayName("404 상태 코드를 응답한다")
            @Test
            void it_returns_404_code() throws Exception {
                mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("PATCH /users/{id} 요청은")
    class Describe_PATCH_user_id {
        @Nested
        @DisplayName("수정할 사용자 정보가, 존재하는 id와 주어지면")
        class Context_with_update_user {

            UserRequest userUpdateRequest;

            @BeforeEach
            void setUp() {
                UserResponse updateResponse = UserResponse.builder()
                        .id(USER_ID)
                        .name(NEW_NAME)
                        .email(NEW_EMAIL)
                        .password(NEW_PASSWORD)
                        .build();

                userUpdateRequest = UserRequest.builder()
                        .name(NEW_NAME)
                        .email(NEW_EMAIL)
                        .password(NEW_PASSWORD)
                        .build();

                given(userService.updateUser(eq(USER_ID), any(UserRequest.class)))
                        .willReturn(updateResponse);
            }

            @DisplayName("사용자 정보를 수정하고 200 코드를 응답한다")
            @Test
            void it_updates_user_detail_and_return_200() throws Exception {
                mockMvc.perform(
                        patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateRequest)))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(NEW_NAME)));
            }
        }
    }

    @Nested
    @DisplayName("DELETE /users/{id} 요청은")
    class Describe_DELETE_user_id {
        //TODO
    }
}
