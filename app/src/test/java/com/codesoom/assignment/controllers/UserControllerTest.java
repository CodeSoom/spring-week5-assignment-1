package com.codesoom.assignment.controllers;

import static com.codesoom.assignment.constants.UserConstants.USER;
import static com.codesoom.assignment.constants.UserConstants.USER_DATA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesoom.assignment.NotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.ErrorResponse;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.utils.Parser;

import org.apache.commons.io.filefilter.NotFileFilter;
import org.aspectj.lang.annotation.Before;
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
import org.springframework.web.bind.annotation.RequestMethod;

@WebMvcTest(UserController.class)
@DisplayName("UserController 클래스")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @AfterEach
    private void afterEach() {
        reset(userService);
    }

    private String requestBody;

    @Nested
    @DisplayName("create 메서드는")
    public class Describe_create {
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
        public class Context_valid_UserData {
            @BeforeEach
            public void beforeEach() {
                mockSubject()
                    .thenReturn(USER);
            }

            @AfterEach
            public void afterEach() {
                verify(userService)
                    .createUser(any(UserData.class));
            }

            @Test
            @DisplayName("User를 생성하고 리턴한다.")
            public void it_creates_a_user() throws Exception {
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
        public class Context_invalid_UserData {
            @AfterEach
            public void afterEach() {
                verify(userService, never())
                    .createUser(any(UserData.class));
            }

            @ParameterizedTest(name = "400(Bad Request)를 리턴한다. email={0}, name={1}, password={2}")
            @CsvSource(
                {
                    "'', 'test', 'password, '이메일이 입력되지 않았습니다.'",
                    "'test@test.com', '', 'password', '이름이 입력되지 않았습니다.'",
                    "'test@test.com', 'test', '', '비밀번호가 입력되지 않았습니다.'"
                }
            )
            public void it_returns_a_bad_request(
                final String email, final String name,
                final String password, final String error
            ) throws Exception {
                requestBody = Parser.toJson(
                    new UserData(null, name, email, password)
                );

                subject()
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(
                        Parser.toJson(
                            ErrorResponse.builder()
                                .method(RequestMethod.POST.toString())
                                .url("/user/1")
                                .error(error)
                                .build()
                        )
                    ));
            }
        }
    }

    @Nested
    @DisplayName("destroy 메서드는")
    public class Describe_destroy {
        private ResultActions subject() throws Exception {
            return mockMvc.perform(
                delete("/user/1")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            );
        }

        @AfterEach
        public void afterEach() {
            verify(userService)
                .deleteUser(anyLong());
        }

        @Nested
        @DisplayName("User를 찾을 수 있는 경우")
        public class Context_find_success {
            @BeforeEach
            public void beforeEach() {
                doNothing()
                    .when(userService).deleteUser(anyLong());
            }

            @Test
            @DisplayName("User를 삭제한다.")
            public void it_deletes_a_user() throws Exception {
                subject()
                    .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("User를 찾을 수 없는 경우")
        public class Context_find_fail {
            @BeforeEach
            public void beforeEach() {
                doThrow(new NotFoundException(User.class.getSimpleName()))
                    .when(userService).deleteUser(anyLong());
            }

            @Test
            @DisplayName("User를 찾지 못하였음을 알려준다.")
            public void it_notifies_user_not_found() throws Exception {
                subject()
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(
                        Parser.toJson(
                            ErrorResponse.builder()
                                .method(RequestMethod.DELETE.toString())
                                .url("/user/1")
                                .error(
                                    new NotFoundException(User.class.getSimpleName()).getMessage()
                                )
                                .build()
                        )
                    ));
            }
        }
    }
}
