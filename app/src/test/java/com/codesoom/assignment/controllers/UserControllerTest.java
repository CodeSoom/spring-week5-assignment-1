package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@DisplayName("UserController")
public class UserControllerTest {
    private static final String EXISTED_USER_NAME = "홍길동";
    private static final String EXISTED_USER_EMAIL = "hong@gmail.com";
    private static final String EXISTED_USER_PASSWORD = "password";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User existedUser;

    void prepareExistedUser() {
        User user = User.builder()
                .name(EXISTED_USER_NAME)
                .email(EXISTED_USER_EMAIL)
                .password(EXISTED_USER_PASSWORD)
                .build();
        existedUser = userRepository.save(user);
    }

    @Nested
    @DisplayName("POST /users 는")
    class Describe_post_users {
        @Nested
        @DisplayName("유저가 주어진다면")
        class Context_with_user {
            private final String NEW_USER_NAME = "new_홍길동";
            private final String NEW_USER_EMAIL = "new_hong@gmail.com";
            private final String NEW_USER_PASSWORD = "new_password";

            String requestContent;
            User newUser;

            @BeforeEach
            void prepareNewUser() throws JsonProcessingException {
                newUser = User.builder()
                        .name(NEW_USER_NAME)
                        .email(NEW_USER_EMAIL)
                        .password(NEW_USER_PASSWORD)
                        .build();

                requestContent = objectMapper.writeValueAsString(newUser);
            }

            @Test
            @DisplayName("유저를 생성하고, 생성한 유저를 응답한다.")
            void it_responses_new_user() throws Exception {
                mockMvc.perform(post("/users").content(requestContent)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(containsString(newUser.getName())));
            }
        }
    }

    @Nested
    @DisplayName("PATCH /users/{id}")
    class Describe_patch_users_id {
        @Nested
        @DisplayName("등록된 유저의 id와 변경할 유저 정보가 주어진다면")
        class Context_with_existed_user {
            private final String UPDATE_USER_NAME = "update_홍길동";
            private final String UPDATE_USER_EMAIL = "update_hong@gmail.com";
            private final String UPDATE_USER_PASSWORD = "update_password";

            String requestContent;
            User updateUser;

            @BeforeEach
            void prepare() {
                prepareExistedUser();
            }

            @BeforeEach
            void prepareUpdateUser() throws JsonProcessingException {
                updateUser = User.builder()
                        .name(UPDATE_USER_NAME)
                        .email(UPDATE_USER_EMAIL)
                        .password(UPDATE_USER_PASSWORD)
                        .build();

                requestContent = objectMapper.writeValueAsString(updateUser);
            }

            @Test
            @DisplayName("해당 id의 유저를 주어진 유저 정보로 변경하고, 변경된 유저를 응답한다.")
            void it_responses_updated_user() throws Exception {
                mockMvc.perform(patch("/users/" + existedUser.getId())
                                .content(requestContent)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(updateUser.getName())));
            }
        }
    }

    @Nested
    @DisplayName("DELETE /users/{id}")
    class Describe_delete_users_id {
        @Nested
        @DisplayName("등록된 유저의 id가 주어진다면")
        class Context_with_existed_user_id {
            @BeforeEach
            void prepare() {
                prepareExistedUser();
            }

            @Test
            @DisplayName("해당 id의 유저를 삭제한다.")
            void it_responses_no_content() throws Exception {
                mockMvc.perform(delete("/users/" + existedUser.getId()))
                        .andExpect(status().isNoContent());

                assertThatThrownBy(() -> userRepository.findById(existedUser.getId()))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
