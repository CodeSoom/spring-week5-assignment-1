package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> findAllUsers() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public User findUser(@PathVariable Long id) {
        return service.findUser(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public User saveUser(@RequestBody @Valid UserData userData) {
        return service.saveUser(userData);
    }

    @PatchMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody @Valid UserData userData) {
        return service.updateUser(id, userData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }
}
