package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserController 테스트")
class UserControllerTest {

    private UserService service;
    private UserController controller;

    private final String TEST = "TEST";
    private final Long SIZE = 3L;

    @BeforeEach
    void setUp() {
        service = mock(UserService.class);
        controller = new UserController(service);
    }

    List<User> newUsers(Long size){
        List<User> users = new ArrayList<>();
        for(long i = 0 ; i < size ; i++){
            users.add(newUser(i));
        }
        return users;
    }

    User newUser(Long number){
        return new User(number , TEST + number , TEST + number , TEST + number);
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
                user = User.builder()
                        .id(1L)
                        .name(TEST)
                        .email(TEST)
                        .password(TEST)
                        .build();
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
                user = User.builder()
                        .id(1L)
                        .name(TEST)
                        .email(TEST)
                        .password(TEST)
                        .build();
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
