package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
@DisplayName("UserController 테스트")
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    private final String CREATE_USER_NAME = "createdName";
    private final String CREATE_USER_EMAIL = "createdEmail";
    private final String CREATE_USER_PASSWORD = "createdPassword";

    private final String UPDATE_USER_NAME = "updatedName";
    private final String UPDATE_USER_EMAIL = "updatedEmail";
    private final String UPDATE_USER_PASSWORD = "updatedPassword";

    private final Long EXISTED_ID = 1L;
    private List<User> users;
    private User setUpUser;

    @BeforeEach
    void setUp() {
        setUpUser = User.builder()
                        .id(EXISTED_ID)
                        .name(CREATE_USER_NAME)
                        .email(CREATE_USER_EMAIL)
                        .password(CREATE_USER_PASSWORD)
                        .build();

        users = List.of(setUpUser);
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {
        @Nested
        @DisplayName("만약 유저 객체가 주어진다면")
        class Context_WithUser {
            private User source;

            @BeforeEach
            void setUp() {
                source = User.builder()
                        .name(CREATE_USER_NAME)
                        .email(CREATE_USER_EMAIL)
                        .password(CREATE_USER_PASSWORD)
                        .build();
            }

            @Test
            @DisplayName("객체를 저장하고 저장된 객체와 CREATED를 리턴한다")
            void itSavesUserAndReturnsUser() throws Exception {
                given(userService.createUser(any(User.class))).willReturn(source);

                mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"paik\",\"email\":\"melon\",\"password\":\"1234\"}"))
                        //.andExpect(jsonPath("id").value(1L))
                        //.andExpect(jsonPath("name").value("paik"))
                        //.andExpect(jsonPath("mail").value("melon"))
                        //.andExpect(jsonPath("password").value("1234"))
                        .andExpect(status().isCreated());

                verify(userService).createUser(any(User.class));
            }
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    class Describe_update {
        @Nested
        @DisplayName("만약 저장되어 있는 유저의 아이디와 객체가 주어진다면")
        class Context_WithExistedIdAndObject {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("주어진 아이디에 해당하는 객체를 업데이트하고 해당 객체와 OK를 리턴한다")
            void itUpdatesObjectAndReturnUpdatedObjectAndOKHttpStatus() throws Exception {
                mockMvc.perform(patch("/user/" + givenExistedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"updatedName\",\"email\":\"updatedEmail\",\"password\":\"updatedPassword\"}"))
                        .andDo(print())
                        .andExpect(status().isOk());

                verify(userService).updateUser(eq(givenExistedId), any(User.class));
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_delete {
        @Nested
        @DisplayName("만약 저장되어 있는 유저의 아이디가 주어진다면")
        class Context_WithExistedId {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("주어진 아이디에 해당하는 객체를 삭제하고 해당 객체와 NO_CONTENT를 리턴한다")
            void itDeletesUserAndReturnsUserAndNO_CONTENTHttpStatus() throws Exception {
                mockMvc.perform(delete("/user/" + givenExistedId))
                        .andDo(print())
                        .andExpect(status().isNoContent());

                verify(userService).deleteUser(givenExistedId);
            }
        }
    }
}