package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.ProductData;
import com.codesoom.assignment.infra.JpaUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Autowired
    private UserRepository repository;


    @BeforeEach
    void setup() {
        this.userService = new UserService(repository);
        cleanup();
    }

    @AfterEach
    void cleanup() {
        repository.deleteAll();
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
                when(userRepository.save(any(User.class)));
            }
        }
    }

//
//    @Nested
//    @DisplayName("editUser 메소드는 ")
//    class Describe_editUser {
//
//        @Nested
//        @DisplayName("만약 찾을 수 있는 id 와 정상적인 유저 데이터를 받으면, ")
//        class Context_existing_id_and_valid_user {
//            @Test
//            @DisplayName("해당 id를 수정하고, 수정된 유저를 반환한다.")
//            void It_edit_and_return_user() {
//            }
//        }
//    }
//
//    @Nested
//    @DisplayName("removeUser 메소드는 ")
//    class Describe_removeUser {
//
//        @Nested
//        @DisplayName("만약 찾을 수 있는 id 를 받으면 ")
//        class Context_existing_id {
//            @Test
//            @DisplayName("해당 id의 유저를 삭제한다")
//            void It_remove_user() {
//            }
//        }
//    }
}
