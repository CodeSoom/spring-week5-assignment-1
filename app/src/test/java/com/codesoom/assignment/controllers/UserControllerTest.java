package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserCommandService;
import com.codesoom.assignment.application.UserQueryService;
import com.codesoom.assignment.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.codesoom.assignment.UserTestData.TEST_SIZE;
import static com.codesoom.assignment.UserTestData.addNewUsers;
import static com.codesoom.assignment.UserTestData.repositoryClear;
import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @DisplayName("findAll 메서드는")
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
            @DisplayName("모든 사용자를 반환한다")
            void It_ReturnUsers() throws Exception {
                String content = mapper.writeValueAsString(users);
                System.out.println(content);
                mvc.perform(get("/user"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(equalTo(content)));
            }
        }
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_Create{

        @Nested
        @DisplayName("필수 입력 항목이 다 존재한다면")
        class Context_PassValidation{

            private User user;

            @BeforeEach
            void setUp() {
            }

            @Test
            @DisplayName("저장 후 저장한 정보를 반환한다")
            void It_Save(){
                fail("미구현된 테스트입니다.");
            }
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    class Describe_Update{

        @Nested
        @DisplayName("{id}에 해당하는 자원이 있고 , 수정할 정보가 비어있지 않다면")
        class Context_ExistedIdAndNotEmptyBody{

            private final Long id = 1L;
            private User updateUser;

            @BeforeEach
            void setUp() {
                updateUser = User.builder()
                                .id(id)
                                .name("UPDATE")
                                .email("UPDATE")
                                .password("UPDATE")
                                .build();
            }

            @Test
            @DisplayName("수정 후 수정된 정보를 반환한다")
            void It_UpdateUser(){
                fail("미구현된 테스트입니다.");
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_Delete{

        @Nested
        @DisplayName("{id}에 해당하는 자원이 존재한다면")
        class Context_ExistedId{

            private final Long id = 1L;
            private User user;

            @BeforeEach
            void setUp() {
            }

            @Test
            @DisplayName("삭제 후 삭제한 정보를 반환한다")
            void It_Delete(){
                fail("미구현된 테스트입니다.");
            }
        }
    }
}
