package com.codesoom.assignment.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TODO:
 * 테스트 할 목록
 * 회원 생성하기 - POST /users
 * 회원 수정하기 - PATCH /users/{id}
 * 회원 삭제하기 - DELETE /users/{id}
 */

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    /**
     * 유저를 생성할 시,
     * 이름, 이메일, 비밀번호는 필수로 받아야한다.
     * @throws Exception
     */

    @Test
    void createUser() throws Exception {
        mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"메이커1\",\"email\":\"maker1@gmail.com\"\"password\":maker11}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("메이커1")))
                .andExpect(content().string(containsString("maker1@gmail.com")))
                .andExpect(content().string(containsString("maker11")));
    }
}