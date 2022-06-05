package com.codesoom.assignment.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codesoom.assignment.application.UserServiceImpl;
import com.codesoom.assignment.application.UserServiceInterface;
import com.codesoom.assignment.dto.UserDTO;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserServiceInterface userService;

	public UserController(UserServiceImpl userService) {
		this.userService = userService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO.Response createUser(@Valid @RequestBody UserDTO.CreateUser createUser) {
		return (UserDTO.Response)userService.createUser(createUser);
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO.Response updateUser(@PathVariable("id") int id, @Valid @RequestBody UserDTO.UpdateUser updateUser) {
		return (UserDTO.Response)userService.updateUsers(id, updateUser);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO.Response getUser(@PathVariable("id") int id) {
		return userService.getUser(id);
	}

	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<UserDTO.Response> getUsers() {
		return userService.getUsers();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("id") int id) {
		userService.deleteUser(id);
	}
}
