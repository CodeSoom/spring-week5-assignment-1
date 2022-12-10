package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("UserController 클래스")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        given(userService.updateUser(eq(1L), any(UserData.class)))
                .will(invocation -> {
                    Long id = invocation.getArgument(0);
                    UserData userData = invocation.getArgument(1);
                    return User.builder()
                            .id(id)
                            .name(userData.getName())
                            .email(userData.getEmail())
                            .password(userData.getPassword())
                            .build();
                });

        User user = User.builder()
                .id(1L)
                .name("홍길동")
                .email("abc@gmail.com")
                .password("abc123")
                .build();

        given(userService.findUser(1L)).willReturn(user);
    }

    @Nested
    @DisplayName("GET 요청은")
    class Describe_GET {
        @Nested
        @DisplayName("파라미터가 없으면")
        class Context_without_segment {
            @Test
            @DisplayName("모든 user 정보를 리턴한다")
            void it_return_allUsers() throws Exception {
                mockMvc.perform(get("/users"))
                        .andExpect(status().isOk());

                verify(userService).findAll();
            }
        }

        @Nested
        @DisplayName("파라미터로 넘어오는 id가 존재하면")
        class Context_with_existedId {
            @Test
            @DisplayName("해당 id의 user 정보를 리턴한다")
            void it_return_user() throws Exception {
                mockMvc.perform(get("/users/1")
                                .accept(APPLICATION_JSON_UTF8)
                                .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("홍길동")));

                verify(userService).findUser(1L);
            }
        }

        @Nested
        @DisplayName("파라미터로 넘어오는 id가 존재하지 않으면")
        class Context_with_notExistedId {
            @BeforeEach
            void setUp() {
                given(userService.findUser(1000L)).willThrow(UserNotFoundException.class);
            }

            @Test
            @DisplayName("UserNotFoundException을 던진다")
            void it_throws_UserNotFoundException() throws Exception {
                mockMvc.perform(get("/users/1000"))
                        .andExpect(status().isNotFound());

                assertThatThrownBy(() -> userService.findUser(1000L))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("POST 요청은")
    class Describe_POST {
        @Nested
        @DisplayName("valid한 UserData가 넘어오면")
        class Context_with_validUserData {
            @Test
            @DisplayName("생성된 user를 리턴한다")
            void it_return_user() throws Exception {
                UserData userData = UserData.builder()
                        .name("홍길동")
                        .email("abc@gmail.com")
                        .password("abc123")
                        .build();

                String content = new ObjectMapper().writeValueAsString(userData);

                mockMvc.perform(post("/users")
                                .contentType(APPLICATION_JSON)
                                .content(content))
                        .andExpect(status().isCreated());

                verify(userService).saveUser(any(UserData.class));
            }
        }

        @Nested
        @DisplayName("valid에 통과하지 못한 UserData가 넘어오면")
        class Context_with_notValidUserData {
            @Test
            @DisplayName("MethodArgumentNotValidException을 던진다")
            void it_throws_MethodArgumentNotValidException() throws Exception {
                mockMvc.perform(post("/users")
                                .contentType(APPLICATION_JSON)
                                .content("{\"name\":\"\", \"email\":\"\", \"password\":\"\"}"))
                        .andExpect(status().isBadRequest())
                        .andExpect(result -> assertThat(result.getResolvedException())
                                                .isInstanceOf(MethodArgumentNotValidException.class));
            }
        }
    }

    @Nested
    @DisplayName("PATCH 요청은")
    class Describe_PATCH {
        //Static declarations in inner classes are not supported at language level '15'.
        private String userDataToJson(String name, String email, String password)
                throws JsonProcessingException {

            UserData userData = UserData.builder()
                    .name(name)
                    .email(email)
                    .password(password)
                    .build();

            return new ObjectMapper().writeValueAsString(userData);
        }

        @Nested
        @DisplayName("존재하는 id와 valid한 UserData가 넘어오면")
        class Context_with_validIdAndUserData {
            @Test
            @DisplayName("해당 user를 업데이트한다")
            void it_update_user() throws Exception {
                mockMvc.perform(patch("/users/1")
                                .contentType(APPLICATION_JSON)
                                .content(userDataToJson("홍길동", "abc@gmail.com", "abc123")))
                        .andExpect(status().isOk());

                verify(userService).updateUser(eq(1L), any(UserData.class));
            }
        }

        @Nested
        @DisplayName("존재하지 않는 id가 넘어오면")
        class Context_with_notExistedId {
            @BeforeEach
            void setUp() {
                given(userService.updateUser(eq(1000L), any(UserData.class)))
                        .willThrow(UserNotFoundException.class);
            }

            @Test
            @DisplayName("UserNotFoundException을 던진다")
            void it_throws_UserNotFoundException() throws Exception {
                mockMvc.perform(patch("/users/1000")
                                .contentType(APPLICATION_JSON)
                                .content(userDataToJson("홍길동", "abc@gmail.com", "abc123")))
                        .andExpect(status().isNotFound());

                verify(userService).updateUser(eq(1000L), any(UserData.class));

                assertThatThrownBy(() -> userService.updateUser(1000L, new UserData()))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("valid에 통과하지 못한 UserData가 넘어오면")
        class Context_with_notValidUserData {
            @Test
            @DisplayName("MethodArgumentNotValidException을 던진다")
            void it_throws_MethodArgumentNotValidException() throws Exception {
                mockMvc.perform(patch("/users/1")
                                .contentType(APPLICATION_JSON)
                                .content(userDataToJson("", "", "")))
                        .andExpect(status().isBadRequest())
                        .andExpect(result -> assertThat(result.getResolvedException())
                                                .isInstanceOf(MethodArgumentNotValidException.class));
            }
        }
    }

    @Nested
    @DisplayName("DELETE 요청은")
    class Describe_DELETE {
        @Nested
        @DisplayName("파라미터로 존재하는 id가 넘어오면")
        class Context_with_existedId {
            @Test
            @DisplayName("해당하는 user를 삭제한다")
            void it_delete_user() throws Exception {
                mockMvc.perform(delete("/users/1"))
                        .andExpect(status().isNoContent());

                verify(userService).deleteUser(1L);
            }
        }

        @Nested
        @DisplayName("파라미터로 존재하지 않는 id가 넘어오면")
        class Context_with_notExistedId {
            @BeforeEach
            void setUp() {
                doThrow(UserNotFoundException.class)
                        .when(userService)
                        .deleteUser(1000L);
            }

            @Test
            @DisplayName("UserNotFoundException을 던진다")
            void it_throws_UserNotFoundException() throws Exception {
                mockMvc.perform(delete("/users/1000"))
                        .andExpect(status().isNotFound());

                assertThatThrownBy(() -> userService.deleteUser(1000L))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
