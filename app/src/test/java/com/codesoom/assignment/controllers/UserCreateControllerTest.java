package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserCrudService;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("UserCreateController")
class UserCreateControllerTest {
    @MockBean
    private UserCrudService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final Long USER_ID = 1L;
    private final String USER_NAME = "Test User";
    private final String USER_EMAIL = "hello@gmail.com";
    private final String USER_PASSWORD = "yahOo~!@12345";
    private User user;
    private User userWithoutId;
    private User userWithEmptyName;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(USER_ID)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();
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
    @DisplayName("create 메소드는")
    class Describe_create {
        @Nested
        @DisplayName("유효한 매개변수를 전달 받는다면")
        class Context_with_valid_param {
            @BeforeEach
            void setUp() {
                given(service.create(any(User.class))).willReturn(user);
            }

            @Test
            @DisplayName("HTTP Status Code 201 CREATED 응답한다")
            void it_responds_with_201() throws Exception {
                mockMvc.perform(post("/users")
                                .content(jsonFrom(userWithoutId))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());

            }
        }

        @Nested
        @DisplayName("유효하지 않은 RequestBody를 전달 받는다면")
        class Context_with_invalid_request_body {
            @BeforeEach
            void setUp() {
                given(service.create(any(User.class))).willReturn(userWithEmptyName);
            }

            @Test
            @DisplayName("HTTP Status Code 400 BAD REQUEST 응답한다")
            void it_responds_with_400() throws Exception {
                mockMvc.perform(post("/users")
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
