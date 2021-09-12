package com.codesoom.assignment.web;

import static com.codesoom.assignment.constants.UserConstants.ID;
import static com.codesoom.assignment.constants.UserConstants.USER;
import static com.codesoom.assignment.constants.UserConstants.USER_DATA;
import static com.codesoom.assignment.constants.UserConstants.USER_ENDPOINT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesoom.assignment.NotFoundException;
import com.codesoom.assignment.controllers.UserController;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.ErrorResponse;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.dto.UpdateUserData;
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
import org.springframework.web.bind.annotation.RequestMethod;

@WebMvcTest(UserController.class)
@DisplayName("User 리소스")
public class UserWebTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserController userController;


    @AfterEach
    private void afterEach() {
        reset(userController);
    }

    private UserController verifyController(final int invokeCounts) {
        return verify(userController, times(invokeCounts));
    }

    private String requestBody;
    private Long requestParameter;

    @Nested
    @DisplayName("생성 엔드포인트는")
    public class Describe_create {
        private ResultActions subject() throws Exception {
            return mockMvc.perform(
                post(USER_ENDPOINT)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            );
        }

        private OngoingStubbing<User> mockCreate() {
            return when(userController.create(any(UserData.class)));
        }

        @Nested
        @DisplayName("User 생성에 필요한 모든 데이터가 주어진경우")
        public class Context_valid_UserData {
            @BeforeEach
            private void beforeEach() {
                mockCreate()
                    .thenReturn(USER);
            }

            @AfterEach
            private void afterEach() {
                verifyController(1)
                    .create(any(UserData.class));
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
        @DisplayName("User 생성에 필요한 모든 데이터가 주어지지 않은 경우")
        public class Context_invalid_UserData {
            @AfterEach
            private void afterEach() {
                verifyController(0)
                    .create(any(UserData.class));
            }

            @ParameterizedTest(name = "어떤 데이터가 필요한지 알려준다.")
            @CsvSource(
                {
                    "'', 'test', 'password', '이메일이 입력되지 않았습니다.'",
                    "'test@test.com', '', 'password', '이름이 입력되지 않았습니다.'",
                    "'test@test.com', 'test', '', '비밀번호가 입력되지 않았습니다.'"
                }
            )
            public void it_returns_a_bad_request(
                final String email, final String name,
                final String password, final String error
            ) throws Exception {
                requestBody = Parser.toJson(
                    new UserData(name, email, password)
                );

                subject()
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(
                        Parser.toJson(
                            ErrorResponse.builder()
                                .method(RequestMethod.POST.toString())
                                .url(USER_ENDPOINT)
                                .error(error)
                                .build()
                        )
                    ));
            }
        }
    }

    @Nested
    @DisplayName("삭제 엔드포인트는")
    public class Describe_destroy {
        private ResultActions subject() throws Exception {
            return mockMvc.perform(
                delete(USER_ENDPOINT + requestParameter)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            );
        }

        @AfterEach
        private void afterEach() {
            verifyController(1)
                .destroy(anyLong());
        }

        @Nested
        @DisplayName("User를 찾을 수 있는 경우")
        public class Context_find_success {
            @BeforeEach
            public void beforeEach() {
                doNothing()
                    .when(userController).destroy(anyLong());
            }

            @Test
            @DisplayName("User를 삭제한다.")
            public void it_deletes_a_user() throws Exception {
                requestParameter = ID;

                subject()
                    .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("User를 찾을 수 없는 경우")
        public class Context_find_fail {
            @BeforeEach
            public void beforeEach() {
                doThrow(new NotFoundException(ID, User.class.getSimpleName()))
                    .when(userController).destroy(anyLong());
            }

            @Test
            @DisplayName("User를 찾지 못하였음을 알려준다.")
            public void it_notifies_user_not_found() throws Exception {
                requestParameter = ID;

                subject()
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(
                        Parser.toJson(
                            ErrorResponse.builder()
                                .method(RequestMethod.DELETE.toString())
                                .url(USER_ENDPOINT + requestParameter)
                                .error(
                                    new NotFoundException(requestParameter, User.class.getSimpleName()).getMessage()
                                )
                                .build()
                        )
                    ));
            }
        }
    }

    @Nested
    @DisplayName("수정 엔드포인트는")
    public class Describe_update {
        private String responseBody;

        private ResultActions subjectPatch() throws Exception {
            return mockMvc.perform(
                patch(USER_ENDPOINT + requestParameter)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            );
        }

        private ResultActions subjectPut() throws Exception {
            return mockMvc.perform(
                put(USER_ENDPOINT + requestParameter)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            );
        }

        private OngoingStubbing<User> mockUpdate() {
            return when(userController.update(anyLong(), any(UpdateUserData.class)));
        }

        @AfterEach
        private void afterEach() {
            verifyController(2)
                .update(anyLong(), any(UpdateUserData.class));
        }

        @Nested
        @DisplayName("User를 찾을 수 있는 경우")
        public class Context_find_success {
            @BeforeEach
            private void beforeEach() {
                mockUpdate()
                    .thenReturn(USER);
            }

            @Test
            @DisplayName("User를 수정하고 리턴한다.")
            public void it_returns_a_updated_user() throws Exception {
                requestParameter = ID;
                requestBody = Parser.toJson(USER_DATA);

                responseBody = Parser.toJson(USER);

                subjectPatch()
                    .andExpect(status().isOk())
                    .andExpect(content().string(responseBody));

                subjectPut()
                    .andExpect(status().isOk())
                    .andExpect(content().string(responseBody));
            }
        }

        @Nested
        @DisplayName("User를 찾을 수 없는 경우")
        public class Context_find_fail {
            @BeforeEach
            private void beforeEach() {
                mockUpdate()
                    .thenThrow(new NotFoundException(ID, User.class.getSimpleName()));
            }

            @Test
            @DisplayName("User를 찾지 못하였음을 알려준다.")
            public void it_notifies_user_not_found() throws Exception {
                requestParameter = ID;
                requestBody = Parser.toJson(USER_DATA);

                subjectPatch()
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(
                        Parser.toJson(
                            ErrorResponse.builder()
                                .method(RequestMethod.PATCH.toString())
                                .url(USER_ENDPOINT + requestParameter)
                                .error(
                                    new NotFoundException(requestParameter, User.class.getSimpleName()).getMessage()
                                )
                                .build()
                        )
                    ));

                subjectPut()
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(
                        Parser.toJson(
                            ErrorResponse.builder()
                                .method(RequestMethod.PUT.toString())
                                .url(USER_ENDPOINT + requestParameter)
                                .error(
                                    new NotFoundException(requestParameter, User.class.getSimpleName()).getMessage()
                                )
                                .build()
                        )
                    ));
            }
        }
    }
}
