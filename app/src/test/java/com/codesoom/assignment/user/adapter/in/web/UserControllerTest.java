package com.codesoom.assignment.user.adapter.in.web;

import com.codesoom.assignment.user.adapter.in.web.dto.response.UserCreateResponse;
import com.codesoom.assignment.utils.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.codesoom.assignment.support.IdFixture.ID_MAX;
import static com.codesoom.assignment.support.UserFixture.USER_1;
import static com.codesoom.assignment.support.UserFixture.USER_2;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("UserController 통합 웹 테스트 테스트")
class UserControllerTest {
    private final MockMvc mockMvc;
    private final UserController userController;

    @Autowired
    UserControllerTest(MockMvc mockMvc, UserController userController) {
        this.mockMvc = mockMvc;
        this.userController = userController;
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_생성_API는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 유효한_상품_정보가_주어지면 {
            @Test
            @DisplayName("201 코드를 반환한다")
            void it_responses_201() throws Exception {
                mockMvc.perform(
                                post("/users")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(JsonUtil.writeValue(USER_1.생성_요청_데이터_생성()))
                        )
                        .andExpect(status().isCreated())
                        .andExpect(content().string(containsString(USER_1.NAME())))
                        .andExpect(content().string(containsString(USER_1.EMAIL())))
                        .andExpect(content().string(containsString(USER_1.PASSWORD())));
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_수정_API는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어질_때 {
            private Long fixtureId;

            @BeforeEach
            void setUpCreateFixture() {
                UserCreateResponse user = userController.create(USER_1.생성_요청_데이터_생성());
                fixtureId = user.getId();
            }

            @Nested
            @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
            class 유효한_상품_정보라면 {
                @Test
                @DisplayName("200 코드를 반환한다")
                void it_responses_200() throws Exception {
                    mockMvc.perform(
                                    patch("/users/" + fixtureId)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(JsonUtil.writeValue(USER_2.수정_요청_데이터_생성()))
                            )
                            .andExpect(status().isOk())
                            .andExpect(content().string(containsString(String.valueOf(fixtureId))))
                            .andExpect(content().string(containsString(USER_2.NAME())))
                            .andExpect(content().string(containsString(USER_2.EMAIL())))
                            .andExpect(content().string(containsString(USER_2.PASSWORD())));
                }
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어질_때 {
            @Test
            @DisplayName("404 코드를 반환한다")
            void it_responses_404() throws Exception {
                mockMvc.perform(
                                patch("/users/" + ID_MAX.value())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(JsonUtil.writeValue(USER_2.수정_요청_데이터_생성()))
                        )
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_삭제_API는 {
        private Long fixtureId;

        @BeforeEach
        void setUpCreateFixture() {
            UserCreateResponse user = userController.create(USER_1.생성_요청_데이터_생성());
            fixtureId = user.getId();
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어질_때 {
            @Test
            @DisplayName("204 코드를 반환한다")
            void it_responses_204() throws Exception {
                mockMvc.perform(
                                delete("/users/" + fixtureId)
                        )
                        .andExpect(status().isNoContent())
                        .andExpect(content().string(containsString(String.valueOf(fixtureId))));
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어질_때 {
            @Test
            @DisplayName("404 코드를 반환한다")
            void it_responses_404() throws Exception {
                mockMvc.perform(
                                delete("/users/" + ID_MAX.value())
                        )
                        .andExpect(status().isNotFound());
            }
        }
    }
}
