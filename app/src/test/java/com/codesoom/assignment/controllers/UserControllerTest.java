package com.codesoom.assignment.controllers;

import com.codesoom.assignment.exception.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserCreateRequestDto;
import com.codesoom.assignment.dto.UserDto;
import com.codesoom.assignment.dto.UserUpdateRequestDto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private final long EXIST_ID = 1L;
    private final long NOT_EXIST_ID = 100L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    private User user;

    private UserCreateRequestDto validUpdateUserDto;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(EXIST_ID)
                .name("양승인")
                .password("1234")
                .email("rhfpdk92@naver.com")
                .build();

        validUpdateUserDto = UserCreateRequestDto.builder()
                .name("양철수")
                .email("newId@naver.com")
                .password("12341234")
                .build();

        userDto = UserDto.builder()
                .id(EXIST_ID)
                .name("양철수")
                .email("newId@naver.com")
                .password("12341234")
                .build();
    }

    @Nested
    @DisplayName("POST /user 요청은")
    class Describe_createUser {
        @Nested
        @DisplayName("생성할 회원의 정보가 있으면")
        class Context_exist_user {
            @BeforeEach
            void setUp() {
                given(userService.createUser(any(UserCreateRequestDto.class)))
                        .willReturn(userDto);
            }

            @Test
            @DisplayName("응답코드는 201이며 생성한 회원을 응답한다.")
            void it_return_createdUser() throws Exception {
                mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("name").exists());
            }
        }

        @Nested
        @DisplayName("생성할 회원의 정보가 없으면")
        class Context_does_not_exist_user {
            @Test
            @DisplayName("응답코드는 400을 응답한다")
            void it_return_bad_request() throws Exception {
                mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("사용자의 이름이 없으면")
        class Context_user_does_not_have_parameter {
            User userWithoutName = User.builder()
                    .id(EXIST_ID)
                    .password("1234")
                    .email("rhfpdk92@naver.com")
                    .build();

            @Test
            @DisplayName("응답코드는 400이며 에러메세지를 응답한다.")
            void it_return_createdUser() throws Exception {
                mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userWithoutName)))
                        .andDo(print())
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("PATCH /users/{id} 요청은")
    class Describe_updateUser {
        @Nested
        @DisplayName("id가 존재하고 변경할 회원의 정보가 모두 있으면")
        class Context_exist_id_and_userdto {
            @BeforeEach
            void setUp() {
                given(userService.updateUser(eq(EXIST_ID), any(UserUpdateRequestDto.class)))
                        .willReturn(userDto);
            }

            @Test
            @DisplayName("응답코드는 200이며 수정된 회원을 응답한다.")
            void it_return_updatedUser() throws Exception {
                mockMvc.perform(patch("/users/{id}", EXIST_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(validUpdateUserDto)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("name").value("양철수"))
                        .andExpect(jsonPath("email").value("newId@naver.com"))
                        .andExpect(jsonPath("password").value("12341234"));
            }
        }

        @Nested
        @DisplayName("id가 존재하지 않으면")
        class Context_does_not_exist_user {

            @BeforeEach
            void setUp() {
                given(userService.updateUser(eq(NOT_EXIST_ID), any(UserUpdateRequestDto.class)))
                        .willThrow(new UserNotFoundException(NOT_EXIST_ID));
            }

            @Test
            @DisplayName("응답코드는 404이며 에러메세지를 응답한다")
            void it_return_not_found() throws Exception {
                mockMvc.perform(patch("/users/{id}", NOT_EXIST_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(validUpdateUserDto)))
                        .andDo(print())
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("존재하는 회원이나 사용자의 이름없이 요청하면")
        class Context_user_does_not_have_parameter {
            User userWithoutName = User.builder()
                    .id(EXIST_ID)
                    .password("1234")
                    .email("rhfpdk92@naver.com")
                    .build();

            @Test
            @DisplayName("응답코드는 400이며 에러메세지를 응답한다.")
            void it_return_createdUser() throws Exception {
                mockMvc.perform(patch("/users/{id}", EXIST_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userWithoutName)))
                        .andDo(print())
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("DELETE /users/{id} 요청은")
    class Describe_deleteUser {
        @Nested
        @DisplayName("id가 존재하는 회원이면")
        class Context_exist_id {
            @Test
            @DisplayName("응답코드 204를 응답한다.")
            void it_return_no_content() throws Exception {
                mockMvc.perform(delete("/users/{id}", EXIST_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("id가 존재하지 않는 회원이면")
        class Context_does_not_exist_id {
            @BeforeEach
            void setUp() {
                willThrow(new UserNotFoundException(NOT_EXIST_ID)).given(userService).deleteUser(NOT_EXIST_ID);
            }

            @Test
            @DisplayName("응답코드 404이며 id가 존재하지 않는다는 메세지를 응답한다.")
            void it_return_no_content() throws Exception {
                mockMvc.perform(delete("/users/{id}", NOT_EXIST_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("message").value("User not found: " + NOT_EXIST_ID));
            }
        }
    }
}
