package com.codesoom.assignment.controllers;

import com.codesoom.assignment.Utf8MockMvc;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.exceptions.UserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Utf8MockMvc
@DisplayName("User 컨트롤러가")
public class UserControllerApiTest {
    @Autowired MockMvc mockMvc;
    @Autowired UserRepository userRepository;
    @Autowired UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ModelMapper modelMapper;
    private final String validUserInput = "{\"name\": \"김갑생\", \"email\": \"gabseng@naver.com\", \"password\": \"gabgabhada123\"}";

    @Nested
    @DisplayName("POST /users 요청을 받을 때")
    class Describe_post_user {
        private final MockHttpServletRequestBuilder requestBuilder;

        public Describe_post_user() {
            requestBuilder = post("/users");
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
    @DisplayName("GET /users/{id} 요청을 받을 때")
    class Describe_get_user {
        @Nested
        @DisplayName("{id} 를 가진 User 가 존재한다면")
        class Context_valid_id {
            private final MockHttpServletRequestBuilder requestBuilder;
            private final ResultActions actions;
            private final User user;

            public Context_valid_id() throws Exception {
                userRepository.deleteAll();
                user = userRepository.save(User.builder()
                        .password("gabseng123")
                        .name("김갑생")
                        .email("gabseng@naver.com")
                        .build());

                requestBuilder = get("/users/" + user.getId());
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

    @Nested
    @DisplayName("PATCH /users/{id} 요청을 받을 때")
    class Describe_patch_user {
        @Nested
        @DisplayName("id 를 가진 User 가 존재하고, body 에 수정될 필드 정보가 들어있다면")
        class Context_valid_id {
            private final MockHttpServletRequestBuilder requestBuilder;
            private final ResultActions actions;
            private final User originUser;
            private final User source;
            private final String originName = "김갑생";
            private final String originEmail = "gabseng@naver.com";

            public Context_valid_id() throws Exception {
                userRepository.deleteAll();

                originUser = userRepository.save(User.builder()
                        .password("gabseng123")
                        .name(originName)
                        .email(originEmail)
                        .build());

                source = User.builder()
                        .name("updated" + originUser.getName())
                        .email("updated" + originUser.getEmail())
                        .build();

                requestBuilder = patch("/users/" + originUser.getId());
                actions = mockMvc.perform(requestBuilder
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(source)));
            }

            @Test
            @DisplayName("200 OK 응답을 반환한다.")
            void It_returns_200_OK() throws Exception {
                actions.andExpect(status().isOk());
            }

            @Test
            @DisplayName("업데이트된 User 를 반환한다.")
            void it_returns_user_with_specific_id() throws Exception {
                modelMapper.map(source, originUser);
                actions.andExpect(content().string(objectMapper.writeValueAsString(originUser)));
                assertThat(originUser.getName()).isEqualTo("updated" + originName);
                assertThat(originUser.getEmail()).isEqualTo("updated" + originEmail);
            }
        }
    }

    @Nested
    @DisplayName("DELETE /users/{id} 요청을 받을 때")
    class Describe_delete_user {
        @Nested
        @DisplayName("id 를 가진 User 가 존재한다면")
        class Context_valid_id {
            private final MockHttpServletRequestBuilder requestBuilder;
            private final ResultActions actions;
            private final User user;

            public Context_valid_id() throws Exception {
                userRepository.deleteAll();

                user = userRepository.save(User.builder()
                        .password("gabseng123")
                        .name("name")
                        .email("name")
                        .build());

                requestBuilder = delete("/users/" + user.getId());

                actions = mockMvc.perform(requestBuilder);
            }

            @Test
            @DisplayName("204 No Content 응답을 반환한다.")
            void it_returns_204_no_content() throws Exception {
                actions.andExpect(status().isNoContent());
            }

            @Test
            @DisplayName("리포지토리에서 user 를 삭제한다.")
            void it_deletes_user() {
                assertThatThrownBy(() -> userService.get(user.getId()))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
