// 1. POST /users -> 회원 생성
// 2. PATCH /users/{id} -> 회원 수정
// 3. DELETE /users/{id} -> 회원 삭제

package com.codesoom.assignment.controllers;

import com.codesoom.assignment.errors.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserRegistrationData;
import com.codesoom.assignment.dto.UserModificationData;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

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

@DisplayName("UserController 테스트")
@WebMvcTest(UserController.class) // 스프링부트 전체를 띄우지 않고 필요한 것만 사용
class UserControllerTest {
    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    void setUpMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class createUser_메소드는 {

        @BeforeEach
        void setUp() {
            given(userService.create(any(UserRegistrationData.class))).will(invocation -> {
                UserRegistrationData registrationData = invocation.getArgument(0);
                User user = User.builder()
                        .id(1L)
                        .name(registrationData.getName())
                        .email(registrationData.getEmail())
                        .build();

                return user;
            });
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 필수_파라메타가_모두_있다면 {

            @Test
            @DisplayName("새로운 회원을 생성한다")
            void 새로운_회원을_생성한다() throws Exception {
                mockMvc.perform(
                                post("/users")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content("{\"name\":\"codesoom\"," +
                                                "\"email\":\"test@test.com\"," +
                                                " \"password\":\"asdqwe1234\"}"
                                        )
                        )
                        .andExpect(status().isCreated())
                        .andExpect(content().string(
                                containsString("\"id\":1")
                        ))
                        .andExpect(content().string(
                                containsString("\"name\":\"codesoom\"")
                        ))
                        .andExpect(content().string(
                                containsString("\"email\":\"test@test.com\"")
                        ));

                verify(userService).create(any(UserRegistrationData.class));
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 필수_파라메타가_모두_없다면 {

            @Test
            @DisplayName("Bad request 를 응답한다")
            void Bad_request를_응답한다() throws Exception {
                mockMvc.perform(
                                post("/users")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content("{\"name\": \"\"," +
                                                "\"email\": \"\"," +
                                                " \"password\": \"asdqwe1234\"}"
                                        )
                        )
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class updateUser_메소드는 {

        @BeforeEach
        void setUp() {
            given(userService.update(eq(1L), any(UserModificationData.class))).will(invocation -> {
                Long id = invocation.getArgument(0);
                UserModificationData modificationData = invocation.getArgument(1);
                return User.builder()
                        .id(id)
                        .email("tester@example.com")
                        .name(modificationData.getName())
                        .password(modificationData.getPassword())
                        .build();
            });

            given(userService.update(eq(1000L), any(UserModificationData.class)))
                    .willThrow(new UserNotFoundException(1000L));
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ID가_존재하는_회원이라면 {

            @Test
            @DisplayName("기존 회원의 정보가 변경된 회원을 리턴한다")
            void 정보가_변경된_회원을_리턴한다() throws Exception {
                mockMvc.perform(
                                patch("/users/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content("{\"name\":\"홍길동\"," +
                                                "\"password\":\"1234qweasd\"}"
                                        )
                        )
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("\"name\":\"홍길동\"")));
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ID가_존재하지않는_회원이라면 {

            @Test
            @DisplayName("Not found 예외를 던진다")
            void Not_found_예외를_던진다() throws Exception {
                mockMvc.perform(
                                patch("/users/1000")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content("{\"name\":\"홍길동\"," +
                                                "\"password\":\"123qweasd\"}"
                                        )
                        )
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 모든_파라메타가_비어있다면 {

            @Test
            @DisplayName("Bad request를 응답한다")
            void Bad_request를_응답한다() throws Exception {
                mockMvc.perform(
                                patch("/users/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content("{\"name\":\"\",\"password\":\"\"}")
                        )
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class deleteUser_메소드는 {

        @BeforeEach
        void setUp() {
            doThrow(new UserNotFoundException(1000L)).when(userService).deleteUserById(1000L);
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ID가_존재하는_회원이라면 {

            @Test
            @DisplayName("회원 정보를 삭제한다")
            void 회원_정보를_삭제한다() throws Exception {
                mockMvc.perform(delete("/users/1"))
                        .andExpect(status().isNoContent());

                verify(userService).deleteUserById(1L);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ID가_존재하지않는_회원이라면 {

            @Test
            @DisplayName("Not found 예외를 던진다")
            void Not_found_예외를_던진다() throws Exception {
                mockMvc.perform(delete("/users/1000"))
                        .andExpect(status().isNotFound());
            }
        }
    }
}