package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .name("mikekang")
                .email("test@github.com")
                .password("qwer1234")
                .build();

        given(userService.getUsers()).willReturn(List.of(user));

        given(userService.getUser(1L)).willReturn(user);

        given(userService.createUser(any(UserData.class))).willReturn(user);
    }

    @Test
    void list() throws Exception{
        mockMvc.perform(
                get("/user")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("mikekang")));
    }

    @Test
    @DisplayName("존재하는 회원 id가 주어지면, 찾은 회원과 상태코드 200을 응답한다.")
    void detailWithExistedUser() throws Exception {
        mockMvc.perform(
                get("/user/1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("mikekang")));
    }


    @Test
    @DisplayName("create는 회원정보를 주면 회원을 생성하고, 상태코드 201을 응답한다.")
    void create() throws Exception {
        mockMvc.perform(
                post("/user")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"name\" : \"mikekang\", \"email\" : \"test@github.com\", \"password\" : \"qwer1234\"}")
        )
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("mikekang")));

        verify(userService).createUser(any(UserData.class));
    }
}