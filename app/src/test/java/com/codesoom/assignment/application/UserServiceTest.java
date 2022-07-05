package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.ProductData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UserServiceTest {
    private UserService userService;

    private UserRepository userRepository = mock(UserRepository.class);


    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);

        User user = User.builder()
                .name("해피해피")
                .email("test@test.com")
                .password("verysecurepassword")
                .build();


        given(userRepository.save(any(User.class))).will(invocation -> {
            User source = invocation.getArgument(0);
            return User.builder()
                    .id(2L)
                    .name(source.getName())
                    .email(source.getEmail())
                    .password(source.getPassword())
                    .build();
        });
    }


    @Nested
    @DisplayName("createUser() 메소드는 ")
    class Describe_createUser {

        @Nested
        @DisplayName("만약 정상적인 유저를 받는다면")
        class Context_valid_user {
            @Test
            @DisplayName("유저를 생성하고 반환한다. ")
            void It_create_user_then_return() {
            }
        }
    }


    @Nested
    @DisplayName("editUser 메소드는 ")
    class Describe_editUser {

        @Nested
        @DisplayName("만약 찾을 수 있는 id 와 정상적인 유저 데이터를 받으면, ")
        class Context_existing_id_and_valid_user {
            @Test
            @DisplayName("해당 id를 수정하고, 수정된 유저를 반환한다.")
            void It_edit_and_return_user() {
            }
        }
    }

    @Nested
    @DisplayName("removeUser 메소드는 ")
    class Describe_removeUser {

        @Nested
        @DisplayName("만약 찾을 수 있는 id 를 받으면 ")
        class Context_existing_id {
            @Test
            @DisplayName("해당 id의 유저를 삭제한다")
            void It_remove_user() {
            }
        }
    }
}
