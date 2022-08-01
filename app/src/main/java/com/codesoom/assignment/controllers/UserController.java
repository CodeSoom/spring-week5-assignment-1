package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserData createUser(@RequestBody @Valid UserData userData) {
        // TODO: 실제 데이터 저장 후 반환 필요
        return UserData.builder()
                .id(1L)
                .name(userData.getName())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .build();
    }
}
