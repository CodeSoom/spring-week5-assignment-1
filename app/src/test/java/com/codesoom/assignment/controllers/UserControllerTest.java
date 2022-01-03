package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("UserController 클래스")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private Mapper mapper = new DozerBeanMapper();
    private ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("회원저장 요청을 처리하는 핸들러는")
    @Nested
    class requestForSaveUserHandler {

        @DisplayName("저장할 회원 파라미터가 유효성 검사를 통과한 경우")
        @Nested
        class UserParamValid {
            @DisplayName("회원을 저장한다")
            @Test
            void saveUser() throws Exception {
                mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"test\",\"email\":\"test@naver.com\",\"password\":\"1234\"}")
                        )
                        .andExpect(status().isCreated());

                verify(userService).createUser(any(UserData.class));
            }
        }

        @DisplayName("저장할 회원 파라미터가 유효성 검사에 실패한 경우")
        @Nested
        class UserParamInValid {
            @DisplayName("에러코드를 보낸다")
            @Test
            void sendErrorCode() throws Exception {
                String requiredName = "홍길동";
                String requiredEmail = "test@naver.com";
                String requiredPassword = null;

                UserData userData = UserData.builder()
                        .name(requiredName)
                        .email(requiredEmail)
                        .password(requiredPassword)
                        .build();

                String json = objectMapper.writeValueAsString(userData);

                mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                        ).andExpect(status().isBadRequest());
            }
        }
    }

    @DisplayName("회원수정 요청을 처리하는 핸들러는")
    @Nested
    class requestForUpdateUserHandler {
        @DisplayName("주어진 아이디의 회원이 있다면")
        @Nested
        class existUserToId {
            private Long userId = 1L;

            @BeforeEach
            void setUp() {
                given(userService.updateUser(eq(userId), any(UserData.class)))
                        .will(invocation -> {
                            UserData userData = invocation.getArgument(1);
                            userData.setId(userId);

                            return mapper.map(userData, User.class);
                        });
            }

            @DisplayName("회원을 수정한다")
            @Test
            void updateUser() throws Exception {
                mockMvc.perform(patch("/users/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"updateName\",\"email\":\"test@naver.com\",\"password\":\"1234\"}")
                        )
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("updateName")));
            }
        }

        @DisplayName("수정할 회원 파라미터가 유효성 검사에 실패한 경우")
        @Nested
        class UserParamInValid {
            private Long userId = 1L;

            @DisplayName("에러코드를 보낸다")
            @Test
            void sendErrorCode() throws Exception {
                String requiredName = "홍길동!!!";
                String requiredEmail = "test@naver.com";
                String requiredPassword = null;

                UserData userData = UserData.builder()
                        .name(requiredName)
                        .email(requiredEmail)
                        .password(requiredPassword)
                        .build();

                String json = objectMapper.writeValueAsString(userData);

                mockMvc.perform(patch("/users/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                        )
                        .andExpect(status().isBadRequest());
            }
        }

        @DisplayName("주어진 아이디의 회원이 없다면")
        @Nested
        class NotExistUserToId {
            private Long wrongId = 100L;
            
            @BeforeEach
            void setUp() {
                doThrow(new UserNotFoundException(wrongId))
                        .when(userService)
                        .updateUser(eq(wrongId), any(UserData.class));
            }

            @DisplayName("에러코드를 보낸다")
            @Test
            void sendErrorCode() throws Exception {
                mockMvc.perform(patch("/users/" + wrongId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"updateName\",\"email\":\"test@naver.com\",\"password\":\"1234\"}")
                ).andExpect(status().isNotFound());
            }
        }
    }

    @DisplayName("회원삭제 요청을 처리하는 핸들러는")
    @Nested
    class requestForDeleteUserHandler {
        @DisplayName("주어진 아이디의 회원이 있다면")
        @Nested
        class existUserToId {
            private Long userId = 1L;

            @BeforeEach
            void setUp() {
                User user = User.createUserForSave("홍길동", "test@test.com", "1234");

                given(userService.deleteUser(eq(userId))).willReturn(user);
            }

            @DisplayName("회원을 삭제한다")
            @Test
            void deleteUser() throws Exception {
                mockMvc.perform(delete("/users/1"))
                        .andExpect(status().isNoContent());
            }
        }

        @DisplayName("주어진 아이디의 회원이 없다면")
        @Nested
        class NotExistUserToId {
            private Long wrongId = 100L;

            @BeforeEach
            void setUp() {
                given(userService.deleteUser(wrongId))
                        .willThrow(new UserNotFoundException(wrongId));
            }

            @DisplayName("에러코드를 보낸다")
            @Test
            void sendErrorCode() throws Exception {
                mockMvc.perform(delete("/users/" + wrongId))
                        .andExpect(status().isNotFound());
            }
        }
    }
}
