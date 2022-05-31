package com.codesoom.assignment.controllers;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
public class UserControllerWebTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper = new ObjectMapper();

	private final UserService userService = mock(UserService.class);
	private UserController userController;

	@BeforeEach
	void setUp() {
		userController = new UserController(userService);
	}

	@Test
	void createUser() throws Exception {
		UserDTO.CreateUser createUser = new UserDTO.CreateUser("create name test", "create email test",
			"create password test");
		String source = objectMapper.writeValueAsString(createUser);
		mockMvc.perform(
				post("/users")
					.accept(MediaType.APPLICATION_JSON_UTF8)
					.contentType(MediaType.APPLICATION_JSON)
					.content(source))
			.andExpect(status().isCreated())
			.andExpect(content().string(containsString("create email test")));
		verify(userService).createUser(any(UserDTO.CreateUser.class));
	}
}
