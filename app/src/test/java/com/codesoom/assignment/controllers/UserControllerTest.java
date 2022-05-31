package com.codesoom.assignment.controllers;

import com.codesoom.assignment.BadRequestException;
import com.codesoom.assignment.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id(1L)
                .name("김철수")
                .email("kim@gmail.com")
                .password("1111")
                .build();

        UserData userDataWithInvalidName = UserData.builder()
                .name("")
                .email("kim@gmail.com")
                .password("1111")
                .build();
        UserData userDataWithInvalidEmail = UserData.builder()
                .name("김철수")
                .email("")
                .password("1111")
                .build();
        UserData userDataWithInvalidPassword = UserData.builder()
                .name("김철수")
                .email("kim@gmail.com")
                .password("")
                .build();

        given(userService.create(any(UserData.class))).willReturn(user);
        given(userService.create(any(userDataWithInvalidName))).willThrow(new BadRequestException());
        given(userService.create(any(userDataWithInvalidEmail))).willThrow(new BadRequestException());
        given(userService.create(any(userDataWithInvalidPassword))).willThrow(new BadRequestException());
    }

    @Test
    void createWithValidAttributes() throws Exception {
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"김철수\"," +
                                "\"email\":\"kim@gmail.com\"," +
                                "\"password\":\"1111\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("김철수"))
                .andExpect(jsonPath("$.email").value("kim@gmail.com"))
                .andExpect(jsonPath("$.password").value("1111"));

        verify(userService).create(any(UserData.class));
    }

    @Test
    void createWithInvalidName() throws Exception {
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"," +
                                "\"email\":\"kim@gmail.com\"," +
                                "\"password\":\"1111\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createWithInvalidEmail() throws Exception {
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"김철수\"," +
                                "\"email\":\"\"," +
                                "\"password\":\"1111\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createWithInvalidPassword() throws Exception {
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"김철수\"," +
                                "\"email\":\"kim@gmail.com\"," +
                                "\"password\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
