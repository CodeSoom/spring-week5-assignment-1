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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static final Long DEFAULT_ID = 1L;

    private static final Long INVALID_ID = 1000L;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id(DEFAULT_ID)
                .name("홍길동")
                .email("hong@naver.com")
                .password("12341234")
                .build();

        given(userService.createUser(any(UserData.class)))
                .willReturn(user);

        given(userService.updateUser(eq(DEFAULT_ID), any(UserData.class)))
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

        given(userService.updateUser(eq(INVALID_ID), any(UserData.class)))
                .willThrow(new UserNotFoundException(INVALID_ID));

        given(userService.deleteUser(INVALID_ID))
                .willThrow(new UserNotFoundException(INVALID_ID));
    }

    @Test
    void createWithValidUser() throws Exception {
        mockMvc.perform(
                        post("/users")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"홍길동\",\"email\":\"hong@naver.com\"," +
                                        "\"password\":\"12341234\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("홍길동")));
    }

    @Test
    void createWithInvalidName() throws Exception {
        mockMvc.perform(
                        post("/users")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"\",\"email\":\"hong@naver.com\"," +
                                        "\"password\":\"12341234\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("이름을 입력하지 않았습니다. 이름을 입력해주세요.")));
    }

    @Test
    void createWithInvalidEmail() throws Exception {
        mockMvc.perform(
                        post("/users")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"홍길동\",\"email\":\"\"," +
                                        "\"password\":\"12341234\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("이메일을 입력하지 않았습니다. 이메일을 입력해주세요.")));
    }

    @Test
    void createWithInvalidEmailType() throws Exception {
        mockMvc.perform(
                        post("/users")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"홍길동\",\"email\":\"hong\"," +
                                        "\"password\":\"12341234\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("잘못된 형식의 이메일입니다. 형식에 맞는 이메일을 입력해주세요.")));
    }

    @Test
    void createWithInvalidPassword() throws Exception {
        mockMvc.perform(
                        post("/users")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"홍길동\",\"email\":\"hong@naver.com\"," +
                                        "\"password\":\"\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("비밀번호를 입력하지 않았습니다. 비밀번호를 입력해주세요.")));
    }

    @Test
    void updateWithValidUser() throws Exception {
        mockMvc.perform(
                        patch("/users/"+DEFAULT_ID)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"앙김홍집\",\"email\":\"hongzip@naver.com\"," +
                                        "\"password\":\"123123\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("앙김홍집")));

    }

    @Test
    void updateWithNotExistedUser() throws Exception {
        mockMvc.perform(
                        patch("/users/"+INVALID_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"앙김홍집\",\"email\":\"hongzip@naver.com\"," +
                                        "\"password\":\"123123\"}")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void updateWithInvalidName() throws Exception {
        mockMvc.perform(
                        patch("/users/1")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"\",\"email\":\"hongzip@naver.com\"," +
                                        "\"password\":\"123123\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("이름을 입력하지 않았습니다. 이름을 입력해주세요.")));
    }

    @Test
    void updateWithInvalidEmail() throws Exception {
        mockMvc.perform(
                        patch("/users/1")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"앙김홍집\",\"email\":\"\"," +
                                        "\"password\":\"123123\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("이메일을 입력하지 않았습니다. 이메일을 입력해주세요.")));
    }

    @Test
    void updateWithInvalidEmailType() throws Exception {
        mockMvc.perform(
                        patch("/users/1")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"앙김홍집\",\"email\":\"hongzip@\"," +
                                        "\"password\":\"123123\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("잘못된 형식의 이메일입니다. 형식에 맞는 이메일을 입력해주세요.")));
    }

    @Test
    void updateWithInvalidPassword() throws Exception {
        mockMvc.perform(
                        patch("/users/1")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"앙김홍집\",\"email\":\"hongzip@naver.com\"," +
                                        "\"password\":\"\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("비밀번호를 입력하지 않았습니다. 비밀번호를 입력해주세요.")));
    }

    @Test
    void deleteWithExistedUser() throws Exception {
        mockMvc.perform(delete("/users/"+DEFAULT_ID))
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(DEFAULT_ID);
    }

    @Test
    void deleteWithNotExistedUser() throws Exception {
        mockMvc.perform(delete("/users/"+INVALID_ID))
                .andExpect(status().isNotFound());

        verify(userService).deleteUser(INVALID_ID);
    }
}