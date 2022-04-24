package com.codesoom.assignment.controllers;

import com.codesoom.assignment.Utf8MockMvc;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Utf8MockMvc
@DisplayName("User 컨트롤러에서")
public class UserControllerApiTest {
    @Autowired MockMvc mockMvc;
    @Autowired UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String validUserInput = "{\"name\": \"김갑생\", \"email\": \"gabseng@naver.com\", \"password\": \"gabgabhada123\"}";

    @Nested
    @DisplayName("POST /user 요청을 보낼 때")
    class Describe_post_user {
        private final MockHttpServletRequestBuilder requestBuilder;

        public Describe_post_user() {
            requestBuilder = post("/user");
        }

        @Nested
        @DisplayName("유효한 입력값을 받는다면")
        class Context_valid_input {
            private final ResultActions actions;

            public Context_valid_input() throws Exception {
                userRepository.deleteAll();

                actions = mockMvc.perform(requestBuilder
                        .content(validUserInput)
                        .contentType(MediaType.APPLICATION_JSON));
            }

            @Test
            @DisplayName("201 CREATE 응답을 반환한다.")
            void It_returns_201_created_response() throws Exception {
                actions.andExpect(status().isCreated())
                        .andDo(print());
            }

            @Test
            @DisplayName("리포지토리에 User 를 추가하고, 추가한 User 를 반환한다.")
            void It_returns_user() throws Exception {
                assertThat(userRepository.count()).isNotZero();
                User user = userRepository.findAll().iterator().next();

                actions.andExpect(content().string(objectMapper.writeValueAsString(user)));
            }
        }
    }

    @Nested
    @DisplayName("GET /user/{id} 요청을 보낼 때")
    class Describe_get_user {
        private final MockHttpServletRequestBuilder requestBuilder;
        Long id = 1000L;

        public Describe_get_user() {
            requestBuilder = get("/users/" + id);
        }

        @Nested
        @DisplayName("{id} 를 가진 User 가 존재한다면")
        class Context_valid_id {
            private final ResultActions actions;
            private final User user = User.builder()
                    .id(id)
                    .password("gabseng123")
                    .name("김갑생")
                    .email("gabseng@naver.com")
                    .build();

            public Context_valid_id() throws Exception {
                userRepository.deleteAll();
                userRepository.save(user);

                actions = mockMvc.perform(requestBuilder);
            }

            @Test
            @DisplayName("200 OK 응답을 반환한다.")
            void It_returns_200_OK() throws Exception {
                actions.andExpect(status().isOk());
            }

            @Test
            @DisplayName("id 를 가진 User 를 반환한다.")
            void it_returns_user_with_specific_id() throws Exception {
                actions.andExpect(content().string(objectMapper.writeValueAsString(user)));
            }
        }
    }
}
