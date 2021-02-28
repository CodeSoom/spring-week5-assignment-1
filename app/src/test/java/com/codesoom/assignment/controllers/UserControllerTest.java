package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
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
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
@DisplayName("UserController 테스트")
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    private WebApplicationContext wac;

    private final String CREATE_USER_NAME = "createdName";
    private final String CREATE_USER_EMAIL = "createdEmail";
    private final String CREATE_USER_PASSWORD = "createdPassword";

    private final String UPDATE_USER_NAME = "updatedName";
    private final String UPDATE_USER_EMAIL = "updatedEmail";
    private final String UPDATE_USER_PASSWORD = "updatedPassword";

    private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();
    private final Long EXISTED_ID = 1L;
    private final Long NOT_EXISTED_ID = 100L;

    private List<User> users;
    private User setUpUser;

    @BeforeEach
    void setUp() {
        mockMvc = webAppContextSetup(wac).addFilter(((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        })).build();

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
        @DisplayName("만약 사용자가 주어진다면")
        class Context_WithUser {
            private UserData userSource;
            private User createdUser;

            @BeforeEach
            void setUp() {
                userSource = UserData.builder()
                        .name(CREATE_USER_NAME)
                        .email(CREATE_USER_EMAIL)
                        .password(CREATE_USER_PASSWORD)
                        .build();

                createdUser = mapper.map(userSource, User.class);
            }

            @Test
            @DisplayName("사용자를 저장하고 저장된 사용자와 CREATED를 리턴한다")
            void itSavesUserAndReturnsUser() throws Exception {
                given(userService.createUser(any(UserData.class))).willReturn(createdUser);

                mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"createdName\",\"email\":\"createdEmail\",\"password\":\"createdPassword\"}"))
                        .andExpect(jsonPath("name").value(createdUser.getName()))
                        .andExpect(jsonPath("email").value(createdUser.getEmail()))
                        .andExpect(jsonPath("password").value(createdUser.getPassword()))
                        .andExpect(status().isCreated());

                verify(userService).createUser(any(UserData.class));
            }
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    class Describe_update {
        @Nested
        @DisplayName("만약 저장되어 있는 사용자의 아이디와 수정 할 사용자가 주어진다면")
        class Context_WithExistedIdAndUser {
            private final Long givenExistedId = EXISTED_ID;
            private UserData userSource;
            private User updatedUser;

            @BeforeEach
            void setUp() {
                userSource = UserData.builder()
                        .name(UPDATE_USER_NAME)
                        .email(UPDATE_USER_EMAIL)
                        .password(UPDATE_USER_PASSWORD)
                        .build();

                updatedUser = mapper.map(userSource, User.class);
            }

            @Test
            @DisplayName("주어진 아이디에 해당하는 사용자를 수정하고 수정된 사용자와 OK를 리턴한다")
            void itUpdatesUserAndReturnUpdatedUserAndOKHttpStatus() throws Exception {
                given(userService.updateUser(eq(givenExistedId), any(UserData.class))).willReturn(updatedUser);

                mockMvc.perform(patch("/users/" + givenExistedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"updatedName\",\"email\":\"updatedEmail\",\"password\":\"updatedPassword\"}"))
                        .andDo(print())
                        .andExpect(jsonPath("name").value(updatedUser.getName()))
                        .andExpect(jsonPath("email").value(updatedUser.getEmail()))
                        .andExpect(jsonPath("password").value(updatedUser.getPassword()))
                        .andExpect(status().isOk());

                verify(userService).updateUser(eq(givenExistedId), any(UserData.class));
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않은 사용자의 아이디가 주어진다면")
        class Context_WithNotExistedIdAndObject {
            private final Long givenNotExistedId = NOT_EXISTED_ID;

            @Test
            @DisplayName("사용자를 찾을 수 없다는 메세지와 NOT_FOUND를 리턴한다")
            void itReturnsNotFoundMessageAndNOT_FOUNDHttpStatus() throws Exception {
                given(userService.updateUser(eq(givenNotExistedId), any(UserData.class)))
                        .willThrow(new UserNotFoundException(givenNotExistedId));

                mockMvc.perform(patch("/users/"+givenNotExistedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"createdUser\" , \"email\":\"createdEmail\", \"password\":\"createdPassword\"}"))
                        .andDo(print())
                        .andExpect(content().string(containsString("User not found")))
                        .andExpect(status().isNotFound());

                verify(userService).updateUser(eq(givenNotExistedId), any(UserData.class));
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_delete {
        @Nested
        @DisplayName("만약 저장되어 있는 사용자의 아이디가 주어진다면")
        class Context_WithExistedId {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("주어진 아이디에 해당하는 사용자 삭제하고 삭제된 사용자와 NO_CONTENT를 리턴한다")
            void itDeletesUserAndReturnsDeletedUserAndNO_CONTENTHttpStatus() throws Exception {
                given(userService.deleteUser(givenExistedId)).willReturn(setUpUser);

                mockMvc.perform(delete("/users/" + givenExistedId))
                        .andDo(print())
                        .andExpect(status().isNoContent());

                verify(userService).deleteUser(givenExistedId);
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않은 사용자의 아이디가 주어진다면")
        class Context_WithNotExistedId {
            private final Long givenNotExistedId = NOT_EXISTED_ID;

            @Test
            @DisplayName("사용자를 찾을 수 없다는 메세지와 NOT_FOUND를 리턴한다")
            void itReturnsNotFoundMessageAndNOT_FOUNDHttpStatus() throws Exception {
                given(userService.deleteUser(givenNotExistedId))
                        .willThrow(new UserNotFoundException(givenNotExistedId));

                mockMvc.perform(delete("/users/"+givenNotExistedId)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(containsString("User not found")))
                        .andExpect(status().isNotFound());

                verify(userService).deleteUser(givenNotExistedId);
            }
        }
    }
}
