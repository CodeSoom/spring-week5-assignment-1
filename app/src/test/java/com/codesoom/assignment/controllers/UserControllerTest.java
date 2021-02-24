package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    final Long EXIST_ID = 1L;
    final Long NOT_EXIST_ID = 1000L;
    final String NAME = "My Name";
    final String EMAIL = "my@gmail.com";
    final String PASSWORD = "My Password";
    final String INVALID_EMAIL = "gmail.com";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    UserData createUser() {
        return UserData.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(userService);
        User user = User.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
        given(userService.createUser(any(UserData.class))).willReturn(user);

        given(userService.findUser(EXIST_ID)).willReturn(user);

        given(userService.findUser(NOT_EXIST_ID))
                .willThrow(new UserNotFoundException(NOT_EXIST_ID));

        given(userService.updateUser(eq(EXIST_ID), any(UserData.class)))
                .will(invocation -> {
                    UserData userData = invocation.getArgument(1);
                    return User.builder()
                            .name(userData.getName())
                            .email(userData.getEmail())
                            .password(userData.getPassword());
                });

        given(userService.deleteUser(EXIST_ID)).willReturn(user);
        
        given(userService.deleteUser(NOT_EXIST_ID))
                .willThrow(new UserNotFoundException(NOT_EXIST_ID));
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

    @Nested
    @DisplayName("DELETE /user/{id} 리퀘스트는")
    class Describe_DELETE_user {
        @Nested
        @DisplayName("존재하는 user id가 주어진다면")
        class Context_with_exist_user_id {
            Long givenId = EXIST_ID;

            @DisplayName("204코드를 응답한다")
            @Test
            void it_returns_204_code() throws Exception {
                mockMvc.perform(delete("/users/{id}", givenId))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 user id가 주어진다면")
        class Context_with_not_exist_user_id {
            Long givenId = NOT_EXIST_ID;

            @DisplayName("404코드를 응답한다")
            @Test
            void it_returns_204_code() throws Exception {
                mockMvc.perform(delete("/users/{id}", givenId))
                        .andExpect(status().isNotFound());
            }
        }
    }
}
