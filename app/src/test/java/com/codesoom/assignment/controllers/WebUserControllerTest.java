package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.users.User;
import com.codesoom.assignment.domain.users.UserRepository;
import com.codesoom.assignment.dto.UserSaveDto;
import com.codesoom.assignment.dto.UserUpdateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("회원 대한 HTTP 요청")
public class WebUserControllerTest {

    private static final String TEST_USER_EMAIL = "unknown@naver.com";
    private static final String TEST_USER_NAME = "unknown";
    private static final String TEST_USER_PASSWORD = "1234";

    private static final String TEST_UPDATE_POSTFIX = "_UPDATE";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Nested
    @DisplayName("PATCH - /users/{id}")
    class Describe_patch {

        final User user = User.builder()
                .email(TEST_USER_EMAIL)
                .name(TEST_USER_NAME)
                .password(TEST_USER_PASSWORD)
                .build();

        Long userId;

        @BeforeEach
        void setUp() {
            userRepository.save(user);
            userId = user.getId();
        }

        @Nested
        @DisplayName("회원 수정에 필요한 데이터로 요청을 한다면")
        class Context_valid {

            final UserUpdateDto updateSource = UserUpdateDto.builder()
                    .email(TEST_USER_EMAIL + TEST_UPDATE_POSTFIX)
                    .name(TEST_USER_NAME + TEST_UPDATE_POSTFIX)
                    .password(TEST_USER_PASSWORD + TEST_UPDATE_POSTFIX)
                    .build();

            @Test
            @DisplayName("회원을 수정하고, 회원정보를 응답한다. [200]")
            void it_response_200() throws Exception {

                mockMvc.perform(patch("/users/{userId}", userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateSource)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("id").value(userId))
                        .andExpect(jsonPath("email").value(TEST_USER_EMAIL + TEST_UPDATE_POSTFIX))
                        .andExpect(jsonPath("name").value(TEST_USER_NAME + TEST_UPDATE_POSTFIX));
            }
        }

        @Nested
        @DisplayName("주어진 아이디와 일치하는 회원이 없다면")
        class Context_notExistId {

            final UserUpdateDto updateSource = UserUpdateDto.builder()
                    .email(TEST_USER_EMAIL + TEST_UPDATE_POSTFIX)
                    .name(TEST_USER_NAME + TEST_UPDATE_POSTFIX)
                    .password(TEST_USER_PASSWORD + TEST_UPDATE_POSTFIX)
                    .build();

            final Long notExistId = 999L;

            @Test
            @DisplayName("Not Found 를 응답한다. [404]")
            void it_response_404() throws Exception {

                mockMvc.perform(patch("/users/{userId}", notExistId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateSource)))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("유효하지 않은 이메일을 요청 한다면")
        class Context_invalidEmail {

            UserSaveDto source;

            void setUp(String givenEmail) {
                source = UserSaveDto.builder()
                        .email(givenEmail)
                        .name(TEST_USER_NAME)
                        .password(TEST_USER_PASSWORD)
                        .build();
            }

            @ParameterizedTest(name = "(\"{0}\") - Bad Request 를 응답한다. [400]")
            @ValueSource(strings = {"", "testnaver.com", "@testemail"})
            void it_response_400(String invalidEmail) throws Exception {

                setUp(invalidEmail);

                mockMvc.perform(patch("/users/{userId}", userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(source)))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("비어있는 이름을 요청 한다면")
        class Context_emptyName {

            final UserUpdateDto updateSource = UserUpdateDto.builder()
                    .email(TEST_USER_EMAIL)
                    .name("")
                    .password(TEST_USER_PASSWORD)
                    .build();

            @Test
            @DisplayName("Bad Request 를 응답한다. [400]")
            void it_response_400() throws Exception {

                mockMvc.perform(patch("/users/{userId}", userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateSource)))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("비어있는 비밀번호 요청 한다면")
        class Context_emptyPassword {

            final UserUpdateDto updateSource = UserUpdateDto.builder()
                    .email(TEST_USER_EMAIL)
                    .name(TEST_USER_NAME)
                    .password("")
                    .build();

            @Test
            @DisplayName("Bad Request 를 응답한다. [400]")
            void it_response_400() throws Exception {

                mockMvc.perform(patch("/users/{userId}", userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateSource)))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("POST - /users")
    class Describe_save {

        @Nested
        @DisplayName("회원 등록에 필요한 데이터로 요청을 한다면")
        class Context_valid {

            private UserSaveDto saveSource;

            @BeforeEach
            void setUp() {
                saveSource = UserSaveDto.builder()
                        .email(TEST_USER_EMAIL)
                        .name(TEST_USER_NAME)
                        .password(TEST_USER_PASSWORD)
                        .build();
            }

            @Test
            @DisplayName("회원을 등록하고, 등록 정보를 응답한다. [201]")
            void it_response_201() throws Exception {

                mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(saveSource)))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("email").value(TEST_USER_EMAIL))
                        .andExpect(jsonPath("name").value(TEST_USER_NAME));
            }
        }

        @Nested
        @DisplayName("유효하지 않은 이메일을 요청 한다면")
        class Context_invalidEmail {

            private UserSaveDto source;

            void setUp(String givenEmail) {
                source = UserSaveDto.builder()
                        .email(givenEmail)
                        .name(TEST_USER_NAME)
                        .password(TEST_USER_PASSWORD)
                        .build();
            }

            @ParameterizedTest(name = "(\"{0}\") - Bad Request 를 응답한다. [400]")
            @ValueSource(strings = {"testnaver.com", "testemail"})
            @NullAndEmptySource
            void it_response_400(String invalidEmail) throws Exception {

                setUp(invalidEmail);

                mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(source)))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("유효하지 않은 이름으로 요청 한다면")
        class Context_invalidName {

            private UserSaveDto source;

            void setUp(String name) {
                source = UserSaveDto.builder()
                        .email(TEST_USER_EMAIL)
                        .name(name)
                        .password(TEST_USER_PASSWORD)
                        .build();
            }

            @ParameterizedTest(name = "(\"{0}\") - Bad Request 를 응답한다. [400]")
            @NullAndEmptySource
            void it_response_400(String givenName) throws Exception {

                setUp(givenName);

                mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(source)))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("유효하지 않은 비밀번호로 요청 한다면")
        class Context_invalidPassword {

            private UserSaveDto source;

            void setUp(String givenPassword) {
                source = UserSaveDto.builder()
                        .email(TEST_USER_EMAIL)
                        .name(TEST_USER_NAME)
                        .password(givenPassword)
                        .build();
            }

            @ParameterizedTest(name = "(\"{0}\") - Bad Request 를 응답한다. [400]")
            @NullAndEmptySource
            void it_response_400(String givenPassword) throws Exception {

                setUp(givenPassword);

                mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(source)))
                        .andExpect(status().isBadRequest());
            }
        }
    }
}
