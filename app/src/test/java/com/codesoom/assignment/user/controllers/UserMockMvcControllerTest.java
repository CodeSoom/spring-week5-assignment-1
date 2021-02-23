package com.codesoom.assignment.user.controllers;

import com.codesoom.assignment.user.application.UserService;
import com.codesoom.assignment.user.dto.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("UserController 클래스")
class UserMockMvcControllerTest {
    private static final Long USER_ID = 1L;
    private static final Long NOT_EXIST_ID = -1L;

    private static final String USER_NAME = "test";
    private static final String USER_PASSWORD = "pass";
    private static final String USER_EMAIL = "test@test.com";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Nested
    @DisplayName("GET /users는")
    class Describe_getUserts {
        @Nested
        @DisplayName("등록된 사용자가 있으면")
        class Context_with_users {
            List<UserResponseDto> users;

            @BeforeEach
            void setUp() {
                UserResponseDto product = UserResponseDto.builder()
                        .id(USER_ID)
                        .name(USER_NAME)
                        .email(USER_EMAIL)
                        .password(USER_PASSWORD)
                        .build();

                users = Collections.singletonList(product);

                given(userService.getUsers())
                        .willReturn(users);
            }

            @DisplayName("200 상태 코드, OK 상태와 사용자 목록을 응답한다.")
            @Test
            void It_responds_ok_with_users() throws Exception {
                mockMvc.perform(get("/users"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(objectMapper.writeValueAsString(users)));
            }
        }

        @Nested
        @DisplayName("등록된 사용자가 없으면")
        class Context_without_users {

            @DisplayName("200 상태코드, OK 상태와 비어있는 사용자 목록을 응답한다.")
            @Test
            void It_responds_ok_with_empty_users() throws Exception {
                mockMvc.perform(get("/users"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("[]")));
            }
        }
    }
}
