package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserCreateDto;
import com.codesoom.assignment.dto.UserRequest;
import com.codesoom.assignment.exception.NotFoundIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    private UserService userService;

    private UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void setup(){
        userService = new UserService(userRepository);

        User user= User.builder()
                .id(1L)
                .name("NAME")
                .email("EMAIL")
                .password("PASSWORD")
                .build();
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
    }

    @Test
    @DisplayName("post/user")
    public void createValid() throws Exception{
        //given
        UserCreateDto dto = UserCreateDto.builder()
                .id(1L)
                .name("name")
                .email("email")
                .password("password")
                .build();

        userService.create(dto);
        //when

        //Then
        verify(userRepository).save(any(User.class));
        assertThat(userRepository.findAll()).isNotNull();
    }

    @Test
    @DisplayName("patch /post/{id}")
    public void updateValid() throws Exception{
        //when
        UserRequest userRequest = UserRequest.builder()
                .name("update_name")
                .email("update_email")
                .password("update_password")
                .build();
        userService.update(1L , userRequest);
        //Then
        User findUser = userRepository.findById(1L)
                .orElseThrow(IllegalArgumentException::new);

        assertThat(findUser.getName()).isEqualTo("update_name");
    }

    @Test
    @DisplayName("delete /user/{id}")
    public void deleteValid() throws Exception{
        this.userService.delete(1L);
        verify(userRepository).delete(any(User.class));
    }

    @Test
    @DisplayName("delete/user{id}")
    public void deleteInValid() throws Exception{
        assertThatThrownBy(() -> userService.delete(1000L))
                .isInstanceOf(NotFoundIdException.class);
    }




}