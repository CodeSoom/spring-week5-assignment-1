package com.codesoom.assignment.controllers;

import com.codesoom.assignment.TestUserDataBuilder;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.infra.InMemoryUserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserController 클래스")
class UserControllerTest {
    private final TestUserDataBuilder validUserDataFactory = TestUserDataBuilder.valid();
    private final TestUserDataBuilder invalidUserDataFactory = TestUserDataBuilder.invalid();
    private MockMvc mockMvc;
    private UserController controller;

    @BeforeEach
    void setup() {
        final UserRepository repository = new InMemoryUserRepository();
        final UserService service = new UserService(repository);
        controller = new UserController(service);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Nested
    @DisplayName("POST /users 요청은")
    class Describe_postUser {
        @Nested
        @DisplayName("유효한 회원 정보를 전달하면")
        class Context_withValidUserData {
            private MockHttpServletRequestBuilder request;

            @BeforeEach
            void prepare() throws JsonProcessingException {
                final String content = validUserDataFactory.buildJson();

                request = post("/users")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);
            }

            @Test
            @DisplayName("Created status, 생성된 회원 정보를 반환한다")
            void it_returnsCratedStatusAndUserData() throws Exception {
                final String expectedContent = validUserDataFactory.id(1L).buildJson();

                mockMvc.perform(request)
                        .andExpect(status().isCreated())
                        .andExpect(content().json(expectedContent));
            }
        }

        @Nested
        @DisplayName("유효하지 않은 회원 정보를 전달하면")
        class Context_withInvalidUserData {
            private MockHttpServletRequestBuilder request;

            @BeforeEach
            void prepare() throws JsonProcessingException {
                final String content = invalidUserDataFactory.buildJson();

                request = post("/users")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);
            }

            @Test
            @DisplayName("Bad Request status를 반환한다")
            void it_returnsCratedStatusAndUserData() throws Exception {
                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("PATCH /users 요청은")
    class Describe_patchUser {
        @Nested
        @DisplayName("존재하는 회원 Id와 유효한 회원 정보를 전달하면")
        class Context_withValidUserData {
            private MockHttpServletRequestBuilder request;

            @BeforeEach
            void prepare() throws JsonProcessingException {
                controller.createUser(validUserDataFactory.buildData());
                final String content = validUserDataFactory.buildJson();

                request = patch("/users/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);
            }

            @Test
            @DisplayName("Ok status, 업데이트된 회원 정보를 반환한다")
            void it_returnsCratedStatusAndUserData() throws Exception {
                final String expectedContent = validUserDataFactory.id(1L).buildJson();

                mockMvc.perform(request)
                        .andExpect(status().isOk())
                        .andExpect(content().json(expectedContent));
            }
        }

        @Nested
        @DisplayName("유효하지 않은 회원 정보를 전달하면")
        class Context_withInvalidUserData {
            private MockHttpServletRequestBuilder request;

            @BeforeEach
            void prepare() throws JsonProcessingException {
                final String content = invalidUserDataFactory.buildJson();

                request = post("/users")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);
            }

            @Test
            @DisplayName("Bad Request status를 반환한다")
            void it_returnsCratedStatusAndUserData() throws Exception {
                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }
    }
}
