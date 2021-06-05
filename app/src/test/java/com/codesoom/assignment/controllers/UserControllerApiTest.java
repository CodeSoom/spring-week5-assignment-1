package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.userdata.UserCreateData;
import com.codesoom.assignment.dto.userdata.UserUpdateData;
import com.codesoom.assignment.exception.UserNotFoundException;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Nested
@DisplayName("UserController API는")
@WebMvcTest(UserController.class)
public class UserControllerApiTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    @DisplayName("GET list 요청에 대해")
    class DescribeGETList {

        private List<User> users;

        @Nested
        @DisplayName("유저가 존재할 때")
        class ContextWithExistedUsers {

            @BeforeEach
            void userSetUp() {
                User user1 = User.builder()
                        .name("Jack")
                        .email("jack@email.com")
                        .password("qwer1234")
                        .build();

                User user2 = User.builder()
                        .name("Wilson")
                        .email("wilson@email.com")
                        .password("qwer1234")
                        .build();

                users = new ArrayList<>();
                users.add(user1);
                users.add(user2);

                given(userService.getUsers())
                        .willReturn(users);
            }

            @Test
            @DisplayName("유저 목록과 OK status를 반환합니다")
            void ItReturnsOkWithUserList() throws Exception {
                String expected = objectMapper.writeValueAsString(users);
                mockMvc.perform(get("/users"))
                        .andExpect(status().isOk())
                        .andExpect(content().json(expected, true));
            }
        }

        @Nested
        @DisplayName("유저가 존재하지 않을 때")
        class ContextWithoutExistedUsers {

            @BeforeEach
            void userSetUp() {
                users = new ArrayList<>();
                given(userService.getUsers())
                        .willReturn(users);
            }

            @Test
            @DisplayName("빈 목록과 OK status를 반환합니다")
            void ItReturnsOkWithEmptyList() throws Exception {
                ArrayList<User> emptyList = new ArrayList<>();
                String expected = objectMapper.writeValueAsString(emptyList);
                mockMvc.perform(get("/users"))
                        .andExpect(status().isOk())
                        .andExpect(content().json(expected, true));
            }
        }
    }

    @Nested
    @DisplayName("GET detail 요청에 대해")
    class DescribeGETDetail {

        @Nested
        @DisplayName("식별자를 가진 유저가 있으면")
        class ContextWithExistedId {

            private User user;

            @BeforeEach
            void userSetUp() {
                this.user = User.builder()
                        .id(1L)
                        .name("Jack")
                        .email("jack@email.com")
                        .password("qwer1234")
                        .build();
                given(userService.getUser(1L))
                        .willReturn(this.user);
            }

            @Test
            @DisplayName("유저와 OK status를 반환합니다")
            void ItReturnsOkWithUser() throws Exception {
                String expected = objectMapper.writeValueAsString(user);
                mockMvc.perform(get("/users/1"))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().json(expected, true));
            }
        }

        @Nested
        @DisplayName("식별자를 가진 유저가 없으면")
        class ContextWithoutExistedId {

            @BeforeEach
            void userSetUp() {
                given(userService.getUser(100L))
                        .willThrow(UserNotFoundException.class);
            }

            @Test
            @DisplayName("NotFound status를 반환합니다")
            void ItReturnNotFoundStatus() throws Exception {
                mockMvc.perform(get("/users/100"))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("POST create 요청에 대해")
    class DescribePOSTCreate {

        @Nested
        @DisplayName("유저 정보가 타당하다면")
        class ContextWithValidData {

            private UserCreateData userCreateData;
            private User user;

            @BeforeEach
            void userDataSetup() {
                this.userCreateData = UserCreateData.builder()
                        .name("Jack")
                        .email("jack@email.com")
                        .password("qwer1234")
                        .build();

                this.user = User.builder()
                        .id(1L)
                        .name(userCreateData.getName())
                        .email(userCreateData.getEmail())
                        .password(userCreateData.getPassword())
                        .build();

                given(userService.createUser(any(UserCreateData.class)))
                        .willReturn(this.user);
            }

            @Test
            @DisplayName("유저와 Created status를 반환합니다")
            void ItReturnsCreatedWithUser() throws Exception {
                String userData = objectMapper.writeValueAsString(this.userCreateData);
                String expected = objectMapper.writeValueAsString(this.user);
                mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userData))
                        .andExpect(status().isCreated())
                        .andExpect(content().json(expected, true));
            }
        }

        @Nested
        @DisplayName("유저 정보가 부족하다면")
        class ContextWithShortData {

            private UserCreateData userCreateData;

            @BeforeEach
            void userDataSetUp() {
                this.userCreateData = UserCreateData.builder()
                        .password("qwer1234")
                        .build();
            }

            @Test
            @DisplayName("BadRequest status를 반환합니다")
            void ItReturnsBadRequest() throws Exception {
                String userData = objectMapper.writeValueAsString(this.userCreateData);
                mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userData))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("유저 정보가 타당하지 않다면")
        class ContextWithInvalidData {

            private UserCreateData userCreateData;

            @BeforeEach
            void userDataSetUp() {
                this.userCreateData = UserCreateData.builder()
                        .name("Jack")
                        .email("jackemail.com")
                        .password("qwer1234")
                        .build();
            }

            @Test
            @DisplayName("BadRequest를 반환합니다")
            void ItReturnsBadRequest() throws Exception {
                String userData = objectMapper.writeValueAsString(this.userCreateData);
                mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userData))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("PATCH update 요청에 대해")
    class DescribePATCHUpdate {

        @Nested
        @DisplayName("유저 정보가 타당하다면")
        class ContextWithValidData {

            private UserUpdateData userUpdateData;
            private User editedUser;

            @BeforeEach
            void userDataSetUp() {
                this.userUpdateData = UserUpdateData.builder()
                        .name("Jack")
                        .email("jack@email.com")
                        .password("qwer1234")
                        .build();
                this.editedUser = User.builder()
                        .id(1L)
                        .name(this.userUpdateData.getName())
                        .email(this.userUpdateData.getEmail())
                        .password(this.userUpdateData.getPassword())
                        .build();
                given(userService.updateUser(eq(1L), any(UserUpdateData.class)))
                        .willReturn(this.editedUser);
            }

            @Test
            @DisplayName("수정된 유저와 OK status를 반환합니다")
            void ItReturnsOkWithEditedUser() throws Exception {
                String userData = objectMapper.writeValueAsString(this.userUpdateData);
                String expected = objectMapper.writeValueAsString(this.editedUser);
                mockMvc.perform(patch("/users/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userData))
                        .andExpect(status().isOk())
                        .andExpect(content().json(expected, true));
            }
        }

        @Nested
        @DisplayName("이름 정보가 없다면")
        class ContextWithoutName {

            private UserUpdateData userUpdateData;

            @BeforeEach
            void userDataSetUp() {
                this.userUpdateData = UserUpdateData.builder()
                        .email("jack@email.com")
                        .password("qwer1234")
                        .build();
            }

            @Test
            @DisplayName("BadRequest를 반환합니다")
            void ItReturnsBadRequest() throws Exception {
                String userData = objectMapper.writeValueAsString(this.userUpdateData);
                mockMvc.perform(patch("/users/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userData))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("email 정보가 없다면")
        class ContextWithoutEmail {

            private UserUpdateData userUpdateData;

            @BeforeEach
            void userDataSetUp() {
                this.userUpdateData = UserUpdateData.builder()
                        .name("Jack")
                        .password("qwer1234")
                        .build();
            }

            @Test
            @DisplayName("BadRequest를 반환합니다")
            void ItReturnsOkWithEditedUser() throws Exception {
                String userData = objectMapper.writeValueAsString(this.userUpdateData);
                mockMvc.perform(patch("/users/1L")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userData))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("email 정보가 형식에 맞지 않다면")
        class ContextWithInvalidEmail {

            private UserUpdateData userUpdateData;

            @BeforeEach
            void userDataSetUp() {
                this.userUpdateData = UserUpdateData.builder()
                        .name("Jack")
                        .email("jackemail.com")
                        .password("qwer1234")
                        .build();
            }

            @Test
            @DisplayName("BadRequest를 반환합니다")
            void ItReturnsBadRequest() throws Exception {
                String userData = objectMapper.writeValueAsString(this.userUpdateData);
                mockMvc.perform(patch("/users/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userData))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("비밀번호 정보가 없다면")
        class ContextWithoutPassword {

            private UserUpdateData userUpdateData;

            @BeforeEach
            void userDataSetUp() {
                this.userUpdateData = UserUpdateData.builder()
                        .name("Jack")
                        .email("jack@email.com")
                        .build();
            }

            @Test
            @DisplayName("BadRequest를 반환합니다")
            void ItReturnsOkWithEditedUser() throws Exception {
                String userData = objectMapper.writeValueAsString(this.userUpdateData);
                mockMvc.perform(patch("/users/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userData))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("DELETE destroy 요청에 대해")
    class DescribeDELETEDestroy {

        @Nested
        @DisplayName("식별자를 가진 유저가 있으면")
        class ContextWithExistedId {

            @BeforeEach
            void userSetUp() {
                User user = User.builder()
                        .id(1L)
                        .name("Jack")
                        .email("jack@email.com")
                        .password("qwer1234")
                        .build();
                given(userService.deleteUser(1L))
                        .willReturn(user);
            }

            @Test
            @DisplayName("유저를 삭제하고 NoContent를 반환합니다")
            void ItReturnsNoContentWithUser() throws Exception {
                mockMvc.perform(delete("/users/1"))
                        .andExpect(status().isNoContent());
                verify(userService).deleteUser(1L);
            }
        }

        @Nested
        @DisplayName("식별자를 가진 유저가 없다면")
        class ContextWithoutExistedId {

            @BeforeEach
            void userDeleteSetUp() {
                given(userService.deleteUser(100L))
                        .willThrow(UserNotFoundException.class);
            }

            @Test
            @DisplayName("NotFound status를 반환합니다")
            void ItReturnsNotFound() throws Exception {
                mockMvc.perform(delete("/users/100"))
                        .andExpect(status().isNotFound());
            }
        }
    }
}
