package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserServiceImpl;
import com.codesoom.assignment.application.UserServiceInterface;
import com.codesoom.assignment.dto.UserDTO;
import com.codesoom.assignment.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServiceInterface userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody UserDTO.CreateUser createUser) {
        return userService.createUser(createUser);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateUser(@PathVariable("id") int id, @Valid @RequestBody UserDTO.UpdateUser updateUser) {
        return userService.updateUsers(id, updateUser);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUser(@PathVariable("id") int id) {
        return userService.getUser(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
    }
}
