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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final Long USER_ID = 1L;
    private static final String USER_NAME = "김태우";
    private static final String USER_PASSWORD = "1234";
    private static final String USER_EMAIL = "xodnzlzl1597@gmail.com";

    private static final String NEW_NAME = "코드숨";
    private static final String NEW_PASSWORD = "1597";
    private static final String NEW_EMAIL = "codesoom@gmail.com";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    private UserData getUserData() {
        return UserData.builder()
                .name(USER_NAME)
                .password(USER_PASSWORD)
                .email(USER_EMAIL)
                .build();
    }

    private UserData updateUserData() {
        return UserData.builder()
                .name(NEW_NAME)
                .password(NEW_PASSWORD)
                .email(NEW_EMAIL)
                .build();
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();

        User user = User.builder()
                .id(USER_ID)
                .name(USER_NAME)
                .password(USER_PASSWORD)
                .email(USER_EMAIL)
                .build();

        given(userService.createUser(any(UserData.class))).willReturn(user);
    }

    @Nested
    @DisplayName("POST /user 요청은")
    class Describe_create {
        @Nested
        @DisplayName("등록되어 있는 user가 주어진다면")
        class Context_with_user {

            @Test
            @DisplayName("user을 저장하고 201을 응답한다")
            void it_return_status() throws Exception {
                UserData userData = getUserData();
                String userContent = objectMapper.writeValueAsString(userData);

                mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userContent))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(containsString(USER_NAME)));

                verify(userService).createUser(any(UserData.class));
            }
        }

        @Nested
        @DisplayName("user가 없다면")
        class Context_withOut_user {

            @Test
            @DisplayName("400을 응답한다")
            void it_return_status() throws Exception {
                UserData userData = UserData.builder()
                        .name(USER_NAME)
                        .build();
                String userContent = objectMapper.writeValueAsString(userData);

                mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userContent))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("PATCH /user/{id} 요청은")
    class Describe_patch {
        @Nested
        @DisplayName("id가 올바르면")
        class Context_with_id {

            @BeforeEach
            void setUp() {
                given(userService.updateUser(eq(1L), any(UserData.class)))
                        .will(invocation -> {
                            Long id = invocation.getArgument(0);
                            UserData userData = invocation.getArgument(1);
                            return User.builder()
                                    .id(id)
                                    .name(userData.getName())
                                    .password(userData.getPassword())
                                    .email(userData.getEmail())
                                    .build();
                        });
            }

            @Test
            @DisplayName("user을 수정하고 200을 응답한다")
            void it_return_status() throws Exception {
                UserData userData = updateUserData();
                String userContent = objectMapper.writeValueAsString(userData);

                mockMvc.perform(
                                patch("/users/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(userContent))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(NEW_NAME)));

                verify(userService).updateUser(eq(1L), any(UserData.class));
            }
        }

        @Nested
        @DisplayName("user의 id가 올바르지 않다면")
        class Context_withOut_id {

            @BeforeEach
            void setUp() {
                given(userService.updateUser(eq(1000L), any(UserData.class))).willThrow(new UserNotFoundException(1000L));
            }

            @Test
            @DisplayName("404을 응답한다")
            void it_return_status() throws Exception {
                UserData userData = updateUserData();
                String userContent = objectMapper.writeValueAsString(userData);

                mockMvc.perform(
                                patch("/users/1000")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(userContent))
                        .andExpect(status().isNotFound());

                verify(userService).updateUser(eq(1000L), any(UserData.class));
            }
        }
    }

    @Nested
    @DisplayName("DELETE /user/{id} 요청은")
    class Describe_delete {
        @Nested
        @DisplayName("user의 id가 올바르면")
        class Context_with_id {

            @Test
            @DisplayName("user를 삭제하고 204를 응답한다")
            void it_return_status() throws Exception {
                mockMvc.perform(
                                delete("/users/1"))
                        .andExpect(status().isNoContent());

                verify(userService).deleteUser(1L);
            }
        }

        @Nested
        @DisplayName("user의 id가 올바르지 않다면")
        class Context_withOut_id {

            @BeforeEach
            void setUp() {
                given(userService.deleteUser(1000L)).willThrow(new UserNotFoundException(1000L));
            }

            @Test
            @DisplayName("404을 응답한다")
            void it_return_status() throws Exception {
                mockMvc.perform(
                                delete("/users/1000"))
                        .andExpect(status().isNotFound());

                verify(userService).deleteUser(1000L);
            }
        }
    }
}
