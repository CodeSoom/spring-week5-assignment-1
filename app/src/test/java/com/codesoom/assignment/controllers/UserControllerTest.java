package com.codesoom.assignment.controllers;

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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
@DisplayName("UserController 클래스의")
class UserControllerTest {

    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User userAlice;
    private UserData validDataAlice;
    private UserData invalidDataAlice;

    @BeforeEach
    void prepareUser() {
        objectMapper = new ObjectMapper();
        userAlice = UserFixtures.alice();
        validDataAlice = UserFixtures.validAliceData();
        invalidDataAlice = UserFixtures.invalidAliceData();
    }

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
            void It_creates_user_and_returns_it() throws Exception {
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
}
