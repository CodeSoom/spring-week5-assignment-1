package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.exception.UserNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("UserController")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper;

    private final Long VALID_ID = 1L;
    private final Long INVALID_ID = 9999L;

    private User correct_user;
    private String content_correct_user;
    private User invalid_user;
    private String content_invalid_user;


    @BeforeEach
    void setUp() throws JsonProcessingException {
        objectMapper = new ObjectMapper();

        correct_user = new User(VALID_ID, "이름1", "패스워드1", "이메일1");
        content_correct_user = objectMapper.writeValueAsString(correct_user);
        invalid_user = User.builder().name("이름1").build();
        content_invalid_user = objectMapper.writeValueAsString(invalid_user);

        given(userService.create(any(User.class))).will(invocation -> {
            User user = invocation.getArgument(0);
            return objectMapper.writeValueAsString(user);
        });

        given(userService.update(eq(VALID_ID), any(User.class))).will(invocation -> {
            User user = invocation.getArgument(1);
            user.setId(VALID_ID);
            return objectMapper.writeValueAsString(user);
        });

        given(userService.update(eq(INVALID_ID), any(User.class)))
                .willThrow(new UserNotFoundException(INVALID_ID));

        willDoNothing().given(userService).delete(VALID_ID);
        willThrow(new UserNotFoundException(INVALID_ID)).given(userService).delete(INVALID_ID);
    }

    @Nested
    @DisplayName("POST /user 호출")
    class Describe_POST_user {

        @Nested
        @DisplayName("올바른 데이터를 전달하면")
        class Context_with_correct_data {

            @Test
            @DisplayName("response(status: created, data: new user)를 반환합니다.")
            void it_response_ok() throws Exception {
                mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content_correct_user))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(content_correct_user));
            }
        }

        @Nested
        @DisplayName("유효하지 않은 데이터를 전달하면")
        class Context_with_invalid_data {

            @Test
            @DisplayName("response(status: bad request)를 반환합니다.")
            void it_response_bad_request() throws Exception {
                mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content_invalid_user))
                        .andExpect(status().isBadRequest());
            }
        }

    }

    @Nested
    @DisplayName("PATCH /user/{id} 호출")
    class Describe_PATCH_user_id {

        @Nested
        @DisplayName("저장된 유저 id를 전달하면")
        class Context_with_existing_user {

            @Test
            @DisplayName("response(status: ok, data: update user)를 반환합니다.")
            void it_response_ok() throws Exception {
                mockMvc.perform(patch("/user/" + VALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content_correct_user))
                        .andExpect(status().isOk())
                        .andExpect(content().string(content_correct_user));
            }
        }

        @Nested
        @DisplayName("저장되지 않은 유저 id를 전달하면")
        class Context_with_not_existing_user {

            @Test
            @DisplayName("response(status: not found)를 반환합니다.")
            void it_response_not_found() throws Exception {
                mockMvc.perform(patch("/user/" + INVALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content_correct_user))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("유효하지 않은 데이터를 전달하면")
        class Context_with_invalid_data {

            @Test
            @DisplayName("response(status: bad request)를 반환합니다.")
            void it_response_bad_request() throws Exception {
                mockMvc.perform(patch("/user/" + VALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content_invalid_user))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("DELETE /user/{id} 호출")
    class Describe_DELETE_user_id {
        @Nested
        @DisplayName("저장된 유저 id를 전달하면")
        class Context_with_existing_user {

            @Test
            @DisplayName("response(status: no content, data: update user)를 반환합니다.")
            void it_response_ok() throws Exception {
                mockMvc.perform(delete("/user/" + VALID_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("저장되지 않은 유저 id를 전달하면")
        class Context_with_not_existing_user {

            @Test
            @DisplayName("response(status: not found)를 반환합니다.")
            void it_response_not_found() throws Exception {
                mockMvc.perform(delete("/user/" + INVALID_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }
        }
    }
}