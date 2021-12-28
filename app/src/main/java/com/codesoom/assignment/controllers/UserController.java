package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 회원의 요청을 관리합니다.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 저장된 회원을 리턴한다.
     *
     * @param user 저장할 회원
     * @return 저장된 회원
     */
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }
}
