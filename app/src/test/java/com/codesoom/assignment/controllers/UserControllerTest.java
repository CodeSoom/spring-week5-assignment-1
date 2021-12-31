package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
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
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"test\",\"email\":\"test@naver.com\"}")
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
                mockMvc.perform(patch("/users/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"updateName\",\"email\":\"test@naver.com\"}")
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
                //TODO
                //SpyBean으로 실제 메소드를 호출했으나
                //수정 성공 로직에는 mock 메소드를 호출하고
                //수정 실패 로직에는 실제 메소드를 호출하니
                //같은 수정 메소드를 서로 다른 2개의 호출을 한다고 에러 메시지가 출력되는 현상
                //구현에 대하여 고민중

                given(userService.updateUser(eq(wrongId), any(UserData.class)))
                        .willThrow(new UserNotFoundException(wrongId));
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
                //TODO
                //현재 setUp과 상관없이 이 테스트는 무조건 통과된다.
                //어떻게 구현해야 할지 고민중

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
