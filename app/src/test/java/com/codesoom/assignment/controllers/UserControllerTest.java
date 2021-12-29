// 1. POST /users -> 회원 생성
// 2. PATCH /users/{id} -> 회원 수정
// 3. DELETE /users/{id} -> 회원 삭제

package com.codesoom.assignment.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserController 테스트")
@WebMvcTest(UserController.class) // 스프링부트 전체를 띄우지 않고 필요한 것만 사용
class UserControllerTest {
    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class createUser_메소드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 필수_파라메타가_모두_있다면 {

            @Test
            @DisplayName("새로운 회원을 생성한다")
            void 새로운_회원을_생성한다() throws Exception {
                mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\": \"codesoom\"," +
                                        "\"email\": \"test@test.com\"," +
                                        " \"password\": \"asdqwe1234\"}"
                                )
                )
                        .andExpect(status().isCreated())
                        .andExpect(content().string(containsString("test@test.com")));
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 필수_파라메타가_모두_없다면 {

            @Test
            @DisplayName("Bad request 를 응답한다")
            void Bad_request를_응답한다() throws Exception {
                mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\": \"\"," +
                                        "\"email\": \"\"," +
                                        " \"password\": \"asdqwe1234\"}"
                                )
                )
                        .andExpect(status().isBadRequest());
            }
        }
    }
}