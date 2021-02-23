package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserController 클래스")
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private List<User> users;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .id(1L)
                .name("이름")
                .email("이메일")
                .password("password")
                .build();

        user2 = User.builder()
                .id(2L)
                .name("이름2")
                .email("이메일2")
                .password("password2")
                .build();
    }

    @Nested
    @DisplayName("GET 요청은")
    class Describe_GET {
        @Nested
        @DisplayName("저장된 회원이 여러명 있다면")
        class Context_with_users {
            @BeforeEach
            void setUp() {
                users = List.of(user1, user2);

                given(userService.getUsers())
                        .willReturn(users);
            }

            @Test
            @DisplayName("모든 회원 목록과 상태코드 200을 응답한다.")
            void it_responds_all_user_list_and_status_code_200() throws Exception {
                mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$", hasSize(users.size())))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("저장된 회원이 없다면")
        class Context_without_users {
            @BeforeEach
            void setUp() {
                given(userService.getUsers())
                        .willReturn(List.of());
            }

            @Test
            @DisplayName("비어있는 회원 목록과 상태코드 200을 응답한다.")
            void it_responds_empty_user_list_and_status_code_200() throws Exception {
                mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$", hasSize(0)))
                        .andExpect(status().isOk());
            }
        }
    }

}
