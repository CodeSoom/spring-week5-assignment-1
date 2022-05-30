package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    public static final String EMAIl = "kimchi@joa.com";
    public static final String NAME = "갓김치";
    public static final String PASSWORD = "1234567";

    UserData userData;

    @BeforeEach
    void setUp() {
        userData = UserData.builder()
                .email(EMAIl)
                .name(NAME)
                .password(PASSWORD)
                .build();
    }

    @Nested
    @DisplayName("POST /users 요청은")
    class Describe_create {

        @BeforeEach
        void setUp() {
            given(userService.signUp(any(UserData.class)))
                    .will((invocation -> {
                        UserData userData = invocation.getArgument(0);
                        return User.builder()
                                .id(1L)
                                .name(userData.getName())
                                .email(userData.getEmail())
                                .password(userData.getPassword())
                                .build();
                    }));
        }

        @Test
        @DisplayName("생성된 유저를 응답한다.")
        void it_responses_created_user() throws Exception {
            mockMvc.perform(post("/users")
                            .content(toJson(userData))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.name").value(NAME))
                    .andExpect(jsonPath("$.password").value(PASSWORD))
                    .andExpect(jsonPath("$.email").value(EMAIl));
        }
    }

    private String toJson(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }
}