package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserSignupData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerSignupTest extends UserControllerTestContext{

    @Nested
    @DisplayName("POST /users 요청은")
    class Describe_create {

        public static final String VALID_EMAIl = "kimchi@joa.com";
        public static final String VALID_NAME = "갓김치";
        public static final String VALID_PASSWORD = "dd123#4567";

        @Nested
        @DisplayName("요청 값 중 이메일 속성이 이메일 형식이 아니면")
        class Context_without_email {

            private final UserSignupData invalidData = UserSignupData.builder()
                    .email("asdasd")
                    .name(VALID_NAME)
                    .password(VALID_PASSWORD)
                    .build();

            @DisplayName("400 status를 응답한다.")
            @Test
            void it_responses_400_status() throws Exception {
                mockMvc.perform(post("/users")
                                .content(toJson(invalidData))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                        )
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString("올바르지 않은 이메일 형식입니다")));
            }
        }

        @Nested
        @DisplayName("요청 값 중 이메일 속성이 비어있으면")
        class Context_when_empty_email {

            private final UserSignupData invalidData = UserSignupData.builder()
                    .email("")
                    .name(VALID_NAME)
                    .password(VALID_PASSWORD)
                    .build();

            @DisplayName("400 status를 응답한다.")
            @Test
            void it_responses_400_status() throws Exception {
                mockMvc.perform(post("/users")
                                .content(toJson(invalidData))
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString("빈 값이 오면 안됩니다.")));
            }
        }

        @Nested
        @DisplayName("요청 값 중 이름 속성이 비어있으면")
        class Context_when_empty_name {

            private final UserSignupData invalidData = UserSignupData.builder()
                    .email(VALID_EMAIl)
                    .name("")
                    .password(VALID_PASSWORD)
                    .build();

            @DisplayName("400 status를 응답한다.")
            @Test
            void it_responses_400_status() throws Exception {
                mockMvc.perform(post("/users")
                                .content(toJson(invalidData))
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString("빈 값이 오면 안됩니다.")));
            }
        }

        @Nested
        @DisplayName("요청 값 중 패스워드가 유효하지 않으면")
        class Context_when_invalid_password {

            private final UserSignupData invalidData = UserSignupData.builder()
                    .email(VALID_EMAIl)
                    .name(VALID_NAME)
                    .password("qwdqwdqwd")
                    .build();

            @DisplayName("400 status를 응답한다.")
            @Test
            void it_responses_400_status() throws Exception {
                mockMvc.perform(post("/users")
                                .content(toJson(invalidData))
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString("는 올바르지 않는 패스워드 형식입니다.")));
            }
        }

        @Nested
        @DisplayName("유효한 요청 데이터가 오면")
        class Context_when_valid_request_data {

            private final UserSignupData validData = UserSignupData.builder()
                    .email(VALID_EMAIl)
                    .name(VALID_NAME)
                    .password(VALID_PASSWORD)
                    .build();

            @BeforeEach
            void setUp() {
                given(userService.signUp(any(UserSignupData.class)))
                        .will((invocation -> {
                            UserSignupData userSignupData = invocation.getArgument(0);
                            return User.builder()
                                    .id(1L)
                                    .name(userSignupData.getName())
                                    .email(userSignupData.getEmail())
                                    .password(userSignupData.getPassword())
                                    .build();
                        }));
            }

            @Test
            @DisplayName("생성된 유저를 응답한다.")
            void it_responses_created_user() throws Exception {
                mockMvc.perform(post("/users")
                                .content(toJson(validData))
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").exists())
                        .andExpect(jsonPath("$.name").value(VALID_NAME))
                        .andExpect(jsonPath("$.password").value(VALID_PASSWORD))
                        .andExpect(jsonPath("$.email").value(VALID_EMAIl));
            }
        }

    }
}
