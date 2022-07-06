package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserRegisterService;
import com.codesoom.assignment.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRegisterService userRegisterService;

    public UserController(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }

    @PostMapping
    public User register(@RequestBody User user) {
        return this.userRegisterService.register(user.getName(), user.getEmail(), user.getPassword());
    }
}
