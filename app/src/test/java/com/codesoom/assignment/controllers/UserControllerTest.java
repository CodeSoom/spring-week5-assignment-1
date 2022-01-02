package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserRegistrationData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("UserController 클래스")
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    List<UserRegistrationData> testUserRegistrationData = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testUserRegistrationData.add(UserRegistrationData.builder()
                .name("Hyuk")
                .password("!234")
                .email("pjh0819@naver.com")
                .build());

        testUserRegistrationData.add(UserRegistrationData.builder()
                .name("Update Hyuk")
                .password("123$")
                .email("pjh9999@naver.com")
                .build());
    }

    @Nested
    @DisplayName("POST /user 요청은")
    class Describe_post{

        @Nested
        @DisplayName("등록될 user가 주어진다면")
        class Context_with_user {

            UserRegistrationData givenUserRegistrationData;
            User givenUser = new User();

            @BeforeEach
            void prepare() {
                givenUserRegistrationData = testUserRegistrationData.get(0);
                givenUser.change(givenUserRegistrationData.getName(),
                        givenUserRegistrationData.getPassword(),
                        givenUserRegistrationData.getEmail());

                given(userService.createUser(any(UserRegistrationData.class)))
                        .willReturn(givenUser);
            }

            @Test
            @DisplayName("201(Created)와 생성된 user 응답합니다.")
            void it_create_user_return_created_and_user() throws Exception {
                mockMvc.perform(post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userDataToContent(givenUserRegistrationData)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name").value(givenUser.getName()))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("등록될 user가 없다면")
        class Context_without_user {

            @Test
            @DisplayName("400(Bad Request)를 응답합니다.")
            void it_return_bad_request() throws Exception {
                mockMvc.perform(post("/user")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("등록될 user의 name이 빈값이라면")
        class Context_with_user_with_empty_name {

            UserRegistrationData givenInvalidUserRegistrationData;

            @BeforeEach
            void prepare() {
                givenInvalidUserRegistrationData = UserRegistrationData.builder()
                        .password("!234")
                        .email("pjh0819@naver.com")
                        .build();
            }

            @Test
            @DisplayName("400(Bad Request)를 응답합니다.")
            void it_return_bad_request() throws Exception {
                mockMvc.perform(post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userDataToContent(givenInvalidUserRegistrationData)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("등록될 user의 password가 빈값이라면")
        class Context_with_user_with_empty_password {

            UserRegistrationData givenInvalidUserRegistrationData;

            @BeforeEach
            void prepare() {
                givenInvalidUserRegistrationData = UserRegistrationData.builder()
                        .name("Hyuk")
                        .email("pjh0819@naver.com")
                        .build();
            }

            @Test
            @DisplayName("400(Bad Request)를 응답합니다.")
            void it_return_bad_request() throws Exception {
                mockMvc.perform(post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userDataToContent(givenInvalidUserRegistrationData)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("등록될 user의 email의 형식이 잘못되었다면")
        class Context_with_user_with_invalid_email {

            UserRegistrationData givenInvalidUserRegistrationData;

            @BeforeEach
            void prepare() {
                givenInvalidUserRegistrationData = UserRegistrationData.builder()
                        .name("Hyuk")
                        .password("!234")
                        .email("pjh0819")
                        .build();
            }

            @Test
            @DisplayName("400(Bad Request)를 응답합니다.")
            void it_return_bad_request() throws Exception {
                mockMvc.perform(post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userDataToContent(givenInvalidUserRegistrationData)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("POST /user/{id} 요청은")
    class Describe_post_id{

        @Nested
        @DisplayName("수정할 id와 user가 주어진다면")
        class Context_with_id_and_user {

            UserRegistrationData givenUserRegistrationData;
            final Long givenId= 1L;

            @BeforeEach
            void prepare() {
                givenUserRegistrationData = testUserRegistrationData.get(1);
                User user = new User();
                user.change(givenUserRegistrationData.getName(),
                        givenUserRegistrationData.getPassword(),
                        givenUserRegistrationData.getEmail());

                given(userService.updateUser(eq(givenId), any(UserRegistrationData.class)))
                        .willReturn(user);
            }

            @Test
            @DisplayName("200(ok)와 업데이트된 User를 응답합니다.")
            void it_update_user_return_ok_and_user() throws Exception {
                mockMvc.perform(post("/user/" + givenId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userDataToContent(givenUserRegistrationData)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value(givenUserRegistrationData.getName()))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("Delete /user/{id} 요청은")
    class Describe_update {

        @Nested
        @DisplayName("삭제할 id가 주어진다면")
        class Context_with_id {

            final Long givenId = 1L;

            @Test
            @DisplayName("204(No Content)과 빈값을 응답합니다.")
            void it_delete_user_return_noContent() throws Exception {
                mockMvc.perform(delete("/user/" + givenId))
                        .andExpect(status().isNoContent())
                        .andDo(print());
            }
        }
    }

    private String userDataToContent(UserRegistrationData userRegistrationData) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userRegistrationData);
    }
}
