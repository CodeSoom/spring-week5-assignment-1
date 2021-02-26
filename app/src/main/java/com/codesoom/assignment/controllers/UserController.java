package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @PostMapping(produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.CREATED)
    UserData createUser(@RequestBody UserData userData) {
        return new UserData(1L, userData.getName(), userData.getEmail(), userData.getPassword());
    }
}
