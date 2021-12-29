package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserTestCase;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
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
            given(userService.createUser(any(UserData.class))).willReturn(user0);
        }

        @Test
        @DisplayName("생성한 user를 응답합니다.")
        void it_responses_created_user() throws Exception {
            mockMvc.perform(post("/user")
                            .accept(MediaType.APPLICATION_JSON_UTF8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(contentUser))
                    .andExpect(status().isCreated())
                    .andExpect(content().string(contentUser));
        }
    }

    @Nested
    @DisplayName("POST /user/{id}")
    class Describe_request_post_to_users_id_path {

        @Nested
        @DisplayName("만약 조회하는 id의 user가 존재한다면")
        class Context_with_exist_id {

            private final Long existId = 0L;

            @BeforeEach
            void setUp() {
                given(userService.updateUser(eq(existId), any(UserData.class))).willReturn(user0);
            }

            @Test
            @DisplayName("수정된 user를 응답합니다.")
            void it_responses_updated_user() throws Exception {
                mockMvc.perform(post("/user/" + existId)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(contentUser))
                        .andExpect(status().isOk())
                        .andExpect(content().string(contentUser));
            }
        }

        @Nested
        @DisplayName("만약 조회하는 id의 user가 존재하지 않는다면")
        class Context_with_not_exist_id {

            private final Long notExistId = 100L;

            @BeforeEach
            void setUp() {
                given(userService.updateUser(eq(notExistId), any(UserData.class))).willThrow(new UserNotFoundException(notExistId));
            }

            @Test
            @DisplayName("NOT_FOUND(404) 상태를 응답합니다.")
            void it_responses_404() throws Exception {
                mockMvc.perform(post("/user/" + notExistId)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(contentUser))
                        .andExpect(status().isNotFound());
            }
        }
    }

}
