package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.domain.UserResponseDto;
import com.codesoom.assignment.domain.UserSaveDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserSaveController 클래스")
public class UserSaveControllerMockMvcTest extends ControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        cleanup();
    }

    @AfterEach
    void cleanup() {
        repository.deleteAll();
    }

    @DisplayName("회원 정보를 성공적으로 저장한다.")
    @Test
    void saveUserTest() throws Exception {
        final UserSaveDto userSaveDto = new UserSaveDto("홍길동", "email", "password");
        final MvcResult result = mockMvc.perform(post("/users").accept(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userSaveDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        final UserResponseDto userResponseDto
                = objectMapper.readValue(result.getResponse().getContentAsByteArray(), UserResponseDto.class);
        assertThat(repository.findById(userResponseDto.getId())).isNotEmpty();
    }
    
}
