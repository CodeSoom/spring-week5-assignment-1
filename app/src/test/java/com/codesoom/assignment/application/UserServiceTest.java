package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.ProductData;
import com.codesoom.assignment.dto.UserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.verify;

/*
1. getAllUsers : 완료
2. getUser : 완료
3. createUser : 완료
4. updateUser
5. deleteUser
 */
class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);

        User user = User.builder()
                .id(1L)
                .name("weno")
                .password("weno@codesoom.com")
                .password("pwd111")
                .build();

        given(userRepository.findAll()).willReturn(List.of(user));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(userRepository.save(any(User.class))).will(invocation -> {
            User source = invocation.getArgument(0);
            return User.builder()
                    .id(2L)
                    .name(source.getName())
                    .email(source.getEmail())
                    .build();
        });
    }

    @Test
    void getAllUsers() {
        List<User> users = userService.getAllUsers();
        assertThat(users).isNotEmpty();
        assertThat(users.get(0).getName()).isEqualTo("weno");
    }

    @Test
    void getUserWithExistedId(){
        User user = userService.getUser(1L);
        assertThat(user.getName()).isEqualTo("weno");
    }

    @Test
    void getUserWithNotExistedId(){
        assertThatThrownBy(()-> userService.getUser(1000L))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void createUser(){
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("weno")
                .password("weno@codesoom.com")
                .password("pwd111")
                .build();

        User user = userService.createUser(userRequestDto);

        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("weno");
    }

    @Test
    void creatUser_checkAutoIncrementId(){
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("weno")
                .password("weno@codesoom.com")
                .password("pwd111")
                .build();

        User user = userService.createUser(userRequestDto);

        verify(userRepository).save(any(User.class));
        assertThat(user.getId()).isEqualTo(2L);
    }

    @Test
    void updateWithExistedID(){
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("weno")
                .password("weno@codesoom.com")
                .password("pwd111")
                .build();

        User user = userService.updateUser(1L, userRequestDto);
        assertThat(user.getName()).isEqualTo("weno");
    }

    @Test
    void updateWithNotExistedID(){
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("weno")
                .password("weno@codesoom.com")
                .password("pwd111")
                .build();

        assertThatThrownBy(()-> userService.updateUser(1L, userRequestDto))
                .isInstanceOf(UserNotFoundException.class);
    }





}
