package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    MessageSource messageSource;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private UserService userService;

    private UserData validUserData = UserData.builder().username("username").email("email@gmail.com").password("2334").build();

    @DisplayName("UserService 클라스 mocking")
    @BeforeEach
    void setup() {
        given(userService.create(validUserData)).willReturn(validUserData);
    }

    @DisplayName("UserController Create 메소드")
    @Nested
    class Describe_create {

        @DisplayName("유저가 유효한 데이터를 보낼때")
        @Nested
        class Context_valid_user {

            @DisplayName("userdata를 반환한다")
            @Test
            void it_returns_userResponseDto() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.post("/users")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validUserData)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.username").value(validUserData.getUsername()))
                        .andExpect(jsonPath("$.password").value(validUserData.getPassword()))
                        .andExpect(jsonPath("$.email").value(validUserData.getEmail()))
                        .andDo(print());
            }
        }

        @DisplayName("유저가 username/password/email를 공백으로 보낼때")
        @Nested
        class Context_empty_User {
            UserData emptyUser = UserData.builder().username("").email("").password("").build();

            @DisplayName("예외를 던진다")
            @Test
            void it_returns_userResponseDto() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.post("/users")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(emptyUser)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.email").value(messageSource.getMessage("User.emailNotBlank", null, null)))
                        .andExpect(jsonPath("$.password").value(messageSource.getMessage("User.passwordNotBlank", null, null)))
                        .andExpect(jsonPath("$.username").value(messageSource.getMessage("User.usernameNotBlank", null, null)))
                        .andDo(print());

            }
        }


        @DisplayName("유저가 username/password/email를 null로 보낼때")
        @Nested
        class Context_empty_username {
            UserData emptyUser = UserData.builder().username(null).email(null).password(null).build();

            @DisplayName("에외를 던진다")
            @Test
            void it_returns_userResponseDto() throws Exception {

                mockMvc.perform(MockMvcRequestBuilders.post("/users")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(emptyUser)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.email").value(messageSource.getMessage("User.emailNotNull", null, null)))
                        .andExpect(jsonPath("$.password").value(messageSource.getMessage("User.passwordNotNull", null, null)))
                        .andExpect(jsonPath("$.username").value(messageSource.getMessage("User.usernameNotNull", null, null)))
                        .andDo(print());
            }

        }


    }


}
