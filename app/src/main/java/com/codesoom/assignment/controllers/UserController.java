package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserPostDto;
import com.codesoom.assignment.dto.UserUpdateDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
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
 * 사용자에 대한 HTTP 요청을 처리합니다.
 */
@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 사용자를 생성하고 리턴합니다.
     * @param userDto 사용자 생성 정보
     * @return 생성된 사용자
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserPostDto userDto) {
        return userService.createUser(userDto);
    }

    /**
     * 요청한 사용자를 수정하고 리턴합니다.
     * @param id 사용자 식별자
     * @param userDto 사용자 수정 정보
     * @return 수정된 사용자
     */
    @PatchMapping("{id}")
    public User update(@PathVariable Long id, @RequestBody @Valid UserUpdateDto userDto) {
        return userService.updateUser(id, userDto);
    }

    /**
     * 요청한 사용자를 삭제합니다.
     * @param id 사용자 식별자
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
