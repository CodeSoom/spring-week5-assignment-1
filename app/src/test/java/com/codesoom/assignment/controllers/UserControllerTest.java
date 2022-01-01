package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserRegisterationData;
import com.codesoom.assignment.dto.UserResultData;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        given(userService.registerUser(any(UserRegisterationData.class)))
                .will(invocations -> {
                    UserRegisterationData registrationData = invocations.getArgument(0);
                    User user = User.builder()
                            .id(13L)
                            .email(registrationData.getEmail())
                            .name(registrationData.getName())
                            .password(registrationData.getPassword())
                            .build();
                    return user;
                });
    }


    @Test
    void registerUserWithValidAttributes() throws Exception {
        mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"email\":\"jihwooon@gmail.com\"," +
                                        "\"name\":\"jihwooon\",\"password\":\"test\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"id\":13")
                ))
                .andExpect(content().string(
                        containsString("\"email\":\"jihwooon@gmail.com\"")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"jihwooon")
                ))
                .andExpect(content().string(
                        containsString("\"password\":\"test")
                ));

        verify(userService).registerUser(any(UserRegisterationData.class));
    }

    @Test
    void registerUserWithInValidAttributes() throws Exception {
        mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(
                        patch("/user/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"email\":\"junghwaaan@gmail.com\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"email\":\"junghwaaan@gmail.com\"")
                ));

    }
}
