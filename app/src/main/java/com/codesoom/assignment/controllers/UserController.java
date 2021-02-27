package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 유저 정보를 관리하는 API.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 주어진 데이터로 유저를 생성한다.
     *
     * @param userData 생성 할 유저의 데이터.
     * @return 생성된 유저의 데이터.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserData create(@RequestBody UserData userData) {
        final User user = userData.toEntity();

        userService.create(user);

        return userData;
    }
}
