package com.codesoom.assignment.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.CreateUserDto;
import com.codesoom.assignment.dto.CreateUserResponseDto;
import com.codesoom.assignment.dto.UpdateUserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Nested
    @DisplayName("POST /users 요청은")
    class Describe_postUsers {

        private CreateUserDto createUserDto;

        @Nested
        @DisplayName("유효한 회원 생성 DTO가 주어질 때")
        class Context_validUser {

            @BeforeEach
            void setUp() {
                createUserDto = CreateUserDto.builder()
                    .name("name")
                    .email("email")
                    .password("password")
                    .build();
            }

            @Test
            @DisplayName("생성한 회원을 리턴하고 201을 응답한다")
            void it_returns_created_user_and_response_201() throws Exception {
                mockMvc.perform(
                    post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(createUserDto))
                )
                    .andExpect(status().isCreated())
                    .andExpect(content()
                        .json(toJson(new CreateUserResponseDto(createUserDto.toEntity()))));
            }
        }

        @Nested
        @DisplayName("유효하지 않은 회원 생성 DTO가 주어질 때")
        class Context_invalidCreateUserDto {

            @BeforeEach
            void setUp() {
                createUserDto = CreateUserDto.builder()
                    .name("name")
                    .email("email")
                    .build();
            }

            @Test
            @DisplayName("에러를 던지고 400을 응답한다")
            void it_throws_and_response_400() throws Exception {
                mockMvc.perform(
                    post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(createUserDto))
                )
                    .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("PATCH /users/{id} 요청은")
    class Describe_patchUsersWithId {

        @BeforeEach
        void setUp() {
            userRepository.deleteAll();
        }

        private UpdateUserDto updateUserDto;
        private Long id;

        @Nested
        @DisplayName("유효한 회원 업데이트 DTO가 주어진다면")
        class Context_validUpdateUserDto {

            @BeforeEach
            void setUp() {
                updateUserDto = UpdateUserDto.builder()
                    .name("name")
                    .email("email")
                    .password("password")
                    .build();
            }

            @Nested
            @DisplayName("회원을 찾을 수 있는 경우")
            class Context_canFindUser {

                @BeforeEach
                void setUp() {
                    User createdUser = userRepository.save(
                        updateUserDto.toEntity()
                    );

                    id = createdUser.getId();
                    assertThat(userRepository.existsById(id))
                        .isTrue();
                }

                @Test
                @DisplayName("수정된 회원을 리턴하고 200을 응답한다")
                void it_returns_updated_user_and_response_200() throws Exception {
                    mockMvc.perform(
                        patch("/users/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(updateUserDto))
                    )
                        .andExpect(status().isOk())
                        .andExpect(content().json(toJson(updateUserDto)));
                }
            }

            @Nested
            @DisplayName("회원을 찾을 수 없는 경우")
            class Context_notFoundUser {

                @BeforeEach
                void setUp() {
                    userRepository.deleteAll();

                    id = 9999L;
                    assertThat(userRepository.existsById(id))
                        .isFalse();
                }

                @Test
                @DisplayName("404를 응답한다")
                void it_response_404() throws Exception {
                    mockMvc.perform(
                        patch("/users/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(updateUserDto))
                    )
                        .andExpect(status().isNotFound());
                }
            }
        }

        @Nested
        @DisplayName("유효하지 않은 업데이트 DTO가 주어진다면")
        class NestedClass {

            @BeforeEach
            void setUp() {
                updateUserDto = UpdateUserDto.builder()
                    .name("")
                    .email("")
                    .build();
            }

            @Nested
            @DisplayName("회원을 찾을 수 있는 경우")
            class Context_canFindUser {

                @BeforeEach
                void setUp() {
                    User createdUser = userRepository.save(
                        updateUserDto.toEntity()
                    );

                    id = createdUser.getId();
                    assertThat(userRepository.existsById(id))
                        .isTrue();
                }

                @Test
                @DisplayName("400을 응답한다")
                void it_returns_updated_user_and_response_200() throws Exception {
                    mockMvc.perform(
                        patch("/users/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(updateUserDto))
                    )
                        .andExpect(status().isBadRequest());
                }
            }

            @Nested
            @DisplayName("회원을 찾을 수 없는 경우")
            class Context_notFoundUser {

                @BeforeEach
                void setUp() {
                    id = 9999L;
                    assertThat(userRepository.existsById(id))
                        .isFalse();
                }

                @Test
                @DisplayName("400를 응답한다")
                void it_response_404() throws Exception {
                    mockMvc.perform(
                        patch("/users/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(updateUserDto))
                    )
                        .andExpect(status().isBadRequest());
                }
            }
        }
    }

    @Nested
    @DisplayName("DELETE /users/{id} 요청은")
    class Describe_deleteUserWithId {

        @BeforeEach
        void setUp() {
            userRepository.deleteAll();
        }

        private Long id;

        @Nested
        @DisplayName("회원을 찾을 수 있는 경우")
        class Context_findUser {

            @BeforeEach
            void setUp() {
                User fixtureUser = User.builder()
                    .name("name")
                    .email("email")
                    .password("password")
                    .build();
                User user = userRepository.save(fixtureUser);

                id = user.getId();

                assertThat(userRepository.existsById(id))
                    .isTrue();
            }

            @Test
            @DisplayName("204를 응답한다")
            void it_response_204() throws Exception {
                mockMvc.perform(
                    delete("/users/" + id)
                )
                    .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("회원을 찾을 수 없는 경우")
        class Context_notFoundUser {

            @BeforeEach
            void setUp() {
                id = 9999L;

                assertThat(userRepository.existsById(id))
                    .isFalse();
            }

            @Test
            @DisplayName("404를 응답한다")
            void it_response_404() throws Exception {
                mockMvc.perform(
                    delete("/users/" + id)
                )
                    .andExpect(status().isNotFound());
            }
        }
    }

    private String toJson(Object value) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(value);
    }
}
