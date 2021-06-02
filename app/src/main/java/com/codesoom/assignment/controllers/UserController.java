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
 * 사용자 관련 http 요청 처리를 담당합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 요청을 받아 사용자를 등록하고 리턴합니다.
     * @param userData 사용자 정보
     * @return 등록 한 사용자
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User signInUser(@RequestBody @Valid UserData userData) {
        return userService.signInUser(userData);
    }

    /**
     * 사용자 수정 요청을 받아 업데이트하고 리턴합니다.
     * @param id 업데이트 할 사용자 id
     * @param userData 업데이트 할 사용자 정보
     * @return 업데이트 한 사용자
     */
    @PatchMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody @Valid UserData userData) {
        return userService.updateUser(id, userData);
    }

    /**
     * 사용자 삭제 요청을 받아 삭제합니다.
     * @param id 삭제 할 사용자 id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
