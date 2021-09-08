package com.codesoom.assignment.web;

import static com.codesoom.assignment.constants.UserConstants.USER_DATA;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.utils.Parser;

import org.junit.jupiter.api.AfterEach;
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

@DisplayName("User 리소스")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class UserWebTest {
    @Autowired
    private MockMvc mockMvc;

    private String requestBody;

    ResultActions subjectPostUser() throws Exception {
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

    // private ResultActions subjectDeleteUserId() throws Exception {
    //     return mockMvc.perform(
    //         delete("/user/" + requestParameter)
    //             .accept(MediaType.APPLICATION_JSON_UTF8)
    //     );
    // }

    @Nested
    @DisplayName("생성 엔드포인트는")
    class Describe_post_user {
        @Nested
        @DisplayName("올바른 User 데이터가 주어진 경우")
        class Context_valid_UserData {
            @AfterEach
            void afterEach() throws Exception {
                // subjectDeleteUserId();
            }

            @Test
            @DisplayName("User를 생성하고 리턴한다.")
            void it_creates_a_user() throws Exception {
                requestBody = Parser.toJson(USER_DATA);
                subjectPostUser()
                    .andExpect(status().isCreated())
                    .andExpect(content().string(containsString("id")));
            }
        }

        @Nested
        @DisplayName("잘못된 User 데이터가 주어진 경우")
        class Context_invalid_UserData {
            @ParameterizedTest(name = "400(Bad Request)를 리턴한다. email={0}, name={1}, password={2}")
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

                subjectPostUser()
                    .andExpect(status().isBadRequest());
            }
        }
    }
}
