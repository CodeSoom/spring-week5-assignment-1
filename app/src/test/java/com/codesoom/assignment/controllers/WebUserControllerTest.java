package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.users.User;
import com.codesoom.assignment.domain.users.UserRepository;
import com.codesoom.assignment.dto.UserSaveRequestData;
import com.codesoom.assignment.dto.UserUpdateRequestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    private static final String[] TEST_INVALID_EMAILS = new String[]{
            "",
            "test @example.com",
            "test",
            "test.example.com",
            "test@@example.com",
            "tes@t@example.com",
            "tes(t)@example.com",
            "test..test@example.com",
            "john.doe@example..com",
            "t\"est@example.com",
            "test@",
            "@example.com",
            "test..test@example.com"
    };

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

            final UserUpdateRequestData updateSource = UserUpdateRequestData.builder()
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

            final UserUpdateRequestData updateSource = UserUpdateRequestData.builder()
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
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        @DisplayName("회원 수정에 필요한 이메일이 유효하지 않다면")
        class Context_invalidEmail {

            UserSaveRequestData invalidSource;

            void setUp(String givenEmail) {
                invalidSource = UserSaveRequestData.builder()
                        .email(givenEmail)
                        .name(TEST_USER_NAME)
                        .password(TEST_USER_PASSWORD)
                        .build();
            }

            Stream<String> provideInvalidEmails() {
                return Stream.of(TEST_INVALID_EMAILS);
            }

            @ParameterizedTest(name = "(\"{0}\") - Bad Request 를 응답한다. [400]")
            @MethodSource("provideInvalidEmails")
            void it_response_400(String invalidEmail) throws Exception {

                setUp(invalidEmail);

                mockMvc.perform(patch("/users/{userId}", userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidSource)))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("회원 수정에 필요한 이름이 빈값 이라면")
        class Context_emptyName {

            final UserUpdateRequestData sourceWithEmptyName = UserUpdateRequestData.builder()
                    .email(TEST_USER_EMAIL)
                    .name("")
                    .password(TEST_USER_PASSWORD)
                    .build();

            @Test
            @DisplayName("Bad Request 를 응답한다. [400]")
            void it_response_400() throws Exception {

                mockMvc.perform(patch("/users/{userId}", userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(sourceWithEmptyName)))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("회원 수정에 필요한 비밀번호가 빈값 이라면")
        class Context_emptyPassword {

            final UserUpdateRequestData sourceWithEmptyPassword = UserUpdateRequestData.builder()
                    .email(TEST_USER_EMAIL)
                    .name(TEST_USER_NAME)
                    .password("")
                    .build();

            @Test
            @DisplayName("Bad Request 를 응답한다. [400]")
            void it_response_400() throws Exception {

                mockMvc.perform(patch("/users/{userId}", userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(sourceWithEmptyPassword)))
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

            private UserSaveRequestData saveSource;

            @BeforeEach
            void setUp() {
                saveSource = UserSaveRequestData.builder()
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
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        @DisplayName("회원 등록에 필요한 이메일이 유효하지 않다면")
        class Context_invalidEmail {

            private UserSaveRequestData invalidSource;

            void setUp(String givenEmail) {
                invalidSource = UserSaveRequestData.builder()
                        .email(givenEmail)
                        .name(TEST_USER_NAME)
                        .password(TEST_USER_PASSWORD)
                        .build();
            }

            Stream<String> provideInvalidEmails() {
                return Stream.of(TEST_INVALID_EMAILS);
            }

            @ParameterizedTest(name = "(\"{0}\") - Bad Request 를 응답한다. [400]")
            @MethodSource("provideInvalidEmails")
            @NullAndEmptySource
            void it_response_400(String invalidEmail) throws Exception {

                setUp(invalidEmail);

                mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidSource)))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("회원 등록에 필요한 이름이 빈값 이라면")
        class Context_emptyName {

            final UserSaveRequestData sourceWithEmptyName = UserSaveRequestData.builder()
                    .email(TEST_USER_EMAIL)
                    .name("")
                    .password(TEST_USER_PASSWORD)
                    .build();

            @Test
            @DisplayName("Bad Request 를 응답한다. [400]")
            void it_response_400() throws Exception {

                mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(sourceWithEmptyName)))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("회원 등록에 필요한 비밀번호가 빈값 이라면")
        class Context_emptyPassword {

            final UserSaveRequestData sourceWithEmptyPassword = UserSaveRequestData.builder()
                    .email(TEST_USER_EMAIL)
                    .name(TEST_USER_NAME)
                    .password("")
                    .build();

            @Test
            @DisplayName("Bad Request 를 응답한다. [400]")
            void it_response_400() throws Exception {

                mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(sourceWithEmptyPassword)))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("DELETE - /users/{userId}")
    class Describe_delete {

        final User user = User.builder()
                .email(TEST_USER_EMAIL)
                .name(TEST_USER_NAME)
                .password(TEST_USER_PASSWORD)
                .build();

        @Nested
        @DisplayName("{userId} 와 일치하는 회원이 있다면")
        class Context_existUserId {

            Long existUserId;

            @BeforeEach
            void setUp() {
                userRepository.save(user);
                existUserId = user.getId();
            }

            @Test
            @DisplayName("회원을 삭제하고, No Content 를 응답한다. [204]")
            void it_response_204() throws Exception {
                mockMvc.perform(delete("/users/{userId}", existUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNoContent());
            }
        }
    }
}
