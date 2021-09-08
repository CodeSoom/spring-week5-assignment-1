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

    private User correctUser;
    private User blankNameUser;
    private User blankPasswordUser;

    @BeforeEach
    void setUp()  {
        objectMapper = new ObjectMapper();

        correctUser = new User(VALID_ID, "이름1", "패스워드1", "이메일1");
        blankNameUser = User.builder().name("").password("패스워드1").build();
        blankPasswordUser = User.builder().name("이름1").password("").build();

        given(userService.create(any(User.class))).will(invocation -> {
            return invocation.getArgument(0);
        });

        given(userService.update(eq(VALID_ID), any(User.class))).will(invocation -> {
            User user = invocation.getArgument(1);
            user.setId(VALID_ID);
            return user;
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
            @DisplayName("status: Created, data: new user를 응답합니다.")
            void it_response_ok() throws Exception {
                mockMvc.perform(post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeContentFromUser(correctUser)))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(makeContentFromUser(correctUser)));
            }
        }

        @Nested
        @DisplayName("이름이 비어있는 유저 데이터를 전달하면")
        class Context_with_blank_name_user {

            @Test
            @DisplayName("status: Bad request를 응답합니다.")
            void it_response_bad_request() throws Exception {
                mockMvc.perform(post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeContentFromUser(blankNameUser)))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("패스워스가 비어있는 유저 데이터를 전달하면")
        class Context_with_blank_password_user {

            @Test
            @DisplayName("status: Bad request를 응답합니다.")
            void it_response_bad_request() throws Exception {
                mockMvc.perform(post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeContentFromUser(blankPasswordUser)))
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
            @DisplayName("status: Ok, data: updated user를 응합니다.")
            void it_response_ok() throws Exception {
                mockMvc.perform(patch("/users/" + VALID_ID)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeContentFromUser(correctUser)))
                        .andExpect(status().isOk())
                        .andExpect(content().string(makeContentFromUser(correctUser)));
            }
        }

        @Nested
        @DisplayName("저장되지 않은 유저 id를 전달하면")
        class Context_with_not_existing_user {

            @Test
            @DisplayName("status: Not found를 응답합니다.")
            void it_response_not_found() throws Exception {
                mockMvc.perform(patch("/users/" + INVALID_ID)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeContentFromUser(correctUser)))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("이름이 비어있는 유저 데이터를 전달하면")
        class Context_with_blank_name_user {

            @Test
            @DisplayName("status: Bad request를 응답합니다.")
            void it_response_bad_request() throws Exception {
                mockMvc.perform(post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeContentFromUser(blankNameUser)))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("패스워스가 비어있는 유저 데이터를 전달하면")
        class Context_with_blank_password_user {

            @Test
            @DisplayName("status: Bad request를 응답합니다.")
            void it_response_bad_request() throws Exception {
                mockMvc.perform(post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeContentFromUser(blankPasswordUser)))
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
            @DisplayName("status: No content, data: updated user를 응답합니다.")
            void it_response_ok() throws Exception {
                mockMvc.perform(delete("/users/" + VALID_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("저장되지 않은 유저 id를 전달하면")
        class Context_with_not_existing_user {

            @Test
            @DisplayName("status: Not found를 응답합니다.")
            void it_response_not_found() throws Exception {
                mockMvc.perform(delete("/users/" + INVALID_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }
        }
    }

    private String makeContentFromUser(User user) throws JsonProcessingException {
        return objectMapper.writeValueAsString(user);
    }
}