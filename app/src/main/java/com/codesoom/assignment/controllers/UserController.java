package com.codesoom.assignment.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.dto.UserDTO;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO.Response createUser(@RequestBody UserDTO.CreateUser createUser) {
		return userService.createUser(createUser);
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO.Response updateUser(@PathVariable("id") int id, @RequestBody UserDTO.UpdateUser updateUser) {
		return userService.updateUsers(id, updateUser);
	}
}
