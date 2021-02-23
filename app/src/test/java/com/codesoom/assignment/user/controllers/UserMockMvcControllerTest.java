package com.codesoom.assignment.user.controllers;

import com.codesoom.assignment.product.application.ProductNotFoundException;
import com.codesoom.assignment.user.application.UserNotFoundException;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
                UserResponseDto product = getUserResponse();

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

    @Nested
    @DisplayName("GET /users/{id} 는")
    class Describe_getUser {

        @Nested
        @DisplayName("등록된 사용자가 없으면")
        class Context_without_user {

            @BeforeEach
            void setUp() {
                given(userService.getUser(eq(NOT_EXIST_ID)))
                        .willThrow(new UserNotFoundException(NOT_EXIST_ID));
            }

            @DisplayName("404 상태코드, Not Found 상태를 응답한다.")
            @Test
            void It_responds_not_found() throws Exception {
                mockMvc.perform(get("/users/{id}", NOT_EXIST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("등록된 사용자가 있으면")
        class Context_with_user {
            UserResponseDto responseDto;

            @BeforeEach
            void setUp() {
                responseDto = getUserResponse();
                given(userService.getUser(anyLong())).willReturn(responseDto);
            }

            @DisplayName("200 상태코드, OK 상태와 찾고자 하는 사용자를 응답한다.")
            @Test
            void it_responds_ok_with_user() throws Exception {
                mockMvc.perform(get("/users/{id}", anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string(objectMapper.writeValueAsString(responseDto)))
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("name").exists())
                        .andExpect(jsonPath("email").exists());
            }
        }
    }

    @Nested
    @DisplayName("Patch /users/{id} 는")
    class Describe_updateUser {
        UserUpdateRequestDto requestDto;

        @Nested
        @DisplayName("갱신할 사용자가 없으면")
        class Context_without_user {

            @BeforeEach
            void setUp() {
                requestDto = UserUpdateRequestDto.builder()
                        .name(NAME)
                        .email(EMAIL)
                        .password(PRICE)
                        .build();
                given(userService.updateUser(eq(NOT_EXIST_ID), any(UserNotFoundException.class)))
                        .willThrow(new ProductNotFoundException(NOT_EXIST_ID));
            }

            @DisplayName("404 상태코드, Not Found 상태를 응답한다.")
            @Test
            void It_responds_not_found() throws Exception {
                mockMvc.perform(patch("/users/{id}", NOT_EXIST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto)))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("갱신할 사용자가 존재하면")
        class Context_with_product {

            @BeforeEach
            void setUp() {
                requestDto = new ProductUpdateRequestDto(NAME, MAKER, PRICE, IMAGE_URL);
                UserResponseDto responseDto = getUserResponse();
                given(userService.updateProduct(anyLong(), any(UserUpdateRequestDto.class)))
                        .willReturn(responseDto);
            }

            @DisplayName("200 상태코드, OK 상태와 갱신된 사용자 정보를 응답한다.")
            @Test
            void It_responds_product_id() throws Exception {
                mockMvc.perform(patch("/users/{id}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("name").exists())
                        .andExpect(jsonPath("email").exists());
            }
        }
    }

    private UserResponseDto getUserResponse() {
        return UserResponseDto.builder()
                .id(USER_ID)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();
    }
}
