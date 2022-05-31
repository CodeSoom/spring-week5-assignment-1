package com.codesoom.assignment.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.codesoom.assignment.application.UserService;

@RestController
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
}
