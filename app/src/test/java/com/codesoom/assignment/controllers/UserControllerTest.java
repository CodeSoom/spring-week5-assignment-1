package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserController 클래스")
class UserControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        final UserController controller = new UserController();
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Nested
    @DisplayName("POST /users 요청은")
    class Describe_postUser {
        @Nested
        @DisplayName("유효한 회원 정보를 전달하면")
        class Context_withValidUserData {
            private MockHttpServletRequestBuilder request;

            @BeforeEach
            void prepare() throws JsonProcessingException {
                final UserData userData = TestUserDataFactory.createValidUserData();
                final String content = objectMapper.writeValueAsString(userData);

                request = post("/users")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);
            }

            @Test
            @DisplayName("Created status, 생성된 회원 정보를 반환한다")
            void it_returnsCratedStatusAndUserData() throws Exception {
                final UserData expectedUserData = TestUserDataFactory.createValidUserData(1L);
                final String expectedContent = objectMapper.writeValueAsString(expectedUserData);

                mockMvc.perform(request)
                        .andExpect(status().isCreated())
                        .andExpect(content().json(expectedContent));
            }
        }
    }
}
