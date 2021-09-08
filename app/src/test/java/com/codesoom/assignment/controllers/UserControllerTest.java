package com.codesoom.assignment.controllers;

import static com.codesoom.assignment.constants.UserConstants.USER;
import static com.codesoom.assignment.constants.UserConstants.USER_DATA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.utils.Parser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(UserController.class)
@DisplayName("UserController 클래스")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @AfterEach
    void afterEach() {
        reset(userService);
    }

    private String requestBody;

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {
        private ResultActions subject() throws Exception {
            return mockMvc.perform(
                post("/user")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            );
        }

        private OngoingStubbing<User> mockSubject() {
            return when(userService.createUser(any(UserData.class)));
        }

        @Nested
        @DisplayName("올바른 User 데이터가 주어진 경우")
        class Context_valid_UserData {
            @BeforeEach
            void beforeEach() {
                mockSubject()
                    .thenReturn(USER);
            }

            @AfterEach
            void afterEach() {
                verify(userService)
                    .createUser(any(UserData.class));
            }

            @Test
            @DisplayName("User를 생성하고 리턴한다.")
            void it_creates_a_user() throws Exception {
                requestBody = Parser.toJson(USER_DATA);

                subject()
                    .andExpect(status().isCreated())
                    .andExpect(content().string(
                        Parser.toJson(USER)
                    ));
            }
        }

        @Nested
        @DisplayName("잘못된 User 데이터가 주어진 경우")
        class Context_invalid_UserData {
            @AfterEach
            void afterEach() {
                verify(userService, never())
                    .createUser(any(UserData.class));
            }

            @ParameterizedTest(name = "400(Bad Request)를 리턴한다.")
            @CsvSource(
                {
                    "'test@test.com', 'test', ''",
                    "'', 'test', 'password",
                    "'test@test.com', '', 'password'"
                }
            )
            void it_returns_a_bad_request(
                final String email, final String name, final String password
            ) throws Exception {
                requestBody = Parser.toJson(
                    new UserData(null, name, email, password)
                );

                subject()
                    .andExpect(status().isBadRequest());
            }
        }
    }
}
