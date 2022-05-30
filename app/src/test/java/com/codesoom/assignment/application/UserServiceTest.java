package com.codesoom.assignment.application;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserDTO;

public class UserServiceTest {
	private UserRepository userRepository;
	private UserService userService;

	@BeforeEach
	void setUp() {
		UserService userService = new UserService(userRepository);
	}

	@Test
	void createUserTest() {
		UserDTO.CreateUser source = new UserDTO.CreateUser("name test","email test","password test");
		UserDTO.Response response = userService.createUser(source);
		verify(userRepository).save(any(User.class));
		assertThat(response.getName()).isEqualTo("email test");
	}
}
