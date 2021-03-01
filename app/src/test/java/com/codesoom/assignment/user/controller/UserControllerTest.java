package com.codesoom.assignment.user.controller;

import com.codesoom.assignment.common.exceptions.DuplicateUserException;
import com.codesoom.assignment.common.exceptions.UserNotFoundException;
import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.dto.UserCreateRequest;
import com.codesoom.assignment.user.dto.UserUpdateRequest;
import com.codesoom.assignment.user.service.UserService;
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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserController 클래스")
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private final Long existingId = 1L;
    private final Long notExistingId = 100L;

    private List<User> users;
    private User user1;
    private User user2;

    private UserCreateRequest validUserCreateRequest;
    private UserCreateRequest invalidUserCreateRequest;

    private UserUpdateRequest validUserUpdateRequest;
    private UserUpdateRequest invalidUserUpdateRequest;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .id(1L)
                .name("이름")
                .email("이메일")
                .build();

        user2 = User.builder()
                .id(2L)
                .name("이름2")
                .email("이메일2")
                .build();

        validUserCreateRequest = UserCreateRequest.builder()
                .name("새 이름")
                .email("newEmail@example.com")
                .password("12345678")
                .build();

        invalidUserCreateRequest = UserCreateRequest.builder()
                .name("")
                .email("")
                .password("")
                .build();

        validUserUpdateRequest = UserUpdateRequest.builder()
                .name("새 이름")
                .password("12345678")
                .build();

        invalidUserUpdateRequest = UserUpdateRequest.builder()
                .name("")
                .password("")
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
                        .willReturn(user1);
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

    @Nested
    @DisplayName("POST 요청은")
    class Describe_POST {
        @Nested
        @DisplayName("올바른 회원 생성 정보가 주어진다면")
        class Context_with_a_valid_user {
            @BeforeEach
            void setUp() {
                given(userService.createUser(any(User.class)))
                        .willReturn(user1);
            }

            @Test
            @DisplayName("생성된 회원과 상태코드 201을 응답한다.")
            void it_responds_the_created_user_and_status_code_201() throws Exception {
                mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserCreateRequest)))
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("name").exists())
                        .andExpect(jsonPath("email").exists())
                        .andExpect(jsonPath("password").doesNotExist())
                        .andExpect(status().isCreated());
            }
        }

        @Nested
        @DisplayName("올바르지 않은 회원 생성 정보가 주어진다면")
        class Context_with_an_invalid_user {
            @Test
            @DisplayName("상태코드 400을 응답한다.")
            void it_responds_status_code_400() throws Exception {
                mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserCreateRequest)))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("중복된 회원 생성 정보가 주어진다면")
        class Context_with_an_duplicate_user {
            private UserCreateRequest duplicateUserCreateRequest;

            @BeforeEach
            void setUp() {
                duplicateUserCreateRequest = UserCreateRequest.builder()
                        .name("duplicated User")
                        .email("duplicatedUser@example.com")
                        .password("12345678")
                        .build();

                given(userService.createUser(any(User.class)))
                        .willThrow(new DuplicateUserException());
            }

            @Test
            @DisplayName("에러메시지와 상태코드 409을 응답한다.")
            void it_responds_the_error_message_and_status_code_409() throws Exception {
                mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateUserCreateRequest)))
                        .andExpect(jsonPath("name").doesNotExist())
                        .andExpect(jsonPath("message").exists())
                        .andExpect(status().isConflict());
            }
        }
    }

    @Nested
    @DisplayName("PATCH 요청은")
    class Describe_PATCH {
        @Nested
        @DisplayName("존재하는 회원 id가 주어진다면")
        class Context_with_an_existing_user_id {
            @BeforeEach
            void setUp() {
                given(userService.updateUser(eq(existingId), any(User.class)))
                        .willReturn(user1);
            }

            @Test
            @DisplayName("수정된 회원과 상태코드 200을 응답한다.")
            void it_responds_the_updated_user_and_status_code_200() throws Exception {
                mockMvc.perform(patch("/users/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserUpdateRequest)))
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
                given(userService.updateUser(eq(notExistingId), any(User.class)))
                        .willThrow(new UserNotFoundException());
            }

            @Test
            @DisplayName("에러메시지와 상태코드 404를 응답한다.")
            void it_responds_the_error_message_and_status_code_404() throws Exception {
                mockMvc.perform(patch("/users/{id}", notExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserUpdateRequest)))
                        .andExpect(jsonPath("name").doesNotExist())
                        .andExpect(jsonPath("message").exists())
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("올바르지 않은 회원 수정 정보가 주어진다면")
        class Context_with_an_invalid_user {
            @Test
            @DisplayName("상태코드 400을 응답한다.")
            void it_responds_status_code_400() throws Exception {
                mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserUpdateRequest)))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("DELETE 요청은")
    class Describe_DELETE {
        @Nested
        @DisplayName("존재하는 회원 id가 주어진다면")
        class Context_with_an_existing_user_id {
            @Test
            @DisplayName("상태코드 204를 응답한다.")
            void it_responds_status_code_204() throws Exception {
                mockMvc.perform(delete("/users/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 회원 id가 주어진다면")
        class Context_with_not_existing_user_id {
            @BeforeEach
            void setUp() {
                willThrow(new UserNotFoundException())
                        .given(userService).deleteUser(notExistingId);
            }

            @Test
            @DisplayName("에러메시지와 상태코드 404를 응답한다.")
            void it_responds_the_error_message_and_status_code_404() throws Exception {
                mockMvc.perform(delete("/users/{id}", notExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("name").doesNotExist())
                        .andExpect(jsonPath("message").exists())
                        .andExpect(status().isNotFound());
            }
        }
    }

}
