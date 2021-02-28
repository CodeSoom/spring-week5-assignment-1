package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 유저 정보 관련 요청과 응답을 관리한다.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 유저 생성 요청을 받고, 요쳥에 대한 결과를 반환한다.
     *
     * @param userData 생성할 유저의 데이터
     * @return User 생성한 유저정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserData userData) {
        return userService.createUser(userData);
    }

    /**
     * 유저 정보 수정요청을 받고, 요쳥에 대한 결과를 반환한다.
     *
     * @param id 수정할 유저의 id
     * @param userData 수정할 유저정보
     * @return User 수정한 유저정보
     */
    @PatchMapping("{id}")
    public User update(
            @PathVariable Long id,
            @RequestBody @Valid UserData userData
    ) {
        userData.setId(id);
        return userService.updateUser(userData);
    }

    /**
     * 유저 정보 삭제요청을 받고, 요쳥에 대한 결과를 반환한다.
     *
     * @param id 삭제할 유저의 id
     * @return User 삭제된 유저정보
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        //
    }
}
