package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserResponse;
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

    private final Long existingId = 1L;
    private final Long notExistingId = 100L;

    private List<User> users;
    private User user1;
    private User user2;
    private UserResponse userResponse;

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

        userResponse = UserResponse.builder()
                .id(1L)
                .name("이름1")
                .email("이메일1")
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

        @Nested
        @DisplayName("존재하는 회원 id가 주어진다면")
        class Context_with_an_existing_user_id {
            @BeforeEach
            void setUp() {
                given(userService.getUser(existingId))
                        .willReturn(userResponse);
            }

            @Test
            @DisplayName("찾은 회원과 상태코드 200을 응답한다.")
            void it_responds_the_found_user_and_status_code_200() throws Exception {
                mockMvc.perform(get("/users/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("name").exists())
                        .andExpect(jsonPath("email").exists())
                        .andExpect(jsonPath("password").doesNotExist())
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 회원 id가 주어진다면")
        class Context_with_not_existing_user_id {
            @BeforeEach
            void setUp() {
                given(userService.getUser(notExistingId))
                        .willThrow(new UserNotFoundException());
            }

            @Test
            @DisplayName("에러메시지와 상태코드 404를 응답한다.")
            void it_responds_the_error_message_and_status_code_404() throws Exception {
                mockMvc.perform(get("/users/{id}", notExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("name").doesNotExist())
                        .andExpect(jsonPath("message").exists())
                        .andExpect(status().isNotFound());
            }
        }
    }

}
