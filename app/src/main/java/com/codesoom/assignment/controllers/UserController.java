package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserData create(@RequestBody @Valid UserData userData) {
        User createdUser = service.createUser(userData);

        return UserData.builder()
                .id(createdUser.getId())
                .name(createdUser.getName())
                .password(createdUser.getPassword())
                .email(createdUser.getEmail())
                .build();
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserData update(
            @PathVariable Long id,
            @RequestBody @Valid UserData userData
    ) {
        // TODO: Service 연동
        return UserData.builder()
                .id(id)
                .name(userData.getName())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .build();
    }
}
