package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.codesoom.assignment.UserTestData.TEST_SIZE;
import static com.codesoom.assignment.UserTestData.addNewUser;
import static com.codesoom.assignment.UserTestData.addNewUsers;
import static com.codesoom.assignment.UserTestData.repositoryClear;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("UserService 테스트")
class UserServiceTest {

    private UserQueryService query;
    private UserCommandService command;
    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        query = new UserQueryService(repository);
        command = new UserCommandService(repository);
    }

    @Nested
    @DisplayName("findAll()")
    class Describe_findAll {

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
        void It_ReturnUsers() {
            List<User> findUsers = query.findAll();
            assertThat(findUsers).hasSize((int) TEST_SIZE);
            for(int i = 0 ; i < users.size() ; i++){
                User user = users.get(i);
                User findUser = findUsers.get(i);
                assertThat(user).isEqualTo(findUser);
            }
        }
    }

    @Nested
    @DisplayName("save()")
    class Describe_Save{

        private final Long id = 1L;
        private User user;

        @BeforeEach
        void setUp() {
            user = addNewUser(command , id);
        }

        @AfterEach
        void tearDown() {
            repositoryClear(query , command);
        }

        @Test
        @DisplayName("저장 후 반환한다")
        void It_ReturnUser(){
            assertThat(command.save(user)).isEqualTo(user);
        }
    }

    @Nested
    @DisplayName("update()")
    class Describe_Update{

        @Nested
        @DisplayName("식별자에 해당하는 자원이 있고 수정할 정보가 있다면")
        class Context_ExistedIdAndNotNullEntity{

            private final Long id = 1L;
            private User user;
            private User updateUser;

            @BeforeEach
            void setUp() {
                user = addNewUser(command , id);
                updateUser = User.builder()
                                .id(user.getId())
                                .name("Update Name")
                                .email("Update Email")
                                .password("Update Password")
                                .build();
            }

            @AfterEach
            void tearDown() {
                repositoryClear(query , command);
            }

            @Test
            @DisplayName("해당 자원을 수정한다")
            void It_Update(){
                assertThat(command.update(user , updateUser)).isEqualTo(updateUser);
            }
        }
    }

    @Nested
    @DisplayName("delete()")
    class Describe_Delete{

        @Nested
        @DisplayName("식별자에 해당하는 자원이 있다면")
        class Context_ExistedId{

            private final Long id = 1L;
            private User user;

            @BeforeEach
            void setUp() {
                user = addNewUser(command , id);
                System.out.println(user);
            }

            @AfterEach
            void tearDown() {
                repositoryClear(query , command);
            }

            @Test
            @DisplayName("해당 자원을 삭제한다")
            void It_DeleteResource(){
                int oldUserSize = query.findAll().size();
                command.delete(user);
                int nowUserSize = query.findAll().size();
                assertThat(nowUserSize).isEqualTo(oldUserSize - 1);
            }
        }
    }
}
