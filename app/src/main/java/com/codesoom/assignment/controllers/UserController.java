package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserData userData) {
        return userService.createUser(userData);
    }

    @PatchMapping("{id}")
    public User update(@RequestBody @Valid UserData userData,
                       @PathVariable Long id) {
        return userService.updateUser(id, userData);
    }

    @DeleteMapping("{id}")
    public User delete(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
