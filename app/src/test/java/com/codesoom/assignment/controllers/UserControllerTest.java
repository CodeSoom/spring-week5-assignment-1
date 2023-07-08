package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserCreator;
import com.codesoom.assignment.application.UserDeleter;
import com.codesoom.assignment.application.UserUpdater;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.CreateUserData;
import com.codesoom.assignment.dto.UpdateUserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserCreator userCreator;

    @MockBean
    UserUpdater userUpdater;

    @MockBean
    UserDeleter userDeleter;

    //fixture
    private User user;
    private User source;
    private CreateUserData validCreateUserData;
    private CreateUserData invalidCreateUserData;
    private UpdateUserData validUpdateUserData;
    private UpdateUserData invalidUpdateUserData;
    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 100L;
    private static final String NAME = "dh";
    private static final String EMAIL = "dh@gmail.com";
    private static final String PASSWORD = "1111";
    private static final String UPDATED_NAME = "dhj";
    private static final String UPDATED_PASSWORD = "2222";
    @BeforeEach
    void setup(){
        user = User.builder()
                .id(VALID_ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        source = User.builder()
                .id(VALID_ID)
                .email(EMAIL)
                .name(UPDATED_NAME)
                .password(UPDATED_PASSWORD)
                .build();

        validCreateUserData = CreateUserData.builder()
                                .name(NAME)
                                .email(EMAIL)
                                .password(PASSWORD)
                                .build();

        validUpdateUserData = UpdateUserData.builder()
                                    .name(UPDATED_NAME)
                                    .password(UPDATED_PASSWORD)
                                    .build();

        invalidCreateUserData = new CreateUserData();

        invalidUpdateUserData = new UpdateUserData();

        given(userCreator.create(any(CreateUserData.class))).willReturn(user);
        given(userUpdater.update(eq(VALID_ID),any(UpdateUserData.class))).willReturn(source);
        given(userUpdater.update(eq(INVALID_ID),any(UpdateUserData.class))).willThrow(new UserNotFoundException(INVALID_ID));
        given(userDeleter.delete(INVALID_ID)).willThrow(new UserNotFoundException(INVALID_ID));
    }

    @Test
    void createWithValidAttributes() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCreateUserData)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(user.getName())));

        verify(userCreator).create(any(CreateUserData.class));
    }

    @Test
    void createWithInvalidAttributes() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCreateUserData)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateWithExistingUser() throws Exception {
        mockMvc.perform(patch("/users/"+VALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUpdateUserData))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(source.getName())));

        verify(userUpdater).update(eq(VALID_ID),any(UpdateUserData.class));
    }

    @Test
    void updateWithNotExistingUser() throws Exception {
        mockMvc.perform(patch("/users/"+INVALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUpdateUserData))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

        verify(userUpdater).update(eq(INVALID_ID),any(UpdateUserData.class));
    }

    @Test
    void updateWithInvalidAttributes() throws Exception {
        mockMvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUpdateUserData)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteWithExistingUser() throws Exception {
        mockMvc.perform(delete("/users/"+VALID_ID))
                .andExpect(status().isNoContent());

        verify(userDeleter).delete(VALID_ID);
    }

    @Test
    void deleteWithNotExistingUser() throws Exception {
        mockMvc.perform(delete("/users/"+INVALID_ID))
                .andExpect(status().isNotFound());

        verify(userDeleter).delete(INVALID_ID);
    }

}
