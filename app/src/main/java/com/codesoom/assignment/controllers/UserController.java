package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 유저에 대한 생성, 수정, 삭제 요청을 처리한다.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 주어진 유저를 저장하고 해당 객체를 리턴한다.
     *
     * @param userData - 새로 저장하고자 하는 유저
     * @return 새로 저장된 유저
     * @throws MethodArgumentNotValidException 만약 주어진 유저의
     *         이름이 비어있거나, 이메일이 비어있거나, 비밀번호가 비어있는 경우
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserData userData) {
        return userService.createUser(userData);
    }

    @PatchMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody @Valid UserData userData) {
        return userService.updateUser(id, userData);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public User delete(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

}
