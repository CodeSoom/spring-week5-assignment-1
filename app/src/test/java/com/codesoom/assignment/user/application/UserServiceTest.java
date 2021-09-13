package com.codesoom.assignment.user.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.codesoom.assignment.product.application.ProductService;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.ProductRepository;
import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.domain.UserRepository;
import com.codesoom.assignment.user.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserServiceTest {
  private final String NAME="test_name";
  private final String PASSWORD="passwword";
  private final String EMAIL="email@mail.com";

  private UserData userData;
  private UserService userService;
  private Mapper mapper;

  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    mapper = DozerBeanMapperBuilder.buildDefault();
    userService = new UserService(mapper, userRepository);

    userData = UserData.builder()
        .name(NAME)
        .password(PASSWORD)
        .email(EMAIL)
        .build();
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
  }

  @Nested
  @DisplayName("create 메소드는")
  class Describe_Create {

    private User user;

    @BeforeEach
    void setUp() {

      user = mapper.map(userData, User.class);
    }

    @Test
    @DisplayName("생성된 User를 return.")
    void It_ReturnCreatedUser() {

      User createdUser = userService.createUser(userData);

      assertThat(createdUser.getName()).isEqualTo(NAME);
      assertThat(createdUser.getPassword()).isEqualTo(PASSWORD);
      assertThat(createdUser.getEmail()).isEqualTo(EMAIL);
    }
  }
}
