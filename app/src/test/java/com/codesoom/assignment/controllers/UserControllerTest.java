package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserCreateRequest;
import com.codesoom.assignment.dto.UserUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserController 클래스")
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private final Long EXISTING_ID = 1L;
    private final Long NOT_EXISTING_ID = 100L;

    private User user;
    private UserCreateRequest userCreateRequest;
    private UserUpdateRequest userUpdateRequest;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(EXISTING_ID)
                .name("name")
                .email("email")
                .build();

    }

    @Nested
    @DisplayName("GET 요청은")
    class Describe_GET {

        @Nested
        @DisplayName("저장된 사용자 id가 주어지면")
        class Context_with_an_existing_user_id {

            @BeforeEach
            void setUp() {
                given(userService.getUser(EXISTING_ID)).willReturn(user);
            }

            @Test
            @DisplayName("찾은 사용자와 상태코드 200을 응답한다")
            void it_responds_found_user_and_status_code_200() throws Exception {
                mockMvc.perform(get("/users/{id}", EXISTING_ID)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").exists())
                        .andExpect(jsonPath("name").exists())
                        .andExpect(jsonPath("email").exists())
                        .andExpect(status().isOk());
            }
        }
    }
}
