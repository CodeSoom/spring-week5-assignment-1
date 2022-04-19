package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.UserCommandService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserResponseDto;
import com.codesoom.assignment.domain.UserSaveDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@UserController
public class UserSaveController {

    private final UserCommandService service;

    public UserSaveController(UserCommandService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserResponseDto saveUser(@Valid @RequestBody UserSaveDto userSaveDto) {
        return new UserResponseDto(service.saveUser(userSaveDto));
    }

}
