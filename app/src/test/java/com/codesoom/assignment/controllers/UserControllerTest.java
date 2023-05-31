package com.codesoom.assignment.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private UserService userService;
	private ObjectMapper objectMapper;

	@BeforeEach
	private void setUp() {
		objectMapper = new ObjectMapper();

		UserData user = UserData.builder()
			.name("지니")
			.email("test@gmail.com")
			.password("1234")
			.build();

		given(userService.getAll()).willReturn((Collections.singletonList(user)));
		given(userService.getDetail(1L)).willReturn(user);
		given(userService.getDetail(100L)).willThrow(UserNotFoundException.class);
		given(userService.create(any(UserData.class))).will(invocation -> {
			UserData createdUser = invocation.getArgument(0);
			createdUser.setId(1L);
			return createdUser;
		});
		given(userService.updateDetail(eq(1L), any(UserData.class))).will(invocation -> {
			UserData updatedUser = invocation.getArgument(1);
			updatedUser.setId(1L);
			return updatedUser;
		});

		doNothing().when(userService).delete(any(Long.class));
	}


	@Test
	public void create() throws Exception {
		UserData user = UserData.builder()
				.name("지니")
				.email("test@gmail.com")
				.password("1234")
				.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/users")
						.accept(MediaType.APPLICATION_JSON_UTF8)
						.contentType(MediaType.APPLICATION_JSON)
						.content(getJsonString(user)))
				.andExpect(status().isCreated())
				.andExpect(content().string(containsString("지니")));

		verify(userService).create(any(UserData.class));
	}

	@Test
	public void createWithInvalidValue() throws Exception {
		UserData user = UserData.builder()
			.name("")
			.email("test@gmail.com")
			.password("1234")
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(getJsonString(user)))
			.andExpect(status().isBadRequest());

		verify(userService, never()).create(any(UserData.class));
	}

	@Test
	public void getAll() throws Exception {
		mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("지니")));

		verify(userService).getAll();
	}

	@Test
	public void getDetailWithExsistedId() throws Exception {
		mockMvc.perform(get("/users/1"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("지니")));

		verify(userService).getDetail(1L);
	}

	@Test
	public void getDetailWithNotExsistedId() throws Exception {
		mockMvc.perform(get("/users/100"))
				.andExpect(status().isNotFound());

		verify(userService).getDetail(100L);
	}

	@Test
	public void updateWithValidData() throws Exception {
		UserData user = UserData.builder()
				.name("코딩쟁이")
				.email("who@gmail.com")
				.password("1000")
				.build();

		mockMvc.perform(patch("/users/1")
						.accept(MediaType.APPLICATION_JSON_UTF8)
						.contentType(MediaType.APPLICATION_JSON)
						.content(getJsonString(user)))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("코딩쟁이")));

		verify(userService).updateDetail(eq(1L), any(UserData.class));
	}

	@Test
	public void updateWithInValidData() throws Exception {
		UserData user = UserData.builder()
				.name("")
				.email("")
				.password("")
				.build();

		mockMvc.perform(patch("/users/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getJsonString(user)))
				.andExpect(status().isBadRequest());

		verify(userService, never()).updateDetail(eq(1L), any(UserData.class));
	}

	@Test
	public void remove() throws Exception {
		mockMvc.perform(delete("/users/1"))
				.andExpect(status().isNoContent());

		verify(userService).delete(1L);
	}

	@Test
	public void getJsonStringTest() throws JsonProcessingException {
		UserData user = UserData.builder()
				.id(1L)
				.name("지니")
				.email("test@gmail.com")
				.password("1234")
				.build();

		assertThat(getJsonString(user)).isEqualTo("{\"id\":1,\"name\":\"지니\",\"email\":\"test@gmail.com\",\"password\":\"1234\"}");
	}

	public String getJsonString(UserData userData) throws JsonProcessingException {
		return objectMapper.writeValueAsString(userData);
	}

}
