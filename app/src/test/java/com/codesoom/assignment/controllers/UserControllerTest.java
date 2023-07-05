package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
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
    UserService userService;

    private User user;
    private User source;
    @BeforeEach
    void setup(){
        user = User.builder()
                .id(1L)
                .name("dh")
                .email("dh@gmail.com")
                .password("1111")
                .build();

        source = User.builder()
                .id(1L)
                .name("dh")
                .email("dh@naver.com")
                .password("2222")
                .build();

        given(userService.createUser(any(CreateUserData.class))).willReturn(user);
        given(userService.updateUser(eq(1L),any(UpdateUserData.class))).willReturn(source);
        given(userService.updateUser(eq(100L),any(UpdateUserData.class))).willThrow(new UserNotFoundException(100L));
        given(userService.deleteUser(100L)).willThrow(new UserNotFoundException(100L));
    }

    @Test
    void createWithValidAttributes() throws Exception {
        CreateUserData createUserData = CreateUserData.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserData)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(user.getName())));

        verify(userService).createUser(any(CreateUserData.class));
    }

    @Test
    void createWithInvalidAttributes() throws Exception {
        CreateUserData invalidUser = CreateUserData.builder()
                .name("")
                .email("")
                .password("")
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateWithExistingUser() throws Exception {
        UpdateUserData updateUserData = UpdateUserData.builder()
                .name(source.getName())
                .email(source.getEmail())
                .password(source.getPassword())
                .build();

        mockMvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(source))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(source.getName())));

        verify(userService).updateUser(eq(1L),any(UpdateUserData.class));
    }

    @Test
    void updateWithNotExistingUser() throws Exception {
        UpdateUserData updateUserData = UpdateUserData.builder()
                .name(source.getName())
                .email(source.getEmail())
                .password(source.getPassword())
                .build();

        mockMvc.perform(patch("/users/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(source))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

        verify(userService).updateUser(eq(100L),any(UpdateUserData.class));
    }

    @Test
    void updateWithInvalidAttributes() throws Exception {
        UpdateUserData invalidUser = new UpdateUserData();

        mockMvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteWithExistingUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(1L);
    }

    @Test
    void deleteWithNotExistingUser() throws Exception {
        mockMvc.perform(delete("/users/100"))
                .andExpect(status().isNotFound());
        
        verify(userService).deleteUser(100L);
    }

}
