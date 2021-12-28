package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@WebMvcTest(UserController.class)
@DisplayName("UserController 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Nested
    class 회원을_저장하는_핸들러는 {
        @Nested
        class 유효한_회원_파라미터인_경우 {

            @Test
            void 회원을_저장한다() throws Exception {
                mockMvc.perform(post("/users", "{name:\"test\", email:\"test@naver.com\", password:\"1234\"}"))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        class 유효하지_않는_회원_파라미터인_경우 {
            @Test
            void 예외코드를_보낸다() {

            }
        }
    }
}