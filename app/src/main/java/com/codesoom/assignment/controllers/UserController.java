package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create() {
        User user = new User(1L, "홍길동", "test@gmail.com", "1234");
        return user;
    }
}
