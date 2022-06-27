package com.codesoom.assignment.controllers;

import com.codesoom.assignment.BadRequestException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserCreateData;
import com.codesoom.assignment.dto.UserUpdateData;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    private static final Long ID = 1L;
    private static final Long INVALID_ID = 1000L;
    private static final String NAME = "김철수";
    private static final String INVALID_NAME = "";
    private static final String NEW_NAME = "김영희";
    private static final String EMAIL = "kim@gmail.com";
    private static final String PASSWORD = "1111";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;
    private UserCreateData userCreateData;
    private UserUpdateData userUpdateData;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        userCreateData = UserCreateData.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        userUpdateData = UserUpdateData.builder()
                .name(NEW_NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    @Nested
    @DisplayName("POST /user 요청 시")
    class Describe_post_user {
        @Nested
        @DisplayName("유효한 속성을 가진 user가 주어졌을 경우")
        class Context_if_user_with_valid_attributes_given {
            @BeforeEach
            void setUp() {
                given(userService.create(any(UserCreateData.class))).willReturn(user);
            }

            @Nested
            @DisplayName("status code 201을 응답한다")
            class It_response_status_code_201 {
                ResultActions subject() throws Exception {
                    return mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(userCreateData))
                    );
                }

                @Test
                void test() throws Exception {
                    subject().andExpect(status().isCreated())
                            .andExpect(jsonPath("$.id").value(ID))
                            .andExpect(jsonPath("$.name").value(NAME))
                            .andExpect(jsonPath("$.email").value(EMAIL))
                            .andExpect(jsonPath("$.password").value(PASSWORD));
                }
            }
        }

        @Nested
        @DisplayName("유효하지 않은 속성을 가진 user가 주어졌을 경우")
        class Context_if_user_with_invalid_attributes_given {
            @BeforeEach
            void setUp() {
                userCreateData = UserCreateData.builder()
                        .name(INVALID_NAME)
                        .email(EMAIL)
                        .password(PASSWORD)
                        .build();

                given(userService.create(eq(userCreateData))).willThrow(new BadRequestException());
            }

            @Nested
            @DisplayName("status code 400을 응답한다")
            class It_response_status_code_400 {
                ResultActions subject() throws Exception {
                    return mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(userCreateData))
                    );
                }

                @Test
                void test() throws Exception {
                    subject().andExpect(status().isBadRequest());
                }
            }
        }
    }

    @Nested
    @DisplayName("PATCH /user 요청 시")
    class Describe_patch_user {
        @Nested
        @DisplayName("유효한 id가 주어졌을 경우")
        class Context_if_valid_id_given {
            @BeforeEach
            void setUp() {
                given(userService.update(eq(ID), any(UserUpdateData.class)))
                        .will(invocation -> {
                            Long id = invocation.getArgument(0);
                            UserUpdateData userUpdateData = invocation.getArgument(1);

                            return User.builder()
                                    .id(id)
                                    .name(userUpdateData.getName())
                                    .email(userUpdateData.getEmail())
                                    .password(userUpdateData.getPassword())
                                    .build();
                        });
            }

            @Nested
            @DisplayName("status code 200을 응답한다")
            class It_response_status_code_200{
                ResultActions subject() throws Exception {
                    return mockMvc.perform(patch("/users/{id}", ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(userUpdateData))
                    );
                }

                @Test
                void test() throws Exception {
                    subject().andExpect(status().isOk())
                            .andExpect(jsonPath("$.name").value(NEW_NAME));
                }
            }
        }

        @Nested
        @DisplayName("유효하지 않은 id가 주어졌을 경우")
        class Context_if_invalid_id_given {
            @BeforeEach
            void setUp() {
                given(userService.update(eq(INVALID_ID), any(UserUpdateData.class))).willThrow(new UserNotFoundException(INVALID_ID));
            }

            @Nested
            @DisplayName("status code 404를 응답한다")
            class It_response_status_code_404 {
                ResultActions subject() throws Exception {
                    return mockMvc.perform(patch("/users/{id}", INVALID_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(userUpdateData))
                    );
                }

                @Test
                void test() throws Exception {
                    subject().andExpect(status().isNotFound());
                }
            }
        }

        @Nested
        @DisplayName("유효하지 않은 속성을 가진 user가 주어졌을 경우")
        class Context_if_user_with_invalid_attributes_given {
            @BeforeEach
            void setUp() {
                userUpdateData = UserUpdateData.builder()
                        .name(INVALID_NAME)
                        .email(EMAIL)
                        .password(PASSWORD)
                        .build();

                given(userService.update(eq(ID), eq(userUpdateData))).willThrow(new BadRequestException());
            }

            @Nested
            @DisplayName("status code 400을 응답한다")
            class It_response_status_code_400 {
                ResultActions subject() throws Exception {
                    return mockMvc.perform(patch("/users/{id}", ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(userUpdateData))
                    );
                }

                @Test
                void test() throws Exception {
                    subject().andExpect(status().isBadRequest());
                }
            }
        }
    }

    @Nested
    @DisplayName("DELETE /user 요청 시")
    class Describe_delete_user {
        @Nested
        @DisplayName("유효한 id가 주어졌을 경우")
        class Context_if_valid_id_given {
            @Nested
            @DisplayName("status code 204를 응답한다")
            class It_response_status_code_204{
                ResultActions subject() throws Exception {
                    return mockMvc.perform(delete("/users/{id}", ID));
                }

                @Test
                void test() throws Exception {
                    subject().andExpect(status().isNoContent());

                    verify(userService).delete(ID);
                }
            }
        }

        @Nested
        @DisplayName("유효하지 않은 id가 주어졌을 경우")
        class Context_if_invalid_id_given {
            @BeforeEach
            void setUp() {
                given(userService.delete(INVALID_ID)).willThrow(new UserNotFoundException(INVALID_ID));
            }

            @Nested
            @DisplayName("status code 404를 응답한다")
            class It_response_status_code_404 {
                ResultActions subject() throws Exception {
                    return mockMvc.perform(delete("/users/{id}", INVALID_ID));
                }

                @Test
                void test() throws Exception {
                    subject().andExpect(status().isNotFound());
                }
            }
        }
    }

    private String toJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(object);
    }
}
