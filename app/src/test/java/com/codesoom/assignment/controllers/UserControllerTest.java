package com.codesoom.assignment.controllers;

import com.codesoom.assignment.exception.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
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
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("UserController")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String userUrl = "/users";
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    UserData setUser(Long id) {
        return UserData.builder()
                .id(id)
                .email("test" + id + "@gamil.com")
                .password("testPW" + id)
                .name("테스트" + id)
                .build();
    }

    String setJSONUserById(Long id) throws JsonProcessingException {
        return setJSONUserByObj(setUser(id));
    }

    String setJSONUserByObj(UserData userData) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userData);
    }

    @Nested
    @DisplayName("userCreate 메소드는")
    class Describe_Create {
        @Nested
        @DisplayName("유저에 대한 모든 정보가 입력 된다면")
        class Context_Valid_Create {
            private String userDataJSON;
            private Long id = 1L;

            @BeforeEach
            void setUpValidCreate() throws JsonProcessingException {
                UserData sourceUserData = setUser(id);
                userDataJSON = setJSONUserByObj(sourceUserData);
                given(userService.createUser(any(UserData.class)))
                        .willReturn(sourceUserData);
            }

            @Test
            @Order(1)
            @DisplayName("Created(201)상태코드로 요청에 응답한다.")
            void create_valid() throws Exception {
                mockMvc.perform(post(userUrl)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJSON))
                        .andExpect(status().isCreated());
            }

            @Test
            @Order(2)
            @DisplayName("저장한 유저 정보를 반환한다.")
            void create_valid_user_return() throws Exception {
                mockMvc.perform(post(userUrl)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJSON))
                        .andExpect(content().string(containsString(userDataJSON)));
            }
        }

        @Nested
        @DisplayName("입력되지 않은 값이 있다면")
        class Context_Invalid_Create {
            private String userDataJson;
            private Long id = 100L;

            @BeforeEach
            void setUpInvalidCreate() throws JsonProcessingException {
                UserData sourceUserData = setUser(id);
                sourceUserData.setEmail("");
                sourceUserData.setName("");
                sourceUserData.setPassword("");

                userDataJson = setJSONUserByObj(sourceUserData);
            }

            @Test
            @DisplayName("Bad Request(400)상태코드로 요청에 응답한다.")
            void create_invalid() throws Exception {
                mockMvc.perform(post(userUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJson))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("userUpdate 메소드는")
    class Describe_Update {
        @Nested
        @DisplayName("요청한 아이디로 유저를 찾을 수 있다면")
        class Context_Valid_Update {
            private Long id = 1L;
            private String userDataJSON;
            private UserData userData;
            private String userPatchUrl = userUrl + "/" + id;

            @BeforeEach
            void setUpValidUpdate() throws JsonProcessingException {

                userData = setUser(id);
                userDataJSON = setJSONUserByObj(userData);
                given(userService.updateUser(eq(id), any(UserData.class)))
                        .willReturn(userData);
            }

            @Test
            @DisplayName("수정된 유저를 반환한다.")
            void user_valid_update_return() throws Exception {
                mockMvc.perform(patch(userPatchUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJSON))
                        .andExpect(content().string(containsString(userDataJSON)));

                then(userService).should(times(1))
                        .updateUser(eq(id), any(UserData.class));
            }
        }

        @Nested
        @DisplayName("요청한 아이디로 유저를 찾을 수 없다면")
        class Context_Invalid_Update {
            private Long id = 100L;
            private UserData userData;
            private String userDataJSON;

            @BeforeEach
            void setUpInvalidUpdate() throws JsonProcessingException {
                userData = setUser(id);
                userDataJSON = setJSONUserByObj(userData);
                given(userService.updateUser(eq(id), any(UserData.class)))
                        .willThrow(new UserNotFoundException(id));
            }

            @Test
            @DisplayName("UserNotFoundException을 던진다.")
            void user_invalid_update_exception() throws Exception {
                mockMvc.perform(patch(userUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJSON))
                        .andExpect(result -> {
                            result.getResponse()
                                    .getClass()
                                    .isInstance(UserNotFoundException.class);
                        });
            }
        }
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_Delete {
        @Nested
        @DisplayName("요청한 아이디로 유저를 찾을 수 있다면")
        class Context_Valid_Delete {
            private Long id = 1L;
            private UserData userData;
            private String userUrlDel = userUrl + "/" + id;
            private int time = 0;

            @BeforeEach
            void setUpValidDelete() {
                userData = setUser(id);
                given(userService.deleteUser(eq(id)))
                        .willReturn(userData);
            }

            @Test
            @DisplayName("No Content(204)상태코드로 요청에 응답한다.")
            @Order(1)
            void valid_delete_status() throws Exception {
                System.out.println(userUrlDel);
                mockMvc.perform(delete(userUrlDel)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
                then(userService).should(times(1))
                        .deleteUser(eq(id));
            }

            @Test
            @DisplayName("유저를 삭제한다.")
            @Order(2)
            void valid_delete_user() throws Exception {
                mockMvc.perform(delete(userUrlDel)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(containsString(String
                                .valueOf(userData.getId()))));
                then(userService).should(times(2))
                        .deleteUser(eq(id));
            }
        }

        @Nested
        @DisplayName("요청한 아이디로 유저를 찾을 수 없다면")
        class Context_Invalid_Delete {
            private Long id = 100L;
            private String userDelUrl = userUrl + "/" + id;

            @BeforeEach
            void setUPInvalidDelete() {
                given(userService.deleteUser(eq(id)))
                        .willThrow(new UserNotFoundException(id));
            }

            @Test
            @DisplayName("UserNotFoundException을 던진다.")
            void invalid_delete_user_exception() throws Exception {
                mockMvc.perform(delete(userDelUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect((result) -> {
                            result.getResponse()
                                    .getClass()
                                    .isInstance(UserNotFoundException.class);
                        });
            }

        }

    }

}
