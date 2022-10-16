package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserCreateRequest;
import com.codesoom.assignment.dto.UserUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("UserController MockMvc 테스트")
class UserControllerTest {

    private static final String NAME = "ABC";
    private static final String EMAIL = "abc@hello.com";
    private static final String PASSWORD = "aa!@#5";
    private static final String UPDATE_PREFIX = "updated_";

    private static final UserCreateRequest CREATE_REQUEST = UserCreateRequest.builder()
            .name(NAME)
            .email(EMAIL)
            .password(PASSWORD)
            .build();

    private static final UserUpdateRequest UPDATE_REQUEST = UserUpdateRequest.builder()
            .name(UPDATE_PREFIX + NAME)
            .password(UPDATE_PREFIX + PASSWORD)
            .build();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("생성 요청이 들어오면 새로운 회원을 생성하고 200 응답 코드를 생성한다")
    void createUser_test() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CREATE_REQUEST)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andDo(print());
    }

    @Test
    @DisplayName("입력값이 올바르지 않은 생성 요청이 들어오면 400 응답 코드를 생성한다")
    void createUser_wrong_input_test() throws Exception {
        UserCreateRequest wrongRequestBody = UserCreateRequest.builder()
                .name("")
                .email("")
                .password("")
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wrongRequestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("수정 요청이 들어오면 회원 정보를 수정하고 200 응답 코드를 생성한다")
    void updateUser_test() throws Exception {
        User user = userService.create(CREATE_REQUEST);

        mockMvc.perform(put("/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_REQUEST)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value(UPDATE_REQUEST.getName()))
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 id로 수정 요청이 들어오면 404 응답 코드를 생성한다")
    void updateUser_invalid_id_test() throws Exception {
        User user = userService.create(CREATE_REQUEST);
        userService.delete(user.getId());

        mockMvc.perform(put("/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_REQUEST)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("입력값이 올바르지 않은 수정 요청이 들어오면 400 응답 코드를 생성한다")
    void updateUser_wrong_input_test() throws Exception {
        User user = userService.create(CREATE_REQUEST);
        userService.delete(user.getId());

        UserUpdateRequest wrongRequestBody = UserUpdateRequest.builder()
                .name("")
                .email("")
                .password("")
                .build();

        mockMvc.perform(put("/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wrongRequestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("삭제 요청이 들어오면 삭제를 수행하고 204 응답 코드를 생성한다")
    void deleteUser_test() throws Exception {
        User user = userService.create(CREATE_REQUEST);

        mockMvc.perform(delete("/users/{id}", user.getId()))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 id로 삭제 요청이 들어오면 404 응답 코드를 생성한다")
    void deleteUser_invalid_id_test() throws Exception {
        User user = userService.create(CREATE_REQUEST);
        userService.delete(user.getId());

        mockMvc.perform(delete("/users/{id}", user.getId()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
