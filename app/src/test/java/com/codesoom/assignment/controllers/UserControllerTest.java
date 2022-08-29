package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codesoom.assignment.UserTestData.newUser;
import static com.codesoom.assignment.UserTestData.newUsers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserController 테스트")
class UserControllerTest {
    private static final Long SIZE = 3L;

    private UserService service;
    private UserController controller;

    @BeforeEach
    void setUp() {
        service = mock(UserService.class);
        controller = new UserController(service);
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
                users = newUsers(SIZE);
                given(service.findAll()).willReturn(users);
            }

            @Test
            @DisplayName("모든 사용자를 반환한다")
            void It_ReturnUsers(){
                assertThat(controller.findAll()).hasSize(Math.toIntExact(SIZE));

                verify(service).findAll();
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
                user = newUser(1L);
                given(service.save(user)).willReturn(user);
            }

            @Test
            @DisplayName("저장 후 저장한 정보를 반환한다")
            void It_Save(){
                assertThat(controller.create(user)).isEqualTo(user);

                verify(service).save(user);
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
                given(service.update(id , updateUser)).willReturn(updateUser);
            }

            @Test
            @DisplayName("수정 후 수정된 정보를 반환한다")
            void It_UpdateUser(){
                assertThat(controller.update(id , updateUser)).isEqualTo(updateUser);

                verify(service).update(id , updateUser);
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
                user = newUser(1L);
                given(service.delete(id)).willReturn(user);
            }

            @Test
            @DisplayName("삭제 후 삭제한 정보를 반환한다")
            void It_Delete(){
                assertThat(controller.delete(id)).isEqualTo(user);

                verify(service).delete(id);
            }
        }
    }
}
