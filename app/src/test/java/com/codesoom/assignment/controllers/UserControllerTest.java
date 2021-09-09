package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.codesoom.assignment.constant.UserConstant.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper;
    private UserData userData;
    private UserData saveUserData;
    private UserData emptyUserData;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        userDataSetup();

        given(userService.createUser(any(UserData.class))).willReturn(saveUserData);

        given(userService.selectUser(DEFAULT_ID)).willReturn(saveUserData);
        given(userService.selectUser(NOT_EXISTS_ID)).willThrow(new UserNotFoundException(NOT_EXISTS_ID));
    }

    private void userDataSetup() {
        userData = UserData.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        saveUserData = UserData.builder()
                .id(DEFAULT_ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        emptyUserData = UserData.builder()
                .name("")
                .email("")
                .password("")
                .build();
    }

    @Test
    @DisplayName("회원 생성")
    void createUser() throws Exception {
        // when
        mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userData)))

        // then
        .andExpect(status().isCreated())
        .andExpect(content().string(containsString(NAME)));

    }

    @Test
    @DisplayName("회원 생성 실패")
    void createUserFail() throws Exception {
        // when
        mockMvc.perform(post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyUserData)))

        // then
        .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("회원 검색")
    void selectUser() throws Exception {
        // when
        mockMvc.perform(get("/users/" + DEFAULT_ID)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(NAME)));
    }

    @Test
    @DisplayName("회원 검색 실패")
    void selectUserFail() throws Exception {
        // when
        mockMvc.perform(get("/users/" + NOT_EXISTS_ID)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("회원 리스트 검색")
    void selectUsers() throws Exception {
        // when
        mockMvc.perform(get("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isOk());

    }
}
