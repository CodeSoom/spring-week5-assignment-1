package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * 유저의 Http 요청을 처리합니다.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 요청 유저와 동일한 유저를 생성하고, 생성한 유저를 응답 합니다.
     *
     * @param user 요청 유저
     * @return 생성한 유저
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserData user) {
        return userService.createUser(user);
    }

    /**
     * 요청 id의 유저를 요청 유저와 동일하도록 변경하고 변경한 유저를 응답합니다.
     *
     * @param id   요청 id
     * @param user 요청 유저
     * @return 변경한 유저
     */
    @PatchMapping("{id}")
    public User update(@PathVariable Long id, @RequestBody @Valid UserData user) {
        return userService.updateUser(id, user);
    }

    /**
     * 요청 id의 유저를 삭제합니다.
     *
     * @param id 요청 id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
