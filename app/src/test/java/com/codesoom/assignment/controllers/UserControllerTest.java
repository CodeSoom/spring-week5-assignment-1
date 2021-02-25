package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.User;
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
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("UserController 클래스")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Nested
    @DisplayName("POST /user 요청은")
    class Describe_POST_user {
        @Nested
        @DisplayName("생성할 사용자 정보가 주어지면")
        class Context_with_user {
            @BeforeEach
            void setUp() {
                User user = User.builder()
                        .id(1L)
                        .name("Min")
                        .email("min@gmail.com")
                        .password("1q2w3e!")
                        .build();
                given(userService.createUser(any(User.class)))
                        .willReturn(user);
            }

            @Test
            @DisplayName("201 코드와 생성된 사용자를 응답한다")
            void it_returns_201_with_created_user() {
                mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("password").exists())

            }

        }
    }

    @Test
    void createUser() {
    }
}
