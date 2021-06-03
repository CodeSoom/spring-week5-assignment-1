package com.codesoom.assignment.controllers;

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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    String validContent = "{\"name\":\"LIM\",\"email\":\"lim@code.com\",\"password\":\"123456\"}";
    String invalidEmailContent = "{\"name\":\"LIM\",\"email\":\"limcode.com\",\"password\":\"123456\"}";
    String invalidPasswordContent = "{\"name\":\"LIM\",\"email\":\"lim@code.com\",\"password\":\"1\"}";

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

        given(userService.updateUser(eq(1L), any(UserData.class)))
                .will(invocation -> {
                    Long id = invocation.getArgument(0);
                    UserData userData = invocation.getArgument(1);
                    return User.builder()
                            .id(id)
                            .name(userData.getName())
                            .email(userData.getEmail())
                            .password(userData.getPassword())
                            .age(userData.getAge())
                            .build();
                });

        given(userService.deleteUser(1L)).willReturn(user);
        given(userService.deleteUser(999L))
                .willThrow(new UserNotFoundException(999L));
    }

    @Test
    void createWithValidAttributes() throws Exception {
        mockMvc.perform(
                post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validContent))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("LIM")));
        verify(userService).createUser(any(UserData.class));
    }

    @Test
    void createWithInvalidAttributes() throws Exception {
        mockMvc.perform(
                post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidEmailContent)
                        .content(invalidPasswordContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateWithValidAttributes() throws Exception {
        mockMvc.perform(
                patch("/users/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validContent)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("LIM")));
    }

    @Test
    void updateWithInvalidAttributes() throws Exception {
        mockMvc.perform(
                patch("/users/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidEmailContent)
                        .content(invalidPasswordContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteWithExistedId() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
        verify(userService).deleteUser(1L);
    }

    @Test
    void deleteWithNotExistedId() throws Exception {
        mockMvc.perform(delete("/users/999"))
                .andExpect(status().isNotFound());
    }
}
