package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserServiceTest {

	private UserService userService;

	private UserRepository userRepository;
	private Mapper mapper;


	private Long VALID_ID = 1L;
	private Long INVALID_ID = 100L;


	@BeforeEach
	private void setUp() {
		mapper = DozerBeanMapperBuilder.buildDefault();
		userRepository = mock(UserRepository.class);
		userService = new UserService(userRepository, mapper);

		UserData user = UserData.builder()
				.id(1L)
				.name("지니")
				.email("test@gmail.com")
				.password("1234")
				.build();
		User userEntity = mapper.map(user, User.class);

		given(userRepository.findAll()).willReturn(Collections.singletonList(userEntity));
		given(userRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(userEntity));
		given(userRepository.findById(INVALID_ID)).willReturn(null);
		given(userRepository.save(userEntity)).will(invocation -> {
			User savedUser = invocation.getArgument(0);
			return User.builder()
					.id(1L)
					.name(savedUser.getName())
					.email(savedUser.getEmail())
					.password(savedUser.getPassword())
					.build();
		});
	}

	@Test
	public void create() {
		UserData user = UserData.builder()
			.name("지니")
			.email("test@gmail.com")
			.password("1234")
			.build();

		UserData savedUser = userService.create(user);

		assertThat(savedUser.getId()).isEqualTo(VALID_ID);
		assertThat(savedUser.getName()).isEqualTo("지니");

		verify(userRepository.save(any(User.class)));
	}

	@Test
	public void getAll() {
		List<User> users = userService.getAll();

		assertThat(users).hasSize(1);

		verify(userRepository).findAll();
	}

	@Test
	public void getDetail() {
		User user = userService.getDetail(VALID_ID);

		assertThat(user.getName()).isEqualTo("지니");

		verify(userRepository).findById(VALID_ID);
	}

	@Test
	public void getDetailWithNotExistedId() {
		assertThatThrownBy(() -> userService.getDetail(INVALID_ID)).isInstanceOf(UserNotFoundException.class);

		verify(userRepository).findById(INVALID_ID);
	}

	@Test
	public void updateDetail() {
		UserData user = UserData.builder()
			.name("코딩쟁이")
			.email("who@gmail.com")
			.password("1000")
			.build();

		User updatedUser = userService.updateDetail(VALID_ID, user);

		assertThat(updatedUser.getName()).isEqualTo("코딩쟁이");

		verify(userRepository).findById(VALID_ID);
	}

	@Test
	public void deleteUser() {
		userService.delete(1L);

		verify(userRepository).delete(any(User.class));
	}


}
