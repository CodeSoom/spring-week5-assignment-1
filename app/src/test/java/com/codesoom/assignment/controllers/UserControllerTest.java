package com.codesoom.assignment.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserRequest;
import com.codesoom.assignment.dto.UserResponse;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {

        User user = new User()
            .builder()
            .id(1L)
            .name("홍길동")
            .email("hong@gmail.com")
            .password("1234")
            .build();

        given(userService.createUser(any(User.class))).willReturn(user);
        given(userService.updateUser(eq(1L), any(UserRequest.class)))
            .will(invocation -> {
                Long id = invocation.getArgument(0);
                UserRequest userRequest = invocation.getArgument(1);
                return new UserResponse(1L, "임꺽정", "lim@gamil.com");
            });
        given(userService.updateUser(eq(1000L), any(UserRequest.class))).willThrow(UserNotFoundException.class);
        given(userService.deleteUser(1000L))
            .willThrow(new ProductNotFoundException(1000L));
    }

    @DisplayName("유효한 양식으로 회원 생성을 요청하였을 때, 상태코드 201을 응답한다.")
    @Test
    void createUserWithValidAttributes() throws Exception {
        mockMvc.perform(post("/users")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .contentType("APPLICATION/JSON")
            .content("{\"name\":\"홍길동\",\"email\":\"hong@gmail.com\",\"password\":\"1234\"}"))
            .andExpect(status().isCreated())
            .andExpect(content().string(containsString("홍길동")));

        verify(userService).createUser(any(User.class));
    }

    @DisplayName("유효하지 않은 양식으로 회원 생성을 요청하였을 떄, 상태코드 400을 응답한다.")
    @Test
    void createWithInvalidAttributes() throws Exception {
        mockMvc.perform(post("/users")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .contentType("APPLICATION/JSON")
            .content("{\"email\":\"hong@gmail.com\",\"password\":\"1234\"}"))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("유효한 양식으로 회원 수정을 요청하였을 때, 상태코드 200을 리턴한다.")
    @Test
    void updateWithValidAttributes() throws Exception {
        mockMvc.perform(put("/users/1")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .contentType("APPLICATION/JSON")
            .content("{\"name\":\"임꺽정\",\"email\":\"lim@gmail.com\",\"password\":\"5678\"}"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("임꺽정")));

        verify(userService).updateUser(eq(1L), any(UserRequest.class));
    }

    @DisplayName("유효하지 않은 양식으로 회원 수정을 요청하였을 때, 상태코드 400을 리턴한다.")
    @Test
    void updateWithInvalidAttributes() throws Exception {
        mockMvc.perform(put("/users/1")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .contentType("APPLICATION/JSON")
            .content("{\"email\":\"hong@gmail.com\"}"))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("존재하는 회원에 대해서 유효한 양식으로 회원 수정을 요청하였을 떄, 상태코드 200를 응답한다.")
    @Test
    void updateWithExistedUser() throws Exception {
        mockMvc.perform(put("/users/1")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .contentType("APPLICATION/JSON")
            .content("{\"name\":\"임꺽정\",\"email\":\"sh9519@gmail.com\",\"password\":\"1234\"}"))
            .andExpect(status().isOk());

        verify(userService).updateUser(eq(1L), any(UserRequest.class));
    }

    @DisplayName("존재하지 않는 회원에 대해서 유효한 양식으로 회원 수정을 요청하였을 떄, 상태코드 404를 응답한다.")
    @Test
    void updateWithNonExistedUser() throws Exception {
        mockMvc.perform(put("/users/1000")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .contentType("APPLICATION/JSON")
            .content("{\"name\":\"임꺽정\",\"email\":\"lim@gmail.com\",\"password\":\"5678\"}"))
            .andExpect(status().isNotFound());

        verify(userService).updateUser(eq(1000L), any(UserRequest.class));
    }

    @DisplayName("존재하는 회원에 대해서 회원 삭제를 요청하였을 때, 상태코드 204를 응답한다.")
    @Test
    void deleteUserWithExistedUser() throws Exception {
        mockMvc.perform(delete("/users/1")
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        verify(userService).deleteUser(eq(1L));
    }

    @DisplayName("존재하지 않는 회원에 대해서 회원 삭제를 요청하였을 때, 상태코드 404를 응답한다.")
    @Test
    void deleteUserWithNotExistedUser() throws Exception {
        mockMvc.perform(delete("/users/1000")
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isNotFound());

        verify(userService).deleteUser(eq(1000L));
    }
}
