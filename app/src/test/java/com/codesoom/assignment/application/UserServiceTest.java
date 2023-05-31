package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

class UserServiceTest {

	private UserService userService;
	@MockBean
	private UserRepository userRepository;
	private Mapper mapper;


	private Long VALID_ID = 1L;
	private Long INVALID_ID = 100L;


	@BeforeEach
	private void setUp() {
		mapper = DozerBeanMapperBuilder.buildDefault();
		userService = new UserService(userRepository, mapper);
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
	}

	@Test
	public void getAll() {
		List<User> users = userService.getAll();
//
		assertThat(users).hasSize(1);
	}

	@Test
	public void getDetail() {
		User user = userService.getDetail(VALID_ID);

		assertThat(user.getName()).isEqualTo("지니");
	}

	@Test
	public void getDetailWithNotExistedId() {
		assertThatThrownBy(() -> userService.getDetail(INVALID_ID)).isInstanceOf(UserNotFoundException.class);
	}

	@Test
	public void updateDetail() {
		UserData user = UserData.builder()
			.name("코딩쟁이")
			.email("who@gmail.com")
			.password("1000")
			.build();

		UserData updatedUser = userService.updateDetail(VALID_ID, user);

		assertThat(updatedUser.getName()).isEqualTo("코딩쟁이");
	}

	@Test
	public void deleteUser() {
		userService.delete(1L);
	}


}