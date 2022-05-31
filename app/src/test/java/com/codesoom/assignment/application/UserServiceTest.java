package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;
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
		User user1 = new User(1, "get name test", "get email test", "get password test");
		User user2 = new User(2, "get name test 2", "get email test 2", "get password test 2");

		List<User> users = Arrays.asList(user1, user2);

		given(userRepository.findAll()).willReturn(users);
		given(userRepository.findById(1)).willReturn(Optional.of(user1));
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

	@Test
	void getUsersTest() {
		List<User> users = userService.getUsers();
		verify(userRepository).findAll();
		assertThat(users.size()).isEqualTo(2);
	}
}
