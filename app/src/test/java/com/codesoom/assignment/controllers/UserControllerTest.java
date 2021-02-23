package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    final Long NOT_EXIST_ID = 1000L;
    final String NAME = "My Name";
    final String EMAIL = "my@gmail.com";
    final String PASSWORD = "My Password";
    final String INVALID_EMAIL = "gmail.com";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    UserData createUser() {
        return UserData.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    @Nested
    @DisplayName("POST /user 리퀘스트는")
    class Describe_POST_user {
        @Nested
        @DisplayName("유효한 user가 주어진다면")
        class Context_with_valid_user_data {
            UserData givenUser;

            @BeforeEach
            void setUp() {
                givenUser = createUser();
            }

            @DisplayName("201코드와 생성된 user를 응답한다")
            @Test
            void it_returns_201_code_with_created_user() throws Exception {
                mockMvc.perform(post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenUser))
                )
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("name").value(NAME))
                        .andExpect(jsonPath("email").value(EMAIL))
                        .andExpect(jsonPath("password").value(PASSWORD));
            }
        }

        @Nested
        @DisplayName("모든 데이터가 공백인 user가 주어진다면")
        class Context_with_all_blank_user_data {
            UserData givenUser = new UserData();

            @DisplayName("400코드를 응답한다")
            @Test
            void it_returns_400_code() throws Exception {
                mockMvc.perform(post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenUser))
                )
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("email 데이터가 유효하지 않은 user가 주어진다면")
        class Context_with_invalid_email_user_data {
            UserData givenUser;

            @BeforeEach
            void setUp() {
                givenUser = UserData.builder()
                        .name(NAME)
                        .email(INVALID_EMAIL)
                        .password(PASSWORD)
                        .build();

            }

            @DisplayName("400코드를 응답한다")
            @Test
            void it_returns_400_code() throws Exception {
                mockMvc.perform(post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenUser))
                )
                        .andExpect(status().isBadRequest());
            }
        }
    }
}
