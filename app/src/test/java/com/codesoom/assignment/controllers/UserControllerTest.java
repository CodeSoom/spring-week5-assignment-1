package com.codesoom.assignment.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.codesoom.assignment.domain.UserConstants.USER;
import static com.codesoom.assignment.domain.UserConstants.USER_DATA;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.utils.Parser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
        ResultActions subject() throws Exception {
            return mockMvc.perform(
                post("/user")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            );
        }

        OngoingStubbing<User> mockSubject() {
            return when(userService.createUser(any(UserData.class)));
        }

        @AfterEach
        void afterEach() {
            verify(userService)
                .createUser(any(UserData.class));
        }

        @Nested
        @DisplayName("올바른 User 데이터가 주어진 경우")
        class Context_correct_UserData {
            @BeforeEach
            void beforeEach() {
                mockSubject()
                    .thenReturn(USER);
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
    }
}
