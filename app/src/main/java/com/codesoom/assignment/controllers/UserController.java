package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.CreateUserRequest;
import com.codesoom.assignment.dto.UpdateUserRequest;
import com.codesoom.assignment.dto.UserResponse;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final Mapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid CreateUserRequest createUserRequest) {
        User user = User.builder()
                .name(createUserRequest.getName())
                .email(createUserRequest.getEmail())
                .password(createUserRequest.getPassword())
                .build();
        User createdUser = userService.createUser(user);
        return UserResponse.builder()
                .name(createdUser.getName())
                .email(createdUser.getEmail())
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        User user = User.builder()
                .name(updateUserRequest.getName())
                .password(updateUserRequest.getPassword())
                .build();
        User updatedUser = userService.updateUser(id, user);
        return UserResponse.builder()
                .name(updatedUser.getName())
                .email(updatedUser.getEmail())
                .build();
    }
}
