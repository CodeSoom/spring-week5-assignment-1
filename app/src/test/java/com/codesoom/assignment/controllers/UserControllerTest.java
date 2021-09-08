package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserCreateDTO;
import com.codesoom.assignment.dto.UserUpdateDTO;
import com.codesoom.assignment.exception.UserNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("UserController")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper;
    private Mapper mapper;

    private final Long VALID_ID = 1L;
    private final Long INVALID_ID = 9999L;

    private User correctUser;
    private User blankNameUser;
    private User blankPasswordUser;
    private User blankEmailUser;

    @BeforeEach
    void setUp()  {
        objectMapper = new ObjectMapper();
        mapper = DozerBeanMapperBuilder.buildDefault();

        correctUser = new User(VALID_ID, "이름1", "패스워드1", "이메일1");
        blankNameUser = User.builder().name(" ").password("패스워드1").email("이메일1").build();
        blankPasswordUser = User.builder().name("이름1").password(" ").email("이메일1").build();

        given(userService.create(any(UserCreateDTO.class))).will(invocation -> {
            return mapper.map(invocation.getArgument(0), User.class);
        });

        given(userService.update(eq(VALID_ID), any(UserUpdateDTO.class))).will(invocation -> {
            UserUpdateDTO userUpdateDTO = invocation.getArgument(1);
            User user = mapper.map(userUpdateDTO, User.class);
            user.setId(VALID_ID);
            return user;
        });

        given(userService.update(eq(INVALID_ID), any(UserUpdateDTO.class)))
                .willThrow(new UserNotFoundException(INVALID_ID));

        willDoNothing().given(userService).delete(VALID_ID);
        willThrow(new UserNotFoundException(INVALID_ID)).given(userService).delete(INVALID_ID);
    }

    @Nested
    @DisplayName("POST /user 호출")
    class Describe_POST_user {

        @Nested
        @DisplayName("올바른 데이터를 전달하면")
        class Context_with_correct_data {

            private UserCreateDTO correctUserCreateDTO;

            @BeforeEach
            void prepare() {
                correctUserCreateDTO = mapper.map(correctUser, UserCreateDTO.class);
            }

            @Test
            @DisplayName("status: Created, data: new user를 응답합니다.")
            void it_response_ok() throws Exception {
                mockMvc.perform(post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeContent(correctUserCreateDTO)))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(makeContent(correctUserCreateDTO)));
            }
        }

        @Nested
        @DisplayName("이름이 비어있는 유저 데이터를 전달하면")
        class Context_with_blank_name_user {

            private UserCreateDTO blankNameUserCreateDTO;

            @BeforeEach
            void prepare() {
                blankNameUserCreateDTO = mapper.map(blankNameUser, UserCreateDTO.class);
            }

            @Test
            @DisplayName("status: Bad request를 응답합니다.")
            void it_response_bad_request() throws Exception {
                mockMvc.perform(post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeContent(blankNameUserCreateDTO)))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("패스워스가 비어있는 유저 데이터를 전달하면")
        class Context_with_blank_password_user {

            private UserCreateDTO blankPasswordUserCreateDTO;

            @BeforeEach
            void prepare() {
                blankPasswordUserCreateDTO = mapper.map(blankPasswordUser, UserCreateDTO.class);
            }

            @Test
            @DisplayName("status: Bad request를 응답합니다.")
            void it_response_bad_request() throws Exception {
                mockMvc.perform(post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeContent(blankPasswordUserCreateDTO)))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("PATCH /user/{id} 호출")
    class Describe_PATCH_user_id {

        @Nested
        @DisplayName("저장된 유저 id를 전달하면")
        class Context_with_existing_user {

            private UserUpdateDTO correctUserUpdateDTO;

            @BeforeEach
            void prepare() {
                correctUserUpdateDTO = mapper.map(correctUser, UserUpdateDTO.class);
            }

            @Test
            @DisplayName("status: Ok, data: updated user를 응합니다.")
            void it_response_ok() throws Exception {
                mockMvc.perform(patch("/users/" + VALID_ID)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeContent(correctUserUpdateDTO)))
                        .andExpect(status().isOk())
                        .andExpect(content().string(makeContent(correctUserUpdateDTO)));
            }
        }

        @Nested
        @DisplayName("저장되지 않은 유저 id를 전달하면")
        class Context_with_not_existing_user {

            private UserUpdateDTO correctUserUpdateDTO;

            @BeforeEach
            void prepare() {
                correctUserUpdateDTO = mapper.map(correctUser, UserUpdateDTO.class);
            }

            @Test
            @DisplayName("status: Not found를 응답합니다.")
            void it_response_not_found() throws Exception {
                System.out.println(makeContent(correctUserUpdateDTO));
                mockMvc.perform(patch("/users/" + INVALID_ID)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeContent(correctUserUpdateDTO)))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("이름이 비어있는 유저 데이터를 전달하면")
        class Context_with_blank_name_user {

            private UserUpdateDTO blankNameUserUpdateDTO;

            @BeforeEach
            void prepare() {
                blankNameUserUpdateDTO = mapper.map(blankNameUser, UserUpdateDTO.class);
            }

            @Test
            @DisplayName("status: Bad request를 응답합니다.")
            void it_response_bad_request() throws Exception {
                mockMvc.perform(post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeContent(blankNameUserUpdateDTO)))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("패스워스가 비어있는 유저 데이터를 전달하면")
        class Context_with_blank_password_user {

            private UserUpdateDTO blankPasswordUserUpdateDTO;

            @BeforeEach
            void prepare() {
                blankPasswordUserUpdateDTO = mapper.map(blankPasswordUser, UserUpdateDTO.class);
            }

            @Test
            @DisplayName("status: Bad request를 응답합니다.")
            void it_response_bad_request() throws Exception {
                mockMvc.perform(post("/users")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeContent(blankPasswordUserUpdateDTO)))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("DELETE /user/{id} 호출")
    class Describe_DELETE_user_id {
        @Nested
        @DisplayName("저장된 유저 id를 전달하면")
        class Context_with_existing_user {

            @Test
            @DisplayName("status: No content, data: updated user를 응답합니다.")
            void it_response_ok() throws Exception {
                mockMvc.perform(delete("/users/" + VALID_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("저장되지 않은 유저 id를 전달하면")
        class Context_with_not_existing_user {

            @Test
            @DisplayName("status: Not found를 응답합니다.")
            void it_response_not_found() throws Exception {
                mockMvc.perform(delete("/users/" + INVALID_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }
        }
    }

    private String makeContent(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}