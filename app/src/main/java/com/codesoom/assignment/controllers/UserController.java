package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 유저 정보를 관리하는 API.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final Mapper mapper;

    public UserController(
            Mapper dozerMapper,
            UserService userService
    ) {
        this.userService = userService;
        this.mapper = dozerMapper;
    }

    /**
     * 주어진 데이터로 유저를 생성한다.
     *
     * @param userData 생성 할 유저의 데이터.
     * @return 생성된 유저의 데이터.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserData create(@RequestBody @Valid UserData userData) {
        final User user = mapper.map(userData, User.class);

        userService.create(user);

        return userData;
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserData modify(
            @PathVariable long id,
            @RequestBody UserData userData
    ) {
        final User user = mapper.map(userData, User.class);

        userService.modify(id, user);

        return userData;
    }
}
