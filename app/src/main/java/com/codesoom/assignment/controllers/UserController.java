package com.codesoom.assignment.controllers;

import javax.validation.Valid;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UpdateUserData;
import com.codesoom.assignment.dto.UserData;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자에 대한 생성, 수정, 삭제 요청을 처리한다.
 */
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * 사용자를 생성하고 리턴한다.
     *
     * @param userData 생성할 사용자 내용
     * @return 생성한 사용자
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserData userData) {
        return userService.createUser(userData);
    }

    /**
     * 사용자를 삭제한다.
     *
     * @param id 삭제할 사용자 id
     * @throws NotFoundException 사용자를 찾을 수 없는 경우
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable final Long id) {
        userService.deleteUser(id);
    }

    /**
     * 사용자를 수정하고 리턴한다.
     *
     * @param id 수정할 사용자 id
     * @param userData 수정할 사용자 내용
     * @return 수정한 사용자
     * @throws NotFoundException 사용자를 찾을 수 없는 경우
     */
    @RequestMapping(
        value = "{id}", method = { RequestMethod.PUT, RequestMethod.PATCH }
    )
    @ResponseStatus(HttpStatus.OK)
    public User update(
        @PathVariable final Long id, @RequestBody @Valid final UpdateUserData updateUserData
    ) {
        return userService.updateUser(id, updateUserData);
    }
}
