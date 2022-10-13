package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserCommandService;
import com.codesoom.assignment.dto.UserRequest;
import com.codesoom.assignment.dto.UserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private UserCommandService userCommandService;
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Long INVALID_USER_ID = 0L;

    @Nested
    @DisplayName("createUser 메서드는")
    class Describe_createUser {
        @Nested
        @DisplayName("User 가 주어진다면")
        class Context_with_User {
            private UserRequest requestUser;
            private String requestBody;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                requestUser = UserRequest.builder()
                        .email("a@a.com")
                        .name("김 코")
                        .password("123")
                        .build();

                requestBody = objectMapper.writeValueAsString(requestUser);
            }

            @AfterEach
            void after() {
                userCommandService.deleteAll();
            }

            @Test
            @DisplayName("User 를 저장하고 리턴한다")
            void it_returns_User() throws Exception {
                mockMvc.perform(post("/users")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.email").value(requestUser.getEmail()))
                        .andExpect(jsonPath("$.name").value(requestUser.getName()))
                        .andExpect(jsonPath("$.password").value(requestUser.getPassword()));
            }
        }

        @Nested
        @DisplayName("UserRequest 에 일부 필드가 null 인 경우")
        class Context_with_partial_null_userRequest {
            private String requestBodyWithNullEmail;
            private String requestBodyWithNullName;
            private String requestBodyWithNullPassword;
            private String requestBodyWithBlankEmail;
            private String requestBodyWithBlankName;
            private String requestBodyWithBlankPassword;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                UserRequest userRequestWithNullEmail = UserRequest.builder()
                        .email(null)
                        .name("김 코")
                        .password("123")
                        .build();
                requestBodyWithNullEmail = objectMapper.writeValueAsString(userRequestWithNullEmail);

                UserRequest userRequestWithNullName = UserRequest.builder()
                        .email("a@a.com")
                        .name(null)
                        .password("123")
                        .build();
                requestBodyWithNullName = objectMapper.writeValueAsString(userRequestWithNullName);

                UserRequest requestUserWithNullPassword = UserRequest.builder()
                        .email("a@a.com")
                        .name("김 코")
                        .password(null)
                        .build();
                requestBodyWithNullPassword = objectMapper.writeValueAsString(requestUserWithNullPassword);

                UserRequest requestUserWithBlankEmail = UserRequest.builder()
                        .email("")
                        .name("김 코")
                        .password("123")
                        .build();
                requestBodyWithBlankEmail = objectMapper.writeValueAsString(requestUserWithBlankEmail);

                UserRequest requestUserWithBlankName = UserRequest.builder()
                        .email("a@a.com")
                        .name("")
                        .password("123")
                        .build();
                requestBodyWithBlankName = objectMapper.writeValueAsString(requestUserWithBlankName);

                UserRequest requestUserWithBlankPassword = UserRequest.builder()
                        .email("a@a.com")
                        .name("김 코")
                        .password("")
                        .build();
                requestBodyWithBlankPassword = objectMapper.writeValueAsString(requestUserWithBlankPassword);
            }

            @AfterEach
            void after() {
                userCommandService.deleteAll();
            }

            @Test
            @DisplayName("email 필드가 null 인 경우 400 status 코드를 응답한다")
            void it_returns_badRequest_with_null_email() throws Exception {
                mockMvc.perform(post("/users")
                                .content(requestBodyWithNullEmail)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("name 필드가 null 인 경우 400 status 코드를 응답한다")
            void it_returns_badRequest_with_null_name() throws Exception {
                mockMvc.perform(post("/users")
                                .content(requestBodyWithNullName)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("password 필드가 null 인 경우 400 status 코드를 응답한다")
            void it_returns_badRequest_with_null_password() throws Exception {
                mockMvc.perform(post("/users")
                                .content(requestBodyWithNullPassword)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("email 필드가 빈 문자열인 경우 400 status 코드를 응답한다")
            void it_returns_badRequest_with_blank_email() throws Exception {
                mockMvc.perform(post("/users")
                                .content(requestBodyWithBlankEmail)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("name 필드가 빈 문자열인 경우 400 status 코드를 응답한다")
            void it_returns_badRequest_with_blank_name() throws Exception {
                mockMvc.perform(post("/users")
                                .content(requestBodyWithBlankName)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("password 필드가 빈 문자열인 경우 400 status 코드를 응답한다")
            void it_returns_badRequest_with_blank_password() throws Exception {
                mockMvc.perform(post("/users")
                                .content(requestBodyWithBlankPassword)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("updateUser 메서드는")
    class Describe_updateUser {
        @Nested
        @DisplayName("요청하는 User 의 필드가 모두 있는 경우")
        class Context_with_full_value {
            private Long userId;
            private UserRequest requestUser;
            private String requestBody;


            @BeforeEach
            void setUp() throws JsonProcessingException {
                UserResponse savedUser = userCommandService.createUser(
                        UserRequest.builder()
                                .email("before@before.com")
                                .name("김 코")
                                .password("123")
                                .build()
                );
                userId = savedUser.getId();

                requestUser = UserRequest.builder()
                        .email("after@after.com")
                        .name("김 딩")
                        .password("456")
                        .build();
                requestBody = objectMapper.writeValueAsString(requestUser);

            }

            @Test
            @DisplayName("모든 필드를 수정 후 리턴한다")
            void it_returns_updated_User() throws Exception {
                mockMvc.perform(patch("/users/" + userId)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.email").value(requestUser.getEmail()))
                        .andExpect(jsonPath("$.name").value(requestUser.getName()))
                        .andExpect(jsonPath("$.password").value(requestUser.getPassword()));
            }
        }

        @Nested
        @DisplayName("UserRequest 에 일부 필드가 invalid 한 경우")
        class Context_with_invalid_userRequest {
            private Long userId;

            private String requestBodyWithNullEmail;
            private String requestBodyWithNullName;
            private String requestBodyWithNullPassword;
            private String requestBodyWithBlankEmail;
            private String requestBodyWithBlankName;
            private String requestBodyWithBlankPassword;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                UserResponse savedUser = userCommandService.createUser(
                        UserRequest.builder()
                                .email("before@before.com")
                                .name("김 코")
                                .password("123")
                                .build()
                );
                userId = savedUser.getId();

                UserRequest userRequestWithNullEmail = UserRequest.builder()
                        .email(null)
                        .name("김 코")
                        .password("123")
                        .build();
                requestBodyWithNullEmail = objectMapper.writeValueAsString(userRequestWithNullEmail);

                UserRequest userRequestWithNullName = UserRequest.builder()
                        .email("a@a.com")
                        .name(null)
                        .password("123")
                        .build();
                requestBodyWithNullName = objectMapper.writeValueAsString(userRequestWithNullName);

                UserRequest requestUserWithNullPassword = UserRequest.builder()
                        .email("a@a.com")
                        .name("김 코")
                        .password(null)
                        .build();
                requestBodyWithNullPassword = objectMapper.writeValueAsString(requestUserWithNullPassword);

                UserRequest requestUserWithBlankEmail = UserRequest.builder()
                        .email("")
                        .name("김 코")
                        .password("123")
                        .build();
                requestBodyWithBlankEmail = objectMapper.writeValueAsString(requestUserWithBlankEmail);

                UserRequest requestUserWithBlankName = UserRequest.builder()
                        .email("a@a.com")
                        .name("")
                        .password("123")
                        .build();
                requestBodyWithBlankName = objectMapper.writeValueAsString(requestUserWithBlankName);

                UserRequest requestUserWithBlankPassword = UserRequest.builder()
                        .email("a@a.com")
                        .name("김 코")
                        .password("")
                        .build();
                requestBodyWithBlankPassword = objectMapper.writeValueAsString(requestUserWithBlankPassword);
            }

            @AfterEach
            void after() {
                userCommandService.deleteAll();
            }

            @Test
            @DisplayName("email 필드가 null 인 경우 400 status 코드를 응답한다")
            void it_returns_badRequest_with_null_email() throws Exception {
                mockMvc.perform(patch("/users/" + userId)
                                .content(requestBodyWithNullEmail)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("name 필드가 null 인 경우 400 status 코드를 응답한다")
            void it_returns_badRequest_with_null_name() throws Exception {
                mockMvc.perform(patch("/users/" + userId)
                                .content(requestBodyWithNullName)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("password 필드가 null 인 경우 400 status 코드를 응답한다")
            void it_returns_badRequest_with_null_password() throws Exception {
                mockMvc.perform(patch("/users/" + userId)
                                .content(requestBodyWithNullPassword)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("email 필드가 빈 문자열인 경우 400 status 코드를 응답한다")
            void it_returns_badRequest_with_blank_email() throws Exception {
                mockMvc.perform(patch("/users/" + userId)
                                .content(requestBodyWithBlankEmail)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("name 필드가 빈 문자열인 경우 400 status 코드를 응답한다")
            void it_returns_badRequest_with_blank_name() throws Exception {
                mockMvc.perform(patch("/users/" + userId)
                                .content(requestBodyWithBlankName)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("password 필드가 빈 문자열인 경우 400 status 코드를 응답한다")
            void it_returns_badRequest_with_blank_password() throws Exception {
                mockMvc.perform(patch("/users/" + userId)
                                .content(requestBodyWithBlankPassword)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("저장되어 있지 않은 User 의 id로 요청한 경우")
        class Context_with_non_existence_id {
            private UserRequest requestUser;
            private String requestBody;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                requestUser = UserRequest.builder()
                        .email("a@a.com")
                        .name("김 코")
                        .password("123")
                        .build();
                requestBody = objectMapper.writeValueAsString(requestUser);

            }

            @Test
            @DisplayName("사용자를 찾을 수 없다는 예외를 던진다")
            void it_throws_exception() throws Exception {
                mockMvc.perform(patch("/users/" + INVALID_USER_ID)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("deleteUser 메서드는")
    class Describe_deleteUser {
        @Nested
        @DisplayName("저장되어있는 user 의 id가 주어진다면 ")
        class Context_with_existing_user_id {
            private Long userId;

            @BeforeEach
            void setUp() {
                UserResponse savedUser = userCommandService.createUser(
                        UserRequest.builder()
                                .email("a@a.com")
                                .name("김 코")
                                .password("123")
                                .build()
                );
                userId = savedUser.getId();

            }

            @Test
            @DisplayName("user 를 삭제한다")
            void it_returns_204() throws Exception {
                mockMvc.perform(delete("/users/" + userId))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("저장되어 있지 않는 user 의 id가 주어진다면 ")
        class Context_with_non_existence_user_id {
            @Test
            @DisplayName("제품을 찾을 수 없는 예외를 던진다")
            void it_delete_user() throws Exception {
                mockMvc.perform(delete("/users/" + INVALID_USER_ID))
                        .andExpect(status().isNotFound());
            }
        }
    }
}
