package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private UserService userService;
	private ObjectMapper objectMapper;
	private Mapper mapper;

	@BeforeEach
	private void setUp() {
		objectMapper = new ObjectMapper();
		mapper = DozerBeanMapperBuilder.buildDefault();

		UserData user = UserData.builder()
				.id(1L)
				.name("지니")
				.email("test@gmail.com")
				.password("1234")
				.build();
		User userEntity = mapper.map(user, User.class);

		given(userService.create(any(UserData.class))).will(invocation -> {
			UserData createdUser = invocation.getArgument(0);
			createdUser.setId(1L);
			return createdUser;
		});
		given(userService.getAll()).willReturn(Collections.singletonList(userEntity));
		given(userService.getDetail(1L)).willReturn(userEntity);
		given(userService.getDetail(100L)).willThrow(UserNotFoundException.class);
		given(userService.updateDetail(eq(1L), any(UserData.class))).will(invocation -> {
			UserData updatedUser = invocation.getArgument(1);
			updatedUser.setId(invocation.getArgument(0));
			return mapper.map(updatedUser, User.class);
		});
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
	public void getAll() throws Exception {
		mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("지니")));

		verify(userService).getAll();
	}

	@Test
	public void getDetailWithExistedId() throws Exception {
		mockMvc.perform(get("/users/1"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("지니")));

		verify(userService).getDetail(any(Long.class));
	}

	@Test
	public void getDetailWithNotExistedId() throws Exception {
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

		verify(userService).updateDetail(any(Long.class), any(UserData.class));
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

		verify(userService, never()).updateDetail(any(Long.class), any(UserData.class));
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
