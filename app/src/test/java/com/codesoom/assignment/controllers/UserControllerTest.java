package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.RegisterData;
import com.codesoom.assignment.dto.UserData;
import org.hamcrest.Matchers;
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
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void registerUserWithValidAttributes() throws Exception {
        given(userService.registerUser(any(RegisterData.class)))
                .will(invocation -> {
                    RegisterData registerData = invocation.getArgument(0);
                    User user = User.builder()
                            .name(registerData.getName())
                            .email(registerData.getEmail())
                            .build();
                    return user;
                });

        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"슭\",\n" +
                                        "\"email\":\"bloomspes@gmail.com\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"email\":\"bloomspes@gmail.com\"")
                ));
        verify(userService).registerUser(any(RegisterData.class));

    }

    @Test
    void registerUserWithInvalidAttributes() throws Exception {
        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                )
                .andExpect(status().isBadRequest());
    }
}