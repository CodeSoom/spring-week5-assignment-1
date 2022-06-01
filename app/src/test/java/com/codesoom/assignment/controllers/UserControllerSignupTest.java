package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserSignupData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerSignupTest extends UserControllerTestContext{

    @Nested
    @DisplayName("POST /users 요청은")
    class Describe_create {

        @Nested
        @DisplayName("요청 값 중 이메일 속성이 이메일 형식이 아니면")
        class Context_without_email {

            private final UserSignupData invalidData = UserSignupData.builder()
                    .email("qwd")
                    .name("김치")
                    .password("123456")
                    .build();

            @DisplayName("400 status를 응답한다.")
            @Test
            void it_responses_400_status() throws Exception {
                mockMvc.perform(post("/users")
                                .content(toJson(invalidData))
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("유효한 요청 데이터가 오면")
        class Context_when_valid_request_data {

            public static final String EMAIl = "kimchi@joa.com";
            public static final String NAME = "갓김치";
            public static final String PASSWORD = "1234567";

            private final UserSignupData validData = UserSignupData.builder()
                    .email(EMAIl)
                    .name(NAME)
                    .password(PASSWORD)
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
                        .andExpect(jsonPath("$.name").value(NAME))
                        .andExpect(jsonPath("$.password").value(PASSWORD))
                        .andExpect(jsonPath("$.email").value(EMAIl));
            }
        }

    }
}
