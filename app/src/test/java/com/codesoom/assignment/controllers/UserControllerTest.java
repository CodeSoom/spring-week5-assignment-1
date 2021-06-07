package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserFixtures;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
@DisplayName("UserController 클래스의")
class UserControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final User userAlice = UserFixtures.alice();
    private final User userBob = UserFixtures.bob();
    private final UserData validDataAlice = UserFixtures.validAliceData();
    private final UserData validDataBob = UserFixtures.validBobData();
    private final UserData invalidDataAlice = UserFixtures.invalidAliceData();
    private final UserData invalidDataBob = UserFixtures.invalidBobData();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Nested
    @DisplayName("POST /users")
    class Describe_create_user {

        @Nested
        @DisplayName("만약 유효한 사용자 데이터가 주어진다면")
        class Context_with_valid_user_data {

            @BeforeEach
            void mocking() {
                given(userService.create(any(UserData.class)))
                        .willReturn(userAlice);
            }

            @Test
            @DisplayName("사용자를 생성한 후 생성된 사용자를 응답한다")
            void It_creates_user_and_returns_it() throws Exception {
                var result = mockMvc.perform(
                        post("/users")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validDataAlice)));

                result.andExpect(status().isCreated())
                      .andExpect(jsonPath("$.name",
                                          containsString(userAlice.getName())))
                      .andExpect(jsonPath("$.email",
                                          containsString(userAlice.getEmail())))
                      .andExpect(jsonPath("$.password",
                                          containsString(userAlice.getPassword())));

                verify(userService).create(any(UserData.class));
            }
        }

        @Nested
        @DisplayName("만약 유효하지 않은 사용자 데이터가 주어진다면")
        class Context_with_invalid_user_data {

            @BeforeEach
            void mocking() {
                given(userService.create(any(UserData.class)))
                        .willReturn(userAlice);
            }

            @Test
            @DisplayName("400 Bad Request 상태와 유효하지 않은 부분을 응답한다")
            void It_responds_bad_request_with_error_message() throws Exception {
                var result = mockMvc.perform(
                        post("/users")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidDataAlice)));

                result.andExpect(status().isBadRequest())
                      .andExpect(jsonPath("$.message",
                                          containsString("이메일은 필수 입력 항목입니다")));
            }
        }
    }

    @Nested
    @DisplayName("PATCH /users/{id}")
    class Describe_patch_user {
        private User userAliceToBob = User.builder()
                                          .id(userAlice.getId())
                                          .name(userBob.getName())
                                          .email(userBob.getEmail())
                                          .password(userBob.getPassword())
                                          .build();

        @Nested
        @DisplayName("만약 저장되어 있지 않은 사용자의 식별자가 주어진다면")
        class Context_with_not_existed_user {
            private final Long invalidUserId = -1L;

            @BeforeEach
            void mocking() {
                given(userService.patch(eq(invalidUserId), any(UserData.class)))
                        .willThrow(new UserNotFoundException(invalidUserId));
            }

            @Test
            @DisplayName("사용자를 생성한 후 생성된 사용자를 응답한다")
            void It_responds_not_found() throws Exception {
                var result = mockMvc.perform(
                        patch("/users/" + invalidUserId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validDataBob)));

                result.andExpect(status().isNotFound())
                      .andExpect(jsonPath("$.message",
                                          containsString("User not found: " + invalidUserId)));

                verify(userService).patch(eq(invalidUserId), any(UserData.class));
            }
        }

        @Nested
        @DisplayName("만약 유효한 사용자 데이터가 주어진다면")
        class Context_with_valid_user_data {

            @BeforeEach
            void mocking() {
                given(userService.patch(any(Long.class), any(UserData.class)))
                        .willReturn(userAliceToBob);
            }

            @Test
            @DisplayName("사용자를 생성한 후 생성된 사용자를 응답한다")
            void It_patches_user_attributes_and_returns_the_user() throws Exception {
                var result = mockMvc.perform(
                        patch("/users/" + userAlice.getId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validDataBob)));

                result.andExpect(status().isOk())
                      .andExpect(jsonPath("$.name",
                                          containsString(userAliceToBob.getName())))
                      .andExpect(jsonPath("$.email",
                                          containsString(userAliceToBob.getEmail())))
                      .andExpect(jsonPath("$.password",
                                          containsString(userAliceToBob.getPassword())));

                verify(userService).patch(any(Long.class), any(UserData.class));
            }
        }

        @Nested
        @DisplayName("만약 유효하지 않은 사용자 데이터가 주어진다면")
        class Context_with_invalid_user_data {

            @BeforeEach
            void mocking() {
                given(userService.patch(any(Long.class), any(UserData.class)))
                        .willReturn(userAliceToBob);
            }

            @Test
            @DisplayName("400 Bad Request 상태와 유효하지 않은 부분을 응답한다")
            void It_responds_bad_request_with_error_message() throws Exception {
                var result = mockMvc.perform(
                        patch("/users/" + userAlice.getId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidDataBob)));

                result.andExpect(status().isBadRequest())
                      .andExpect(jsonPath("$.message",
                                          containsString("비밀번호는 필수 입력 항목입니다")));
            }
        }
    }

    @Nested
    @DisplayName("DELETE /users/{id}")
    class Describe_delete_user {

        @Nested
        @DisplayName("만약 저장되어 있는 사용자의 식별자가 주어진다면")
        class Context_with_existed_user_id {

            @BeforeEach
            void mocking() {
                doNothing().when(userService)
                           .delete(eq(userAlice.getId()));
            }

            @Test
            @DisplayName("해당 식별자의 사용자를 제거한다")
            void It_deletes_the_user() throws Exception {
                var result = mockMvc.perform(
                        delete("/users/" + userAlice.getId()));

                result.andExpect(status().isNoContent());

                verify(userService, times(1))
                        .delete(eq(userAlice.getId()));
            }
        }

        @Nested
        @DisplayName("만약 유효하지 않은 사용자 데이터가 주어진다면")
        class Context_with_invalid_user_data {
            private final Long invalidUserId = -1L;

            @BeforeEach
            void mocking() {
                doThrow(new UserNotFoundException(invalidUserId))
                        .when(userService)
                        .delete(eq(invalidUserId));
            }

            @Test
            @DisplayName("사용자를 찾을 수 없다는 메시지를 응답한다")
            void It_responds_not_found_with_error_message() throws Exception {
                var result = mockMvc.perform(
                        delete("/users/" + invalidUserId));

                result.andExpect(status().isNotFound())
                      .andExpect(jsonPath("$.message",
                                          containsString("User not found: " + invalidUserId)));
            }
        }
    }
}
