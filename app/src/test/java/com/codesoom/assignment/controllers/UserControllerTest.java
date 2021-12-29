package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserTestCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {
    // 회원 생성하기 - POST /user
    // 회원 수정하기 - POST /user/{id}
    // 회원 삭제하기 - DELETE /user/{id}

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private List<User> users;
    private User user0;
    private User user1;
    private String contentUsers;
    private String contentUser;

    @BeforeEach
    void setUp() throws JsonProcessingException {

        users = UserTestCase.getTestUsers(2);
        user0 = users.get(0);
        user1 = users.get(1);

        contentUsers = objectMapper.writeValueAsString(users);
        contentUser = objectMapper.writeValueAsString(user0);
    }
    

    @Nested
    @DisplayName("POST /user")
    class Describe_request_post_to_user_path {

        @BeforeEach
        void setUp() {
            given(userService.createUser(any(User.class))).willReturn(user0);
        }

        @Test
        @DisplayName("생성한 user를 응답합니다.")
        void it_responses_created_user() throws Exception {
            mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(contentUser))
                    .andExpect(status().isCreated())
                    .andExpect(content().string(contentUser));
        }
    }


}
