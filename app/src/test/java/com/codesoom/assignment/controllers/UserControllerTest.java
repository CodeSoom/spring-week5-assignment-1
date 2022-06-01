package com.codesoom.assignment.controllers;

import com.codesoom.assignment.BadRequestException;
import com.codesoom.assignment.UserNotFoundException;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

        UserData newUserDataWithInvalidName = UserData.builder()
                .name("")
                .email("young@gmail.com")
                .password("1234")
                .build();

        given(userService.create(any(UserData.class))).willReturn(user);
        given(userService.create(eq(userDataWithInvalidName))).willThrow(new BadRequestException());
        given(userService.create(eq(userDataWithInvalidEmail))).willThrow(new BadRequestException());
        given(userService.create(eq(userDataWithInvalidPassword))).willThrow(new BadRequestException());

        given(userService.update(eq(1L), any(UserData.class)))
                .will(invocation -> {
                    Long id = invocation.getArgument(0);
                    UserData userData = invocation.getArgument(1);

                    return User.builder()
                            .id(1L)
                            .name(userData.getName())
                            .email(userData.getEmail())
                            .password(userData.getPassword())
                            .build();
        });
        given(userService.update(eq(1000L), any(UserData.class))).willThrow(new UserNotFoundException(1000L));
        given(userService.update(eq(1L), eq(newUserDataWithInvalidName))).willThrow(new BadRequestException());

        given(userService.delete(1000L)).willThrow(new UserNotFoundException(1000L));
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

    @Test
    void updateWithExistedUser() throws Exception {
        mockMvc.perform(patch("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"김영희\"," +
                                "\"email\":\"young@gmail.com\"," +
                                "\"password\":\"1234\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("김영희"));

        verify(userService).update(eq(1L), any(UserData.class));
    }

    @Test
    void updateWithNotExistedUser() throws Exception {
        mockMvc.perform(patch("/user/1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"김영희\"," +
                                "\"email\":\"young@gmail.com\"," +
                                "\"password\":\"1234\"}"))
                .andExpect(status().isNotFound());

        verify(userService).update(eq(1000L), any(UserData.class));
    }

    @Test
    void updateWithInvalidAttributes() throws Exception {
        mockMvc.perform(patch("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"," +
                                "\"email\":\"young@gmail.com\"," +
                                "\"password\":\"1234\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteWithExistedUser() throws Exception {
        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isNoContent());

        verify(userService).delete(1L);
    }

    @Test
    void deleteWithNotExistedUser() throws Exception {
        mockMvc.perform(delete("/user/1000"))
                .andExpect(status().isNotFound());

        verify(userService).delete(1000L);
    }
}
