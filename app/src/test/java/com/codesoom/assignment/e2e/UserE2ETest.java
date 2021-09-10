package com.codesoom.assignment.e2e;

import static com.codesoom.assignment.constants.UserConstants.ID;
import static com.codesoom.assignment.constants.UserConstants.USER_DATA;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesoom.assignment.NotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.ErrorResponse;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.utils.Parser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.RequestMethod;

@DisplayName("User 리소스")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class UserE2ETest {
    @Autowired
    private MockMvc mockMvc;

    private String requestBody;
    private Long requestParameter;

    private ResultActions subjectPostUser() throws Exception {
        return mockMvc.perform(
            post("/user")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );
    }

    // private ResultActions subjectGetUserId() throws Exception {
    //     return mockMvc.perform(
    //         get("/user/" + requestParameter)
    //             .accept(MediaType.APPLICATION_JSON_UTF8)
    //     );
    // }

    private ResultActions subjectDeleteUser() throws Exception {
        return mockMvc.perform(
            delete("/user/" + requestParameter)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );
    }

    @Nested
    @DisplayName("생성 엔드포인트는")
    public class Describe_post_user {
        @Nested
        @DisplayName("올바른 User 데이터가 주어진 경우")
        public class Context_valid_UserData {
            @AfterEach
            public void afterEach() throws Exception {
                subjectDeleteUser();
            }

            @Test
            @DisplayName("User를 생성하고 리턴한다.")
            public void it_creates_a_user() throws Exception {
                requestBody = Parser.toJson(USER_DATA);
                requestParameter = Parser.toObject(
                    subjectPostUser()
                        .andExpect(status().isCreated())
                        .andExpect(content().string(containsString("id")))
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), User.class
                ).getId();
            }
        }

        @Nested
        @DisplayName("잘못된 User 데이터가 주어진 경우")
        public class Context_invalid_UserData {
            @ParameterizedTest(name = "400(Bad Request)를 리턴한다.")
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
                    new UserData(null, name, email, password)
                );

                subjectPostUser()
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(
                        Parser.toJson(
                            ErrorResponse.builder()
                                .method(RequestMethod.POST.toString())
                                .url("/user")
                                .error(error)
                                .build()
                        )
                    ));
            }
        }
    }

    @Nested
    @DisplayName("삭제 엔드포인트는")
    public class Desciribe_delete_user {
        @Nested
        @DisplayName("User를 찾을 수 있는 경우")
        public class Context_find_success {
            @BeforeEach
            public void beforeEach() throws Exception {
                requestBody = Parser.toJson(USER_DATA);
                requestParameter = Parser.toObject(
                    subjectPostUser()
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), User.class
                ).getId();
            }

            @Test
            @DisplayName("User를 삭제한다.")
            public void it_deletes_a_user() throws Exception {
                subjectDeleteUser()
                    .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("User를 찾을 수 없는 경우")
        public class Context_find_fail {
            @Test
            @DisplayName("User를 찾지 못하였음을 알려준다.")
            public void it_notifies_user_not_found() throws Exception {
                requestParameter = ID;
                subjectDeleteUser()
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(
                        Parser.toJson(
                            ErrorResponse.builder()
                                .method(RequestMethod.DELETE.toString())
                                .url("/user/" + requestParameter)
                                .error(
                                    new NotFoundException(requestParameter, User.class.getSimpleName()).getMessage()
                                ).build()
                        )
                    ));
            }
        }
    }
}
