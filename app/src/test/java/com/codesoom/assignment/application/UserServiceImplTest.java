package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.codesoom.assignment.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.PropertySource;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserDTO;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
class UserServiceImplTest {
	private final UserRepository userRepository = mock(UserRepository.class);
	private UserServiceImpl userServiceImpl;

	@BeforeEach
	void setUp() {
		Mapper mapper = DozerBeanMapperBuilder.buildDefault();
		userServiceImpl = new UserServiceImpl(userRepository, mapper);
		setUpFixture();
	}

	void setUpFixture() {
		User user1 = new User(1, "get name test", "get email test", "get1password@test");
		User user2 = new User(2, "get name test 2", "get email test 2", "get1password@test2");
		List<User> users = Arrays.asList(user1, user2);

		given(userRepository.save(any(User.class))).will(invocation -> {
			User source = invocation.getArgument(0);
			return User.builder()
				.id(1)
				.name(source.getName())
				.email(source.getEmail())
				.password(source.getPassword())
				.build();
		});
		given(userRepository.findAll()).willReturn(users);
		given(userRepository.findById(1)).willReturn(Optional.of(user1));
	}

	@Test
	void createUserTest() {
		UserDTO.CreateUser source = new UserDTO.CreateUser("name test", "email test", "password1@test");
		UserResponse response = userServiceImpl.createUser(source);
		verify(userRepository).save(any(User.class));
		assertThat(response.getEmail()).isEqualTo("email test");
	}

	@Test
	void getUserTest() {
		UserResponse response = userServiceImpl.getUser(1);
		verify(userRepository).findById(1);
		assertThat(response.getEmail()).isEqualTo("get email test");
	}

	@Test
	void deleteUserTest() {
		userServiceImpl.deleteUser(1);
		verify(userRepository).deleteById(1);
	}

	@Test
	void getUsersTest() {
		List<UserResponse> users = userServiceImpl.getUsers();
		verify(userRepository).findAll();
		assertThat(users).hasSize(2);
	}

	@Test
	void updateUserTest() {
		UserDTO.UpdateUser source = new UserDTO.UpdateUser("update name test", "update email test",
			"update1password@test");
		UserResponse response = userServiceImpl.updateUsers(1, source);
		assertThat(response.getEmail()).isEqualTo("update email test");
	}
}
