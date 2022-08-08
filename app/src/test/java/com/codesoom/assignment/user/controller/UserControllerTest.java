package com.codesoom.assignment.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.util.FileUtil;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("UserController 클래스의")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("POST /users 요청은")
    class Describe_create {
        @Nested
        @DisplayName("유저 정보가 주어지면")
        class Context_with_UserData {
            private Map<String, Object> userData = Map.of(
                "id", "BEOMKING",
                "password", "123456",
                "email", "qjawlsqjacks@naver.com"
            );

            @Test
            @DisplayName("유저를 생성하고 상태코드 201을 함께 응답한다")
            void It_returns_createdUser() throws Exception {
                mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userData)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id", Is.is("BEOMKING")))
                        .andExpect(jsonPath("$.password", Is.is("123456")))
                        .andExpect(jsonPath("$.email", Is.is("qjawlsqjacks@naver.com")));
            }
        }
    }
}
