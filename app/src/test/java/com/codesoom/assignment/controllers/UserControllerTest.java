package com.codesoom.assignment.controllers;

import com.codesoom.assignment.TestUserBuilder;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.infra.InMemoryUserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserController 클래스")
class UserControllerTest {
    private final TestUserBuilder allFieldsUserBuilder = TestUserBuilder.presetAllFields();
    private final TestUserBuilder noFieldsUserBuilder = TestUserBuilder.presetNoFields();
    private MockMvc mockMvc;

    private UserRepository repository;
    private UserService service;
    private UserController controller;

    @BeforeEach
    void setup() {
        final Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        repository = new InMemoryUserRepository();
        service = new UserService(repository, mapper);
        controller = new UserController(service, mapper);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new NotFoundErrorAdvice())
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
                final String content = allFieldsUserBuilder.buildJson();

                request = post("/users")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);
            }

            @Test
            @DisplayName("Created status, 생성된 회원 정보를 반환한다")
            void it_returnsCratedStatusAndUserData() throws Exception {
                final String expectedContent = allFieldsUserBuilder.id(1L).buildJson();

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
                final String content = noFieldsUserBuilder.buildJson();

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
            private TestUserBuilder updateDataFactory = new TestUserBuilder()
                    .name("name2")
                    .password("password2")
                    .email("email2");

            @BeforeEach
            void prepare() throws JsonProcessingException {
                controller.create(allFieldsUserBuilder.buildData());
                final String content = updateDataFactory.buildJson();

                request = patch("/users/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);
            }

            @Test
            @DisplayName("Ok status, 업데이트된 회원 정보를 반환한다")
            void it_returnsCratedStatusAndUserData() throws Exception {
                final String expectedContent = updateDataFactory.id(1L).buildJson();

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
                final String content = noFieldsUserBuilder.buildJson();

                request = patch("/users/1")
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

        @Nested
        @DisplayName("존재하지 않는 회원 Id를 전달하면")
        class Context_withNotExistedUserId {
            private MockHttpServletRequestBuilder request;

            @BeforeEach
            void prepare() throws JsonProcessingException {
                repository.deleteAll();

                final String content = allFieldsUserBuilder
                        .name("name2")
                        .email("email2")
                        .password("password2")
                        .buildJson();

                request = patch("/users/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);
            }

            @Test
            @DisplayName("Not found status를 반환한다")
            void it_returnsCratedStatusAndUserData() throws Exception {
                mockMvc.perform(request)
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("DELETE /users/{id} 요청은")
    class Describe_deleteUser {
        @Nested
        @DisplayName("존재하는 회원 Id로 전달하면")
        class Context_withExistingUserId {
            @BeforeEach
            void prepare() {
                repository.save(allFieldsUserBuilder.buildUser());
            }

            @Test
            @DisplayName("No content status를 반환한다")
            void it_returnsNoContent() throws Exception {
                mockMvc.perform(delete("/users/1"))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 회원 Id로 요청하면")
        class Context_withNotExistingUserId {
            @BeforeEach
            void prepare() {
                repository.deleteAll();
            }

            @Test
            @DisplayName("Not found status를 반환한다")
            void it_returnsNotFound() throws Exception {
                mockMvc.perform(delete("/users/1"))
                        .andExpect(status().isNotFound());
            }
        }
    }
}
