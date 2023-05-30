package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	@GetMapping
	public List<UserData> getAll() {
		return Collections.emptyList();
	}

	@GetMapping("{id}")
	public UserData getDetail() {
		return null;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserData create(@RequestBody UserData user) {
		return user;
	}

	@PatchMapping("{id}")
	public UserData patch(@PathVariable Long id,
						  @RequestBody UserData userData) {
		return userData;
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void destroy() {
	}


}
