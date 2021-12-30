package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
     * @param userData 저장할 회원
     * @return 저장된 회원
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody UserData userData) {
        return userService.createUser(userData);
    }

    /**
     * 수정된 회원을 리턴한다.
     *
     * @param id       수정할 회원의 아이디
     * @param userData 수정할 회원
     * @return 수정된 회원
     */
    @PatchMapping("{id}")
    public User update(@PathVariable Long id, @Valid @RequestBody UserData userData) {
        return userService.updateUser(id, userData);
    }

    /**
     * 회원을 삭제한다.
     *
     * @param id 삭제할 회원
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
