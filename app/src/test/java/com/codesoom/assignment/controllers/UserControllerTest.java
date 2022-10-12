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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private UserService userService;

    private UserData userData = UserData.builder().build();


    @DisplayName("Mocking with UserService")
    @Test
    void setup() {
//        given(userService.create(userRequestDto)).willReturn(userResponseDto);
    }

    @DisplayName("UserController Create Method")
    @Nested
    class Describe_create {

        @DisplayName("if a user requests for a valid sign up")
        @Nested
        class Context_user_requests {

            @DisplayName("returns a UserResponseDto")
            @Test
            void it_returns_userResponseDto() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.post("/user")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userData)))
//                              .andExpect(status().isCreated());
                                .andDo(print());
//                              .andExpect(jsonPath("$.").value("12345"))
//                              .andExpect(content().objectMapper.writeValueAsString(userResponseDto))
            }
        }
    }


}
