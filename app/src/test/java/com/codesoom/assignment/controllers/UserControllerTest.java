package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.Mapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//        회원 생성하기 - POST /user
//        회원 수정하기 - POST /user/{id}
//        회원 삭제하기 - DELETE /user/{id}
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper dozerMapper;

    @MockBean
    UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("양승인")
                .password("1234")
                .email("rhfpdk92@naver.com")
                .build();
    }

    @Nested
    @DisplayName("POST /user 요청은")
    class Describe_createUser {
        @Nested
        @DisplayName("requestbody에 user가 있으면")
        class Context_exist_user {
            @BeforeEach
            void setUp() {
                given(userService.createUser(any(UserDto.class)))
                        .willReturn(dozerMapper.map(user,UserDto.class));
            }

            @Test
            @DisplayName("응답코드는 201이며 생성한 유저를 응답한다.")
            void createUser() throws Exception {
                mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("name").exists());
            }
        }

        @Nested
        @DisplayName("requestbody에 user가 없으면")
        class Context_does_not_exist_user {
            @Test
            @DisplayName("응답코드는 400을 응답한다")
            void it_return_bad_request() throws Exception {
                mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isBadRequest());
            }
        }
    }
}
