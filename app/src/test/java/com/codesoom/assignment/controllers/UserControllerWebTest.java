package com.codesoom.assignment.controllers;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
class UserControllerWebTest {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@BeforeEach
	void setUp() {
		UserDTO.Response response1 = new UserDTO.Response(1, "name test",
			"email test", "password test");
		UserDTO.Response response2 = new UserDTO.Response(2, "name test 2",
			"email test 2", "password test 2");
		List<UserDTO.Response> userDTOs = Arrays.asList(response1, response2);

		UserDTO.UpdateUser updateUser = new UserDTO.UpdateUser("update name test", "update email test",
			"update password test");

		given(userService.getUser(1)).willReturn(response1);
		given(userService.getUsers()).willReturn(userDTOs);

		given(userService.createUser(any(UserDTO.CreateUser.class)))
			.will(invocation -> {
				UserDTO.CreateUser source = invocation.getArgument(0);
				return new UserDTO.Response(1, source.getName(), source.getEmail(),
					source.getPassword());
			});

		given(userService.updateUsers(eq(1), any(UserDTO.UpdateUser.class)))
			.will(invocation -> {
				int id = invocation.getArgument(0);
				UserDTO.UpdateUser source = invocation.getArgument(1);
				return UserDTO.Response.builder()
					.id(id)
					.name(source.getName())
					.email(source.getEmail())
					.password(source.getPassword())
					.build();
			});
	}

	@Test
	void createUser() throws Exception {
		String source = objectMapper.writeValueAsString(new UserDTO.CreateUser("create name test",
			"create email test", "create password test"));

		mockMvc.perform(
				post("/users")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(source))
			.andExpect(status().isCreated())
			.andExpect(content().string(containsString("create email test")));

		verify(userService).createUser(any(UserDTO.CreateUser.class));
	}

	@Test
	void updateUser() throws Exception {
		String source = objectMapper.writeValueAsString(
			new UserDTO.UpdateUser("update name test", "update email test", "update password test"));

		mockMvc.perform(
				patch("/users/{id}", 1)
					.accept(MediaType.APPLICATION_JSON_UTF8)
					.contentType(MediaType.APPLICATION_JSON)
					.content(source))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("update email test")));

		verify(userService).updateUsers(eq(1), any(UserDTO.UpdateUser.class));
	}

	@Test
	void getUser() throws Exception {
		mockMvc.perform(get("/users/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("name test")));

		verify(userService).getUser(1);
	}

	@Test
	void getUsers() throws Exception {
		mockMvc.perform(get("/users"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("email test 2")))
			.andExpect(content().string(containsString("email test 1")));

		verify(userService).getUsers();
	}
}
