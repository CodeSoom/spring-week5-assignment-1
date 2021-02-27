package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserApplicationService;
import com.codesoom.assignment.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserApplicationService service;

    @BeforeEach
    void setUp() {
        String name = "라스";
        String mail = "las@magical.dev";
        String password = "pK1RZRUAgExuFYfr0qHY";
        User user = new User(1L, name, mail, password);

        given(service.createUser(name, mail, password)).willReturn(user);
        doNothing().when(service).deleteUser(1L);
        doThrow(new UserNotFoundException(2L))
                .when(service)
                .deleteUser(2L);
    }

    @Test
    void createUser() throws Exception {
        String userJson = "{\"name\": \"라스\", \"email\": \"las@magical.dev\", \"password\": \"pK1RZRUAgExuFYfr0qHY\"}";
        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(userJson)
        )
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("라스")));
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteNotExistUser() throws Exception {
        mockMvc.perform(delete("/users/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser() throws Exception {
        String userJson = "{\"name\": \"Las\", \"email\": \"las2@magical.dev\", \"password\": \"1234yu90\"}";
        mockMvc.perform(
                patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Las")));
    }
}
