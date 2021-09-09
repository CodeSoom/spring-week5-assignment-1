package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserData createUser(@RequestBody @Valid UserData userData) {
        return userService.createUser(userData);
    }

    @GetMapping("/{id}")
    public UserData selectUser(@PathVariable Long id) {
        return userService.selectUser(id);
    }

    @GetMapping
    public List<UserData> selectUsers() {
        return userService.selectUsers();
    }
}
