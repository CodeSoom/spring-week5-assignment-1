package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 사용자의 생성, 업데이트, 삭제를 처리합니다.
 */
@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> list() {
        return userService.getUsers();
    }

    @GetMapping("{id}")
    public User detail(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserData userData) {
        return userService.createUser(userData);
    }

    @PatchMapping("{id}")
    public User update(
            @PathVariable Long id,
            @RequestBody @Valid UserData userData
    ) {
        return userService.updateUser(id, userData);
    }

    @PutMapping("{id}")
    public User put(
            @PathVariable Long id,
            @RequestBody @Valid UserData userData
    ) {
        return userService.putUser(id, userData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
