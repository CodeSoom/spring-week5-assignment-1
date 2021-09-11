package com.codesoom.assignment.controllers;

import javax.validation.Valid;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * User에 대한 생성, 조회, 수정, 삭제 요청을 Application layer에 전달한다.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * User 생성 요청을 Application layer에 전달한다.
     *
     * @param userData 생성할 User 데이터
     * @return 생성한 User 데이터
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserData userData) {
        return userService.createUser(userData);
    }

    /**
     * User 삭제 요청을 Application layer에 전달한다.
     *
     * @param id 삭제할 User id
     * @throws NotFoundException User를 찾을 수 없는 경우
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable final Long id) {
        userService.deleteUser(id);
    }

    /**
     * User 수정 요청을 Application layer에 전달한다.
     *
     * @param id 수정할 User id
     * @param userData 수정할 User 데이터
     * @return 수정한 User 데이터
     * @throws NotFoundException User를 찾을 수 없는 경우
     */
    @RequestMapping(
        value = "{id}", method = { RequestMethod.PUT, RequestMethod.PATCH }
    )
    @ResponseStatus(HttpStatus.OK)
    public User update(
        @PathVariable final Long id, @RequestBody final UserData userData
    ) {
        return userService.updateUser(id, userData);
    }
}
