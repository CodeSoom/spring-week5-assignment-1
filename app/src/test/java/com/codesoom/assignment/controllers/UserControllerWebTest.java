package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserDto;
import com.codesoom.assignment.exception.NotFoundUserException;
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
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("UserController 클래스 Web")
class UserControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper;

    final private Long EXISTENT_ID = 1L;
    final private Long NON_EXISTENT_ID = -1L;
    private UserDto sampleUserDto;
    private UserDto updateUserDto;

    private UserDto blankNameUserDto;
    private UserDto blankEmailUserDto;
    private UserDto blankPasswordUserDto;

    private User sampleUser;
    private User updatedUser;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();

        sampleUserDto = UserDto.builder()
                .id(EXISTENT_ID)
                .name("name1")
                .email("email1")
                .password("password1")
                .build();
        sampleUser = User.builder()
                .id(sampleUserDto.getId())
                .name(sampleUserDto.getName())
                .email(sampleUserDto.getEmail())
                .password(sampleUserDto.getPassword())
                .build();
        updateUserDto = UserDto.builder()
                .name("updatedName")
                .email("updatedEmail")
                .password("updatedPassword")
                .build();
        blankNameUserDto = UserDto.builder()
                .id(sampleUserDto.getId())
                .email(sampleUserDto.getEmail())
                .password(sampleUserDto.getPassword())
                .build();
        blankEmailUserDto = UserDto.builder()
                .id(sampleUserDto.getId())
                .name(sampleUserDto.getName())
                .password(sampleUserDto.getPassword())
                .build();
        blankPasswordUserDto = UserDto.builder()
                .id(sampleUserDto.getId())
                .name(sampleUserDto.getName())
                .email(sampleUserDto.getEmail())
                .build();
        updatedUser = User.builder()
                .id(updateUserDto.getId())
                .name(updateUserDto.getName())
                .email(updateUserDto.getEmail())
                .password(updateUserDto.getPassword())
                .build();
    }

    @Nested
    @DisplayName("POST /users 요청시")
    class Describe_of_POST_users {

        private String givenUrl;

        @BeforeEach
        void setup() {
            givenUrl = "/users";
        }

        @Nested
        @DisplayName("유효한 유저 정보를 전달하면")
        class Context_of_valid_user_data {

            private ResultActions response;

            @BeforeEach
            void sendRequest() throws Exception {
                given(userService.createUser(any(UserDto.class)))
                        .willReturn(sampleUser);

                response = mockMvc.perform(
                        post(givenUrl)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(sampleUserDto)));
            }

            @Test
            @DisplayName("유저를 생성하고, 201 상태코드와 생성한 유저를 응답한다")
            void it_creates_and_returns_user() throws Exception {
                response.andExpect(status().isCreated())
                        .andExpect(content().string(containsString(sampleUser.getName())));
            }
        }

        @Nested
        @DisplayName("이름이 비어있는 유저 정보를 전달하면")
        class Context_of_blank_name {

            private ResultActions response;

            @BeforeEach
            void sendRequest() throws Exception {
                response = mockMvc.perform(
                        post(givenUrl)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(blankNameUserDto)));
            }

            @Test
            @DisplayName("400 상태코드를 응답한다")
            void it_creates_and_returns_user() throws Exception {
                response.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("이메일이 비어있는 유저 정보를 전달하면")
        class Context_of_blank_email {

            private ResultActions response;

            @BeforeEach
            void sendRequest() throws Exception {
                response = mockMvc.perform(
                        post(givenUrl)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(blankEmailUserDto)));
            }

            @Test
            @DisplayName("400 상태코드를 응답한다")
            void it_creates_and_returns_user() throws Exception {
                response.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("비밀번호가 비어있는 유저 정보를 전달하면")
        class Context_of_blank_password {

            private ResultActions response;

            @BeforeEach
            void sendRequest() throws Exception {
                response = mockMvc.perform(
                        post(givenUrl)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(blankPasswordUserDto)));
            }

            @Test
            @DisplayName("400 상태코드를 응답한다")
            void it_creates_and_returns_user() throws Exception {
                response.andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("PATCH /users/{id} 요청시")
    class Describe_PATCH_users {

        private String givenUrl;

        @BeforeEach
        void setup() {
            givenUrl = "/users/";
        }

        @Nested
        @DisplayName("존재하지 않는 유저의 id를 전달하면")
        class Context_non_existent_id {

            private ResultActions response;

            @BeforeEach
            void sendRequest() throws Exception {
                givenUrl += NON_EXISTENT_ID;
                given(userService.updateUser(eq(NON_EXISTENT_ID), any(UserDto.class)))
                        .willThrow(NotFoundUserException.class);

                response = mockMvc.perform(
                        patch(givenUrl)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateUserDto)));
            }

            @Test
            @DisplayName("404 상태 코드를 응답한다")
            void it_responses_404() throws Exception {
                response.andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("존재하는 유저 id와")
        class Context_existent_id {

            @BeforeEach
            void setUrlWithExistentId() {
                givenUrl += EXISTENT_ID;
            }

            @Nested
            @DisplayName("모든 속성이 기입된 유저 정보를 전달하면")
            class Context_of_filled_all_attributes {

                private UserDto givenUserDto;
                private ResultActions response;

                @BeforeEach
                void setup() throws Exception {
                    givenUserDto = updateUserDto;
                    given(userService.updateUser(eq(EXISTENT_ID), any(UserDto.class)))
                            .willReturn(updatedUser);

                    response = mockMvc.perform(
                            patch(givenUrl)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(givenUserDto)));
                }

                @Test
                @DisplayName("유저를 갱신하고, 갱신한 유저를 응답한다")
                void it_updates_user_and_response_200_and_updated_user() throws Exception {
                    response.andExpect(status().isOk())
                            .andExpect(content().string(containsString(updatedUser.getName())));
                }
            }

            @Nested
            @DisplayName("이메일이 비어있는 유저 데이터를 전달하면")
            class Context_of_blank_email {

                private UserDto givenUserDto;
                private ResultActions response;

                @BeforeEach
                void setup() throws Exception {
                    givenUserDto = blankEmailUserDto;
                    given(userService.updateUser(eq(EXISTENT_ID), any(UserDto.class)))
                            .willReturn(updatedUser);

                    response = mockMvc.perform(
                            patch(givenUrl)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(givenUserDto)));
                }

                @Test
                @DisplayName("유저를 갱신하고, 갱신한 유저를 응답한다")
                void it_updates_user_and_response_200_and_updated_user() throws Exception {
                    response.andExpect(status().isOk())
                            .andExpect(content().string(containsString(updatedUser.getName())));
                }
            }

            @Nested
            @DisplayName("이름이 비어있는 유저 데이터를 전달하면")
            class Context_of_blank_name {

                private UserDto givenUserDto;
                private ResultActions response;

                @BeforeEach
                void setup() throws Exception {
                    givenUserDto = blankNameUserDto;

                    response = mockMvc.perform(
                            patch(givenUrl)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(givenUserDto)));
                }

                @Test
                @DisplayName("400 상태코드를 응답한다")
                void it_returns_400_status_code() throws Exception {
                    response.andExpect(status().isBadRequest());
                }
            }


            @Nested
            @DisplayName("비밀번호가 비어있는 유저 데이터를 전달하면")
            class Context_of_blank_password {

                private UserDto givenUserDto;
                private ResultActions response;

                @BeforeEach
                void setup() throws Exception {
                    givenUserDto = blankPasswordUserDto;

                    response = mockMvc.perform(
                            patch(givenUrl)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(givenUserDto)));
                }

                @Test
                @DisplayName("400 상태코드를 응답한다")
                void it_returns_400_status_code() throws Exception {
                    response.andExpect(status().isBadRequest());
                }
            }
        }
    }

    @Nested
    @DisplayName("DELETE /users/{id} 요청시")
    class Describe_of_DELETE_users {

        private String givenUrl;

        @BeforeEach
        void setup() {
            givenUrl = "/users/";
        }

        @Nested
        @DisplayName("존재하는 유저 id를 전달하면")
        class Context_of_existent_id {

            private ResultActions response;

            @BeforeEach
            void setUrl() throws Exception {
                givenUrl += EXISTENT_ID;

                response = mockMvc.perform(delete(givenUrl)
                        .accept(MediaType.APPLICATION_JSON));
            }

            @Test
            @DisplayName("유저를 삭제하고, 204 상태코드를 응답한다")
            void it_deletes_user_and_responses_204_status_code() throws Exception {
                response.andExpect(status().isNoContent());

                verify(userService).deleteUser(EXISTENT_ID);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 유저 id를 전달하면")
        class Context_of_non_existent_id {

            private ResultActions response;

            @BeforeEach
            void setUrl() throws Exception {
                givenUrl += NON_EXISTENT_ID;
                willThrow(NotFoundUserException.class)
                        .given(userService).deleteUser(NON_EXISTENT_ID);

                response = mockMvc.perform(delete(givenUrl)
                        .accept(MediaType.APPLICATION_JSON));
            }

            @Test
            @DisplayName("404 상태코드를 응답한다")
            void it_responses_404_status_code() throws Exception {
                response.andExpect(status().isNotFound());
            }
        }
    }
}
