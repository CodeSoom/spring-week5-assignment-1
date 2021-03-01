package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserApplicationService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UpdateUserData;
import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private final UserApplicationService service;

    public UserController(UserApplicationService service) {
        this.service = service;
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.CREATED)
    UserData createUser(@RequestBody @Valid UserData userData) {
        User user = service.createUser(userData.getName(), userData.getEmail(), userData.getPassword());
        return new UserData(user.getId(), user.getName(), user.getMail(), user.getPassword());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }

    @PatchMapping("{id}")
    UserData updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserData userData
    ) {
        User user = service.changeName(id, userData.getName());
        return new UserData(user.getId(), user.getName(), user.getMail(), user.getPassword());
    }
}
