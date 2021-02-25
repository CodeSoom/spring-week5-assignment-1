package com.codesoom.assignment.User.controller;

import com.codesoom.assignment.common.exception.UserNotFoundException;
import com.codesoom.assignment.user.application.UserService;
import com.codesoom.assignment.user.controller.UserController;
import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.dto.UserData;
import com.codesoom.assignment.user.dto.UserUpdate;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    User user;
    User updatedUser;

    private final Long USER_ID = 1L;
    private final String USER_NAME = "name1";
    private final String USER_EMAIL = "email1@gmail.com";
    private final String USER_PASSWORD = "password1";
    private final String NEW_USER_NAME = "새로운이름";
    private final String NEW_USER_EMAIL = "newEmail@gmail.com";
    private final String NEW_USER_PASSWORD = "newPassword";
    private final String UPDATED_USER_NAME = "newName";
    private final String UPDATED_USER_EMAIL = "updatedEmail@gmail.com";
    private final String UPDATED_USER_PASSWORD = "updatedPassword";
    private final Long NOT_EXISTED_USER_ID = -999L;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(USER_ID)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();
    }

    @Nested
    @DisplayName("POST 요청은")
    class Describe_post_request {
        User newUser;

        @Nested
        @DisplayName("새로운 회원을 추가하면")
        class Context_add_new_user {
            @BeforeEach
            void setUp() {
                newUser = User.builder()
                        .name(NEW_USER_NAME)
                        .email(NEW_USER_EMAIL)
                        .password(NEW_USER_PASSWORD)
                        .build();

                given(userService.createUser(any(UserData.class)))
                        .willReturn(newUser);
            }

            @Test
            @DisplayName("201코드와 생성된 회원을 응답한다.")
            void it_responds_201_and_new_user() throws Exception {
                mockMvc.perform(
                        post("/users")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newUser))
                )
                        .andExpect(status().isCreated())
                        .andExpect(content().string(containsString(NEW_USER_NAME)))
                        .andExpect(content().string(containsString(NEW_USER_EMAIL)))
                        .andExpect(content().string(containsString(NEW_USER_PASSWORD)));
            }
        }
    }

    @Nested
    @DisplayName("PATCH 요청은")
    class Describe_patch_request {

        @Nested
        @DisplayName("등록된 회원의 id가 주어지면")
        class Context_contains_target_id {
            @BeforeEach
            void setUp() {
                given(userService.createUser(any(UserData.class)))
                        .willReturn(user);

                updatedUser = User.builder()
                        .name(UPDATED_USER_NAME)
                        .email(UPDATED_USER_EMAIL)
                        .password(UPDATED_USER_PASSWORD)
                        .build();

                given(userService.updateUser(eq(USER_ID), any(UserUpdate.class)))
                        .willReturn(updatedUser);
            }

            @Test
            @DisplayName("200코드와 수정된 회원을 응답한다.")
            void it_responds_200_and_updated_user() throws Exception {
                mockMvc.perform(patch("/users/{id}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser))
                )
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(UPDATED_USER_NAME)))
                        .andExpect(content().string(containsString(UPDATED_USER_EMAIL)))
                        .andExpect(content().string(containsString(UPDATED_USER_PASSWORD)));
            }
        }

        @Nested
        @DisplayName("등록되지 않은 회원의 id가 주어지면")
        class Context_not_contains_target_id {
            @BeforeEach
            void setUp() {
                given(userService.updateUser(eq(NOT_EXISTED_USER_ID), any(UserUpdate.class)))
                        .willThrow(new UserNotFoundException(NOT_EXISTED_USER_ID));
            }

            @Test
            @DisplayName("404코드를 응답한다.")
            void it_responds_404() throws Exception {
                mockMvc.perform(patch("/users/{id}", NOT_EXISTED_USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                )
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("DELETE 요청은")
    class Describe_delete_request {

        @Nested
        @DisplayName("등록된 회원의 id가 주어지면")
        class Context_contains_target_id {
            @BeforeEach
            void setUp() {
                given(userService.createUser(any(UserData.class)))
                        .willReturn(user);
            }

            @Test
            @DisplayName("id에 해당하는 회원을 삭제한다.")
            void it_deletes_user() throws Exception {
                mockMvc.perform(delete("/users/{id}", USER_ID));
            }
        }

        @Nested
        @DisplayName("등록되지 않은 회원의 id가 주어지면")
        class Context_not_contains_target_id {
            @BeforeEach
            void setUp() {
                willThrow(new UserNotFoundException(NOT_EXISTED_USER_ID))
                        .given(userService).deleteUser(NOT_EXISTED_USER_ID);
            }

            @Test
            @DisplayName("404코드를 응답한다.")
            void it_responds_404() throws Exception{
                mockMvc.perform(delete("/users/{id}", NOT_EXISTED_USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                )
                        .andExpect(status().isNotFound());
            }
        }
    }
}
