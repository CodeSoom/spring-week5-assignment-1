package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private UserService userService;

    private UserData userData = UserData.builder().username("username").email("email@gmail.com").password("2334").build();


    @DisplayName("UserService 클라스")
    @Test
    void setup() {
        given(userService.create(userData)).willReturn(userData);
    }

    @DisplayName("UserController Create 메소드")
    @Nested
    class Describe_create {

        @DisplayName("유저가 유효한 데이터를 보낼때")
        @Nested
        class Context_valid_user {

            @DisplayName("userdata를 반환한다")
            @Test
            void it_returns_userResponseDto() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.post("/user")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userData)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.username").value(userData.getUsername()))
                        .andExpect(jsonPath("$.password").value(userData.getPassword()))
                        .andExpect(jsonPath("$.email").value(userData.getEmail()))
                        .andDo(print());
            }
        }






    }


}
