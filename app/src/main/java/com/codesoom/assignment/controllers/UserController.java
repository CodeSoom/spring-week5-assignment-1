package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserModificationData;
import com.codesoom.assignment.dto.UserRegistrationData;
import com.codesoom.assignment.dto.UserResultData;
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
import java.util.stream.Collectors;

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
    public List<UserResultData> findAllUsers() {
        return service.findAll()
                .stream()
                .map(this::getUserResultData) //user -> getUserResultData(user)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public UserResultData findUser(@PathVariable Long id) {
        User user = service.findUser(id);
        return getUserResultData(user);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public UserResultData saveUser(@RequestBody @Valid UserRegistrationData userData) {
        User user = service.saveUser(userData);
        return getUserResultData(user);
    }

    @PatchMapping("{id}")
    public UserResultData updateUser(@PathVariable Long id, @RequestBody @Valid UserModificationData userData) {
        User user = service.updateUser(id, userData);
        return getUserResultData(user);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }

    private UserResultData getUserResultData(User user) {
        if (user == null) {
            return null;
        }

        return UserResultData.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
