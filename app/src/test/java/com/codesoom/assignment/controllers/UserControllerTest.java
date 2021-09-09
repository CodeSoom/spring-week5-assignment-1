package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserPostDto;
import com.codesoom.assignment.dto.UserUpdateDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private UserPostDto userPostDtoFixture;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setupFixtures() {
        userPostDtoFixture = UserPostDto.builder()
                .name("nana")
                .email("nana@gmail.com")
                .password("nananan")
                .build();
    }

    private <T> T getResponseContent(ResultActions actions, TypeReference<T> type)
            throws UnsupportedEncodingException, JsonProcessingException {
        MvcResult mvcResult = actions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        return objectMapper.readValue(contentAsString, type);
    }

    private User createUserBeforePatchTest() throws Exception {
        ResultActions actions = mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(userPostDtoFixture))
                .contentType(MediaType.APPLICATION_JSON));

        User createdUser = getResponseContent(actions, new TypeReference<User>() {});

        return createdUser;
    }

    @Nested
    @DisplayName("Post Request")
    class PostRequest {
        @Nested
        @DisplayName("With a valid request body")
        class WithValidRequestBody {
            @Test
            @DisplayName("returns a created user with 201 HTTP status code")
            void returnsCreatedUser() throws Exception {
                mockMvc.perform(post("/users")
                                .content(objectMapper.writeValueAsString(userPostDtoFixture))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(containsString(userPostDtoFixture.getName())))
                        .andExpect(content().string(containsString(userPostDtoFixture.getEmail())))
                        .andExpect(content().string(containsString(userPostDtoFixture.getPassword())));
            }
        }

        @Nested
        @DisplayName("When the name is invalid")
        class WhenNameIsInvalid {
            @ParameterizedTest(name = "responses with 400 error with the name \"{0}\"")
            @ValueSource(strings = {" ", ""})
            void responsesWith400Error(String name) throws Exception {
                UserPostDto invalidNameUserPostDto = UserPostDto.builder()
                        .email("valid@email.com")
                        .password("valid-password")
                        .name(name)
                        .build();

                mockMvc.perform(post("/users")
                                .content(objectMapper.writeValueAsString(invalidNameUserPostDto))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("When the email is invalid")
        class WhenEmailIsInvalid {
            @ParameterizedTest(name = "responses with 400 error with the email \"{0}\"")
            @ValueSource(strings = {" ", "", "no-at-sign"})
            void responsesWith400Error(String email) throws Exception {
                UserPostDto invalidEmailUserPostDto = UserPostDto.builder()
                        .email(email)
                        .password("valid-password")
                        .name("valid-name")
                        .build();

                mockMvc.perform(post("/users")
                                .content(objectMapper.writeValueAsString(invalidEmailUserPostDto))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("When the password is invalid")
        class WhenPasswordIsInvalid {
            @ParameterizedTest(name = "responses with 400 error with the password \"{0}\"")
            @ValueSource(strings = {" ", ""})
            void responsesWith400Error(String password) throws Exception {
                UserPostDto invalidPasswordUserPostDto = UserPostDto.builder()
                        .email(password)
                        .password("valid-password")
                        .name("valid-name")
                        .build();

                mockMvc.perform(post("/users")
                                .content(objectMapper.writeValueAsString(invalidPasswordUserPostDto))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("Patch Request")
    class PatchRequest {
        private User createdUser;
        private UserUpdateDto updateDto;

        @BeforeEach
        void setup() throws Exception {
            createdUser = createUserBeforePatchTest();

            updateDto = UserUpdateDto.builder()
                    .name("new name")
                    .password("newpassword")
                    .build();
        }

        @Nested
        @DisplayName("With a valid request body")
        class WithValidRequestBody {
            @Test
            @DisplayName("returns an updated user with 200 HTTP status code")
            void returnsUpdatedUser() throws Exception {
                User expectedUpdatedUser = User.builder()
                        .id(createdUser.getId())
                        .email(createdUser.getEmail())
                        .name(updateDto.getName())
                        .password(updateDto.getPassword())
                        .build();

                mockMvc.perform(patch("/users/" + createdUser.getId())
                                .content(objectMapper.writeValueAsString(updateDto))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().json(objectMapper.writeValueAsString(expectedUpdatedUser)));
            }
        }

        @Nested
        @DisplayName("With a non existent id")
        class WithNonExistentId {
            @Test
            @DisplayName("responses with 404 HTTP status code")
            void responsesWith404Error() throws Exception {
                mockMvc.perform(patch("/users/" + 100000000L)
                                .content(objectMapper.writeValueAsString(updateDto))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("When the name is invalid")
        class WhenNameIsInvalid {
            @ParameterizedTest(name = "responses with 400 error with the name \"{0}\"")
            @ValueSource(strings = {" ", ""})
            void responsesWith400Error(String name) throws Exception {
                UserUpdateDto invalidNameUserUpdateDto = UserUpdateDto.builder()
                        .password("valid-password")
                        .name(name)
                        .build();

                mockMvc.perform(patch("/users/" + createdUser.getId())
                                .content(objectMapper.writeValueAsString(invalidNameUserUpdateDto))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("When the password is invalid")
        class WhenPasswordIsInvalid {
            @ParameterizedTest(name = "responses with 400 error with the password \"{0}\"")
            @ValueSource(strings = {" ", ""})
            void responsesWith400Error(String password) throws Exception {
                UserUpdateDto invalidPasswordUserUpdateDto = UserUpdateDto.builder()
                        .password(password)
                        .name("valid-name")
                        .build();

                mockMvc.perform(patch("/users/" + createdUser.getId())
                                .content(objectMapper.writeValueAsString(invalidPasswordUserUpdateDto))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("Delete Request")
    class DeleteRequest {
        private User createdUser;

        @BeforeEach
        void setup() throws Exception {
            createdUser = createUserBeforePatchTest();
        }

        @Nested
        @DisplayName("With a valid path parameter")
        class WithValidRequestBody {
            @Test
            @DisplayName("responses with 204 HTTP status code")
            void responsesWith204() throws Exception {
                mockMvc.perform(delete("/users/" + createdUser.getId()))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("With a non existent id")
        class WithNonExistentParameter {
            @Test
            @DisplayName("responses with 404 HTTP status code")
            void responsesWith404() throws Exception {
                mockMvc.perform(delete("/users/" + 1000000000L))
                        .andExpect(status().isNotFound());
            }
        }
    }
}
