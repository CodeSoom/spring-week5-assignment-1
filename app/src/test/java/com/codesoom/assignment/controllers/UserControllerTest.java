package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.exception.UserNotFoundException;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUpAll() {
        User userData1 = User.builder()
                .id(1L)
                .name("name")
                .email("email")
                .password("password")
                .build();

        given(userService.getUser(1L)).willReturn(userData1);

        given(userService.createUser(any(UserData.class))).will(invocation -> {
            UserData user = invocation.getArgument(0);
            return UserData.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .build();
        });
    }


    @Nested
    class Describe_create{

        @Nested
        @DisplayName("유효한 파라미터로 요청이 들어왔을 경우")
        class Context_with_valid_attributes{
            UserData validParam = UserData.builder()
                    .id(1L)
                    .name("validName")
                    .email("validEmail")
                    .password("validPassword")
                    .build();

            @Test
            @DisplayName("Created 상태를 응답한다.")
            void it_responses_Created_status() throws Exception {
                mockMvc.perform(
                        post("/user")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(validParam))
                )
                        .andExpect(status().isCreated())
                        .andExpect(content().string(containsString("validName")));

                verify(userService).createUser(any(UserData.class));
            }
        }

        @Nested
        @DisplayName("유효하지 못 파라미터로 요청이 들어왔을 경우")
        class Context_with_invalid_attributes{
            UserData invalidParam = UserData.builder()
                    .id(1L)
                    .name("validName")
                    .email("")
                    .password("")
                    .build();

            @Test
            @DisplayName("BadRequest 상태를 응답한다.")
            void it_responses_Created_status() throws Exception {
                mockMvc.perform(
                        post("/user")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(invalidParam))
                )
                        .andExpect(status().isBadRequest());
            }
        }
    }


    @Nested
    @DisplayName("update 메소드는")
    class Describe_update{

        @Nested
        @DisplayName("유효한 파라미터로 요청이 들어왔을 경우")
        class Context_with_valid_attributes{
            UserData validParam = UserData.builder()
                        .id(1L)
                        .name("validName")
                        .email("validEmail")
                        .password("validPassword")
                        .build();

            @Test
            @DisplayName("Ok 상태를 응답한다.")
            void it_responses_ok_status() throws Exception {
                mockMvc.perform(
                        post("/user/1")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(validParam))
                ).andExpect(status().isOk());

                verify(userService).updateUser(eq(1L), any(UserData.class));
            }
        }

        @Nested
        @DisplayName("유효하지 못한 파라미터로 요청이 왔을 경우")
        class Context_with_invalid_attributes{
            UserData invalidParam = UserData.builder()
                    .id(1L)
                    .name("validName")
                    .email("")
                    .password("")
                    .build();

            @Test
            @DisplayName("badRequest 상태를 응답한다.")
            void it_responses_badRequest_status() throws Exception {
                mockMvc.perform(
                        post("/user/1")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(invalidParam))
                ).andExpect(status().isBadRequest());
            }
        }
    }


    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete{

        @Nested
        @DisplayName("존재하는 사용자를 삭제 요청할 경우")
        class Context_with_existed_user{

            @Test
            @DisplayName("NoContent 상태를 응답한다.")
            void it_responses_NoContent_status() throws Exception {
                mockMvc.perform(
                        delete("/user/1")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 사용자를 삭제 요청할 경우")
        class Context_with_not_existed_user{
            Long invalidId = 100L;

            @BeforeEach
            void setUp() {
                given(userService.deleteUser(invalidId)).willThrow(new UserNotFoundException(100L));
            }

            @Test
            @DisplayName("NotFound 상태를 응답한다.")
            void it_responses_NotFound_status() throws Exception {
                mockMvc.perform(
                        delete("/user/100")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound());
            }
        }
    }

}
