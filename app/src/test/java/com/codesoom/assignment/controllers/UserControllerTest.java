package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserCommandService;
import com.codesoom.assignment.application.UserQueryService;
import com.codesoom.assignment.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.codesoom.assignment.UserTestData.TEST_SIZE;
import static com.codesoom.assignment.UserTestData.addNewUser;
import static com.codesoom.assignment.UserTestData.addNewUsers;
import static com.codesoom.assignment.UserTestData.repositoryClear;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("UserController 테스트")
class UserControllerTest {

    private MockMvc mvc;
    @Autowired
    private UserController controller;
    @Autowired
    private UserCommandService command;
    @Autowired
    private UserQueryService query;
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new NotFoundErrorAdvice())
                .build();
    }

    @Nested
    @DisplayName("findAll()")
    class Describe_FindAll{

        @Nested
        @DisplayName("사용자가 존재 한다면")
        class Context_ExistedUsers{

            private List<User> users;

            @BeforeEach
            void setUp() {
                users = addNewUsers(command , TEST_SIZE);
            }

            @AfterEach
            void tearDown() {
                repositoryClear(query , command);
            }

            @Test
            @DisplayName("모든 사용자를 반환한다.")
            void It_ReturnUsers() throws Exception {
                String content = mapper.writeValueAsString(users);
                System.out.println(content);
                mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(status().isOk())
                        .andExpect(content().string(equalTo(content)));
            }
        }
    }

    @Nested
    @DisplayName("create()")
    class Describe_Create{

        @Nested
        @DisplayName("사용자의 이름이 null이거나 공백이라면")
        class Context_NameIsNullOrBlank{

            private String nameNullContent = "{\"email\":\"new email\",\"password\":\"new password\"}";
            private String nameBlnakContent = "{\"name\":\"\",\"email\":\"new email\",\"password\":\"new password\"}";

            @Test
            @DisplayName("잘못된 요청이라는 예외를 던진다.")
            void It_ThrowsException() throws Exception {
                mvc.perform(post("/users")
                                .content(nameNullContent)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
                mvc.perform(post("/users")
                                .content(nameBlnakContent)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("사용자의 이메일이 null이거나 공백이라면")
        class Context_EmailIsNullOrBlank{

            private String emailNullContent = "{\"name\":\"new name\",\"password\":\"new password\"}";
            private String emailBlnakContent = "{\"name\":\"new name\",\"email\":\"\",\"password\":\"new password\"}";

            @Test
            @DisplayName("잘못된 요청이라는 예외를 던진다.")
            void It_ThrowsException() throws Exception {
                mvc.perform(post("/users")
                                .content(emailNullContent)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
                mvc.perform(post("/users")
                                .content(emailBlnakContent)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("사용자의 비밀번호가 null이거나 공백이라면")
        class Context_PasswordIsNullOrBlank{

            private String passwordNullContent = "{\"name\":\"new name\",\"email\":\"new email\"}";
            private String passwordBlnakContent = "{\"name\":\"new name\",\"email\":\"new email\",\"password\":\"\"}";

            @Test
            @DisplayName("잘못된 요청이라는 예외를 던진다.")
            void It_ThrowsException() throws Exception {
                mvc.perform(post("/users")
                                .content(passwordNullContent)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
                mvc.perform(post("/users")
                                .content(passwordBlnakContent)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("필수 입력 검증을 통과한다면")
        class Context_PassValidation{

            private User user;
            private final String content = "{\"name\":\"new name\",\"email\":\"new email\",\"password\":\"new password\"}";

            @AfterEach
            void tearDown() {
                repositoryClear(query , command);
            }

            @Test
            @DisplayName("저장된 정보와 자원이 생성되었다는 응답을 반환한다.")
            void It_Save() throws Exception {
                user = mapper.readValue(content , User.class);

                MvcResult mvcResult = mvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .content(content))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andReturn();

                User returnUser = mapper.readValue(mvcResult.getResponse().getContentAsString() , User.class);
                assertThat(returnUser.getEmail()).isEqualTo(user.getEmail());
                assertThat(returnUser.getName()).isEqualTo(user.getName());
            }
        }
    }

    @Nested
    @DisplayName("update()")
    class Describe_Update{

        @Nested
        @DisplayName("{id}에 해당하는 자원이 있고 , 수정할 정보가 비어있지 않다면")
        class Context_ExistedIdAndNotEmptyBody{

            private Long id = 1L;
            private User oldUser;
            private String updateContent;
            private User updateUser;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                oldUser = addNewUser(command , id);
                updateContent = "{\"name\":\"new name\",\"email\":\"new email\",\"password\":\"new password\"}";
                updateUser = mapper.readValue(updateContent , User.class);
            }

            @AfterEach
            void tearDown() {
                repositoryClear(query , command);
            }

            @Test
            @DisplayName("수정 후 수정된 정보를 반환한다.")
            void It_UpdateUser() throws Exception {
                MvcResult mvcResult = mvc.perform(patch("/users/" + oldUser.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateContent))
                        .andExpect(status().isOk())
                        .andReturn();
                User returnUser = mapper.readValue(mvcResult.getResponse().getContentAsString() , User.class);
                assertThat(returnUser.getName()).isEqualTo(updateUser.getName());
                assertThat(returnUser.getEmail()).isEqualTo(updateUser.getEmail());
            }
        }

        @Nested
        @DisplayName("{id}에 해당하는 사용자가 존재하지 않는다면")
        class Context_NotExistedId{

            private Long id = 1L;
            private String updateContent;

            @BeforeEach
            void setUp() {
                repositoryClear(query , command);
                updateContent = "{\"name\":\"new name\",\"email\":\"new email\",\"password\":\"new password\"}";
            }

            @Test
            @DisplayName("자원이 존재하지 않는다는 예외를 던진다.")
            void It_ThrowException() throws Exception {
                mvc.perform(patch("/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateContent))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("delete()")
    class Describe_Delete{

        @Nested
        @DisplayName("{id}에 해당하는 사용자가 존재한다면")
        class Context_ExistedId{

            private User user;

            @BeforeEach
            void setUp() {
                user = addNewUser(command ,1L);
            }

            @AfterEach
            void tearDown() {
                repositoryClear(query , command);
            }

            @Test
            @DisplayName("삭제를 수행하고, 삭제했음을 의미하는 응답을 돌려준다.")
            void It_Delete() throws Exception {
                mvc.perform(delete("/users/" + user.getId()))
                        .andExpect(status().isNoContent());
            }
        }
    }
}
