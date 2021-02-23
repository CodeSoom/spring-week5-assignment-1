package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
@DisplayName("UserController 테스트")
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {
        @Nested
        @DisplayName("만약 유저 객체가 주어진다면")
        class Context_WithUser {
            @Test
            @DisplayName("객체를 저장하고 저장된 객체와 Created를 리턴한다")
            void itSavesUserAndReturnsUser() throws Exception {
                User user = new User(1L, "paik", "melon", "1234");

                mockMvc.perform(post("/users"))
                        .andExpect(jsonPath("id").value(1L))
                        .andExpect(jsonPath("name").value("paik"))
                        .andExpect(jsonPath("mail").value("melon"))
                        .andExpect(jsonPath("password").value("1234"))
                        .andExpect(status().isOk());
            }
        }
    }
}