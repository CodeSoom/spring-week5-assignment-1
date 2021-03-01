package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 사용자 관련 요청을 처리합니다.
 */
@RestController
@RequestMapping("/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 사용자 등록 요청을 처리하고, 등록된 사용자 정보를 반환합니다.
     *
     * @param userData
     * @return 등록된 사용자 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid UserData userData) {
        return userService.createUser(userData);
    }

    /**
     * 사용자 정보 수정 요청을 처리하고, 수정된 사용자 정보를 반환합니다.
     *
     * @param id
     * @param userData
     * @return 수정된 사용자 정보
     */
    @PatchMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody @Valid UserData userData) {
        return userService.updateUser(id, userData);
    }

    /**
     * 사용자 삭제 요청을 처리합니다.
     *
     * @param id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
