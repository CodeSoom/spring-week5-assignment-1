package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserController 클래스")
class UserControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        final UserController controller = new UserController();
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Nested
    @DisplayName("POST /user 요청은")
    class Describe_postUser {
        @Nested
        @DisplayName("유효한 회원 정보를 전달하면")
        class Context_withValidUserData {
            private final UserData validUserData = UserData.builder()
                    .name("name")
                    .email("email")
                    .password("password")
                    .build();

            @Test
            @DisplayName("Created status, 생성된 회원 정보를 반환한다")
            void it_returnsCratedStatusAndUserData() throws Exception {
                final ObjectMapper objectMapper = new ObjectMapper();
                final String requestContent = objectMapper.writeValueAsString(validUserData);

                final UserData expectUserData = UserData.builder()
                        .id(1L)
                        .name(validUserData.getName())
                        .email(validUserData.getEmail())
                        .password(validUserData.getPassword())
                        .build();
                final String expectContent = objectMapper.writeValueAsString(expectUserData);

                mockMvc.perform(
                            post("/users")
                                    .content(requestContent)
                                    .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isCreated())
                        .andExpect(content().json(expectContent));
            }
        }
    }
}
