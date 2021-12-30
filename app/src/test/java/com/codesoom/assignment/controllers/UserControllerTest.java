package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
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

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@WebMvcTest(UserController.class)
@DisplayName("UserController 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Nested
    class 회원을_저장하는_핸들러는 {
        @Nested
        class 유효한_회원_파라미터인_경우 {
            @Test
            void 회원을_저장한다() throws Exception {
                mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"test\",\"email\":\"test@naver.com\",\"password\":\"1234\"}")
                        )
                        .andExpect(status().isCreated());

                verify(userService).createUser(any(UserData.class));
            }
        }

        @Nested
        class 유효하지_않는_회원_파라미터인_경우 {
            @Test
            void 에러코드를_보낸다() throws Exception {
                mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"test\",\"email\":\"test@naver.com\"}")
                        ).andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    class 회원을_수정하는_핸들러는 {
        @Nested
        class 주어진_아이디의_회원이_있다면 {
            private Long userId = 1L;

            @BeforeEach
            void setUp() {
                given(userService.updateUser(eq(userId), any(UserData.class)))
                        .will(invocation -> {
                            UserData userData = invocation.getArgument(1);
                            return User.testUser(
                                    userData.getId(),
                                    userData.getName(),
                                    userData.getEmail(),
                                    userData.getPassword()
                            );
                        });

            }

            @Test
            void 회원을_수정한다() throws Exception {
                mockMvc.perform(patch("/users/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"updateName\",\"email\":\"test@naver.com\",\"password\":\"1234\"}")
                        )
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("updateName")));
            }
        }

        @Nested
        class 유효하지_않는_수정할_회원에_대한_값이면 {
            private Long userId = 1L;

            @Test
            void 에러코드를_보낸다() throws Exception {
                mockMvc.perform(patch("/users/" + userId))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        class 주어진_아이디의_회원이_없다면 {
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
                        .willThrow(new UserNotFoundException("회원을 찾을 수 없습니다."));
            }
            
            @Test
            void 에러코드를_보낸다() throws Exception {
                mockMvc.perform(patch("/users/" + wrongId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"updateName\",\"email\":\"test@naver.com\",\"password\":\"1234\"}")
                ).andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    class 회원을_삭제하는_핸들러는 {
        @Nested
        class 주어진_아이디의_회원이_있다면 {
            private Long userId = 1L;

            @BeforeEach
            void setUp() {
                //TODO
                //현재 setUp과 상관없이 이 테스트는 무조건 통과된다.
                //어떻게 구현해야 할지 고민중

                User user = User.createSaveUser("홍길동", "test@test.com", "1234");

                given(userService.deleteUser(eq(userId))).willReturn(user);
            }

            @Test
            void 회원을_삭제한다() throws Exception {
                mockMvc.perform(delete("/users/1"))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        class 주어진_아이디의_회원이_없다면 {
            private Long wrongId = 100L;

            @BeforeEach
            void setUp() {
                given(userService.deleteUser(wrongId))
                        .willThrow(new UserNotFoundException("회원을 찾을 수 없습니다."));
            }

            @Test
            void 에러_코드를_보낸다() throws Exception {
                mockMvc.perform(delete("/users/" + wrongId))
                        .andExpect(status().isNotFound());
            }
        }
    }
}
