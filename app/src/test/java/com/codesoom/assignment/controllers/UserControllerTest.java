package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserCommandService;
import com.codesoom.assignment.dto.UserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private UserCommandService userCommandService;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    @DisplayName("createUser 메서드는")
    class Describe_createUser {
        @Nested
        @DisplayName("User 가 주어진다면")
        class Context_with_User {
            private UserRequest requestUser;
            private String requestBody;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                requestUser = UserRequest.builder()
                        .email("a@a.com")
                        .password("123")
                        .build();

                requestBody = objectMapper.writeValueAsString(requestUser);
            }

            @AfterEach
            void after() {
                userCommandService.deleteAll();
            }

            @Test
            @DisplayName("User 를 저장하고 리턴한다")
            void it_returns_User() throws Exception {
                mockMvc.perform(post("/users")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.email").value(requestUser.getEmail()))
                        .andExpect(jsonPath("$.password").value(requestUser.getPassword()));
            }
        }
    }
}