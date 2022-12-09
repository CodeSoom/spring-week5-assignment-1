package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    }

    @Nested
    @DisplayName("POST 요청은")
    class Descrbie_POST {
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
    }

    @Nested
    @DisplayName("PATCH 요청은")
    class Describe_PATCH {
        @Nested
        @DisplayName("존재하는 id와 valid한 UserData가 넘어오면")
        class Context_with_validIdAndUserData {
            @Test
            @DisplayName("해당 user를 업데이트한다")
            void it_update_user() throws Exception {
                UserData userData = UserData.builder()
                        .name("고길동")
                        .email("def@gmail.com")
                        .password("def456")
                        .build();

                String content = new ObjectMapper().writeValueAsString(userData);

                mockMvc.perform(patch("/users/1")
                        .contentType(APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isOk());

                verify(userService).updateUser(eq(1L), any(UserData.class));
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
    }
}
