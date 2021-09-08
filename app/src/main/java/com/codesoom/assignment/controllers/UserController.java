package com.codesoom.assignment.controllers;

import javax.validation.Valid;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * User 리소스
 * Http 요청과 Application layer를 연결한다.
 *
 * @see UserService
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Product 생성 요청을 Application layer에 연결한다.
     *
     * @param userData 생성할 User 데이터
     * @return 생성한 User 데이터
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserData userData) {
        return userService.createUser(userData);
    }
}
