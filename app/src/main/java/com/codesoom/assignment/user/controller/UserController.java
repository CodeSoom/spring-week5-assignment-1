package com.codesoom.assignment.user.controller;

import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserData userData) {
        return new User("BEOMKING", "123456", "qjawlsqjacks@naver.com");
    }
}
