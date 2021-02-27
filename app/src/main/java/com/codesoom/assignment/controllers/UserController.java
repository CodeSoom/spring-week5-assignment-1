package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사용자의 생성, 업데이트, 삭제를 처리
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
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
}
