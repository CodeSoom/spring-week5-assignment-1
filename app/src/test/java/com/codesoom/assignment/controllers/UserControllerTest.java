package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id(1L)
                .name("LIM")
                .email("lim@code.com")
                .password("123456")
                .build();

        given(userService.createUser(any(UserData.class)))
                .willReturn(user);
    }

    @Test
    void createWithValidAttributes() throws Exception {
        mockMvc.perform(
                post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"LIM\",\"email\":\"lim@code.com\",\"password\":\"123456\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("LIM")));
        verify(userService).createUser(any(UserData.class));
    }

    @Test
    void createWithInvalidAttributes() throws Exception {
        String invalidContent = "{\"name\":\"LIM\",\"email\":\"limcode.com\",\"password\":\"123456\"}";
        mockMvc.perform(
                post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidContent))
                .andExpect(status().isBadRequest());
    }
}
