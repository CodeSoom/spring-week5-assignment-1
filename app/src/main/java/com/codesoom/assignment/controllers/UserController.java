package com.codesoom.assignment.controllers;


import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    /**
     * TODO:
     * 회원 생성하기 - POST /users
     * 회원 수정하기 - PATCH /users/{id}
     * 회원 삭제하기 - DELETE /users/{id}
     */

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return user;
    }


}
