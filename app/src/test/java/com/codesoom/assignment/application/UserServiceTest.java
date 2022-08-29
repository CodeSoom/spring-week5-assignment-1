package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codesoom.assignment.UserTestData.newUser;
import static com.codesoom.assignment.UserTestData.newUsers;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserService 테스트")
class UserServiceTest {

    private UserService service;
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        service = new UserService(repository);
    }

    @Nested
    @DisplayName("findAll()")
    class Describe_findAll {

        private List<User> users;

        @BeforeEach
        void setUp() {
            users = newUsers(3L);
            given(repository.findAll()).willReturn(users);
        }

        @Test
        @DisplayName("모든 사용자를 반환한다")
        void It_ReturnUsers() {
            assertThat(service.findAll()).isEqualTo(users.size());

            verify(repository).findAll();
        }
    };
    @Nested
    @DisplayName("save()")
    class Describe_Save{

        private User user;

        @BeforeEach
        void setUp() {
            user = newUser(1L);
            given(repository.save(user)).willReturn(user);
        }

        @Test
        @DisplayName("저장 후 반환한다")
        void It_ReturnUser(){
            assertThat(service.save(user)).isEqualTo(user);

            verify(repository).save(user);
        }
    }
}
