package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.ProductData;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
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

    @Autowired
    private ObjectMapper objectMapper;

    private Long ExistedId = 1L;
    private Long NotExistedId = 1000L;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id(1L)
                .name("mikekang")
                .email("test@github.com")
                .password("qwer1234")
                .build();

        given(userService.getUsers()).willReturn(List.of(user));

        given(userService.getUser(ExistedId)).willReturn(user);

        given(userService.getUser(NotExistedId))
                .willThrow(new UserNotFoundException(NotExistedId));

        given(userService.createUser(any(UserData.class))).willReturn(user);

        given(userService.updateUser(eq(1L), any(UserData.class)))
                .will(invocation -> {
                    Long id = invocation.getArgument(0);
                    UserData userData = invocation.getArgument(1);
                    return User.builder()
                            .id(id)
                            .name(userData.getName())
                            .email(userData.getEmail())
                            .password(userData.getPassword())
                            .build();
                });

        given(userService.updateUser(eq(1000L), any(UserData.class)))
                .willThrow(new UserNotFoundException(1000L));

        given(userService.deleteUser(1000L))
                .willThrow(new UserNotFoundException(1000L));
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
    @DisplayName("존재하지 않는 회원 id가 주어지면, 상태코드 404를 응답한다.")
    void detailWithNotExistedUser() throws Exception {
        mockMvc.perform(get("/user/1000"))
                .andExpect(status().isNotFound());
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

    @Test
    @DisplayName("올바르지 않은 회원 정보가 주어지면, 상태코드 404를 리턴한다.")
    void createWithInvalidAttributes() throws Exception {
        mockMvc.perform(
                post("/user")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());

    }

    @Test
    void updateWithExistedUser() throws Exception {
        mockMvc.perform(
                patch("/user/1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"jason\", \"email\" : \"test@github.com\", \"password\" : \"qwer1234\"}")
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("jason")));

        verify(userService).updateUser(eq(1L), any(UserData.class));
    }

    @Test
    void updateWithNotExistedUser() throws Exception {
        mockMvc.perform(
                patch("/user/1000")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\" : \"jason\", \"email\" : \"test@github.com\", \"password\" : \"qwer1234\"}")
        )
                .andExpect(status().isNotFound());


        verify(userService).updateUser(eq(1000L), any(UserData.class));

    }

    @Test
    void destroyWithExistedUser() throws Exception {
        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(1L);
    }

    @Test
    void destroyWithNotExistedUser() throws Exception {
        mockMvc.perform(delete("/user/1000"))
                .andExpect(status().isNotFound());

        verify(userService).deleteUser(1000L);
    }
}