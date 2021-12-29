package com.codesoom.assignment.controllers;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("UserController 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private UserService userService;

    private final Long USER_ID = 1L;

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
                        .andExpect(status().isOk());

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
                        ).andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    class 회원을_수정하는_핸들러는 {
        @Nested
        class 주어진_아이디의_회원이_있다면 {
            @BeforeEach
            void setUp() {
                given(userService.updateUser(eq(USER_ID), any(UserData.class)))
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
                mockMvc.perform(patch("/users/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"updateName\",\"email\":\"test@naver.com\",\"password\":\"1234\"}")
                        )
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("updateName")));
            }
        }

        @Nested
        class 주어진_아이디의_회원이_없다면 {
            private Long wroungId;
            
            @BeforeEach
            void setUp() {
                UserData source = UserData.builder()
                        .name("홍길동")
                        .email("test@test.com")
                        .password("1234")
                        .build();

                User savedUser = userService.createUser(source);
                wroungId = savedUser.getId() + 1;
            }
            
            @Test
            void 에러코드를_보낸다() throws Exception {
                mockMvc.perform(patch("/users/" + wroungId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"updateName\",\"email\":\"test@naver.com\",\"password\":\"1234\"}")
                ).andExpect(status().isNotFound());
            }
        }
    }
}
