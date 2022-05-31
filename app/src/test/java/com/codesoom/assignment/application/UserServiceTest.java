package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserDTO;

public class UserServiceTest {
	private UserRepository userRepository = mock(UserRepository.class);
	private UserService userService;

	@BeforeEach
	void setUp() {
		userService = new UserService(userRepository);
		given(userRepository.save(any(User.class))).will(invocation -> {
			User source = invocation.getArgument(0);
			return User.builder()
				.id(1)
				.name(source.getName())
				.email(source.getEmail())
				.password(source.getPassword())
				.build();
		});
		User user = new User(1,"get name test","get email test","get password test");
		given(userRepository.findById(1)).willReturn(Optional.of(user));
	}

	@Test
	void createUserTest() {
		UserDTO.CreateUser source = new UserDTO.CreateUser("name test", "email test", "password test");
		UserDTO.Response response = userService.createUser(source);
		verify(userRepository).save(any(User.class));
		assertThat(response.getEmail()).isEqualTo("email test");
	}
	@Test
	void getUserTest() {
		UserDTO.Response response = userService.getUser(1);
		verify(userRepository).findById(1);
		assertThat(response.getEmail()).isEqualTo("get email test");
	}
	@Test
	void deleteUserTest() {
		userService.deleteUser(1);
		verify(userRepository).deleteById(1);
	}
}
