package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserCommandService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserCommandService userCommandService;

    public UserController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody UserRequest userRequest) {
        return userCommandService.createUser(userRequest);
    }
}
