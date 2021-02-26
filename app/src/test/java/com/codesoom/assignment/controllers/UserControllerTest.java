package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id(1L)
                .name("mikekang")
                .email("test@github.com")
                .password("qwer1234")
                .build();
    }
//
//    @Test
//    void list() throws Exception{
//        mockMvc.perform(
//                get("/users")
//                    .accept(MediaType.APPLICATION_JSON_UTF8)
//        )
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("mikekang")));
//    }
}