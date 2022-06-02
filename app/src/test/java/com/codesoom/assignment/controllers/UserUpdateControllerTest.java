package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserCrudService;
import com.codesoom.assignment.application.exceptions.UserNotFoundException;
import com.codesoom.assignment.controllers.dtos.UserRequestData;
import com.codesoom.assignment.domain.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("UserUpdateController")
class UserUpdateControllerTest {
    @MockBean
    private UserCrudService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final Long USER_ID = 1L;
    private final Long USER_ID_NOT_EXISTING = 10L;
    private final String USER_NAME = "Test User";
    private final String USER_EMAIL = "hello@gmail.com";
    private final String USER_PASSWORD = "yahOo~!@12345";
    private User userUpdated;
    private User userWithoutId;
    private User userWithEmptyName;

    @BeforeEach
    void setUp() {
        userWithoutId = User.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();
        userWithEmptyName = User.builder()
                .name(" ")
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();
    }

    @Nested
    @DisplayName("patch 메소드는")
    class Describe_patch {
        @Nested
        @DisplayName("유효한 매개변수를 전달 받는다면")
        class Context_with_valid_param {
            @BeforeEach
            void setUp() {
                userUpdated = User.builder()
                        .id(USER_ID)
                        .name(USER_NAME + "UPDATED")
                        .email(USER_EMAIL)
                        .password(USER_PASSWORD)
                        .build();

                given(service.update(eq(USER_ID), any(User.class))).willReturn(userUpdated);
            }

            @Test
            @DisplayName("HTTP Status Code 200 OK 응답한다")
            void it_responds_with_200_ok() throws Exception {
                mockMvc.perform(patch("/users/" + USER_ID)
                                .content(jsonFrom(userWithoutId))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 ID를 매개변수로 전달 받는다면")
        class Context_without_existing_user {
            @BeforeEach
            void setUp() {
                given(service.update(eq(USER_ID_NOT_EXISTING), any(User.class)))
                        .willThrow(new UserNotFoundException(USER_ID_NOT_EXISTING));
            }

            @Test
            @DisplayName("HTTP Status Code 404 NOT FOUND 응답한다")
            void it_responds_with_404() throws Exception {
                mockMvc.perform(patch("/users/" + USER_ID_NOT_EXISTING)
                                .content(jsonFrom(userWithoutId))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("유효하지 않은 RequestBody를 전달 받는다면")
        class Context_with_invalid_request_body {
            @BeforeEach
            void setUp() {
                given(service.update(eq(USER_ID), any(User.class))).willReturn(userWithEmptyName);
            }

            @Test
            @DisplayName("HTTP Status Code 400 BAD REQUEST 응답한다")
            void it_responds_with_400() throws Exception {
                mockMvc.perform(patch("/users/" + USER_ID)
                                .content(jsonFrom(userWithEmptyName))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());

            }
        }
    }

    private String jsonFrom(User user) throws JsonProcessingException {
        UserRequestData requestData = UserRequestData.builder()
                .name(user.getName())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        return objectMapper.writeValueAsString(requestData);
    }
}
