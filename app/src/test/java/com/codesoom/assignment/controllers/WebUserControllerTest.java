package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.UserSaveDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("회원 대한 HTTP 요청")
public class WebUserControllerTest {

    private static final String TEST_USER_EMAIL = "unknown@naver.com";
    private static final String TEST_USER_NAME = "unknown";
    private static final String TEST_USER_PASSWORD = "1234";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Nested
    @DisplayName("POST - /users")
    class Describe_save {

        @Nested
        @DisplayName("회원 등록에 필요한 데이터로 요청을 한다면")
        class Context_valid {

            UserSaveDto source = UserSaveDto.builder()
                    .email(TEST_USER_EMAIL)
                    .name(TEST_USER_NAME)
                    .password(TEST_USER_PASSWORD)
                    .build();

            @Test
            @DisplayName("회원을 등록하고, 등록 정보를 응답한다. [201]")
            void it_response_201() throws Exception {

                mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(source)))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("email").value(TEST_USER_EMAIL))
                        .andExpect(jsonPath("name").value(TEST_USER_NAME));
            }
        }
    }
}
