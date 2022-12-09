/*
회원 관리하기
회원 생성, 수정, 삭제하기 REST API를 만들어주세요. 회원은 이름, 이메일, 비밀번호는 필수 입력 항목입니다.
만약 잘못된 정보로 회원이 만들어지지 않도록 유효성 검사를 하고 올바른 에러를 웹에게 응답할 수 있도록 만들어 주세요.

회원 생성하기 - POST /users
회원 수정하기 - PATCH /users/{id}
회원 삭제하기 - DELETE /users/{id}
 */

package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public User saveUser(@RequestBody @Valid UserData userData) {
        return service.saveUser(userData);
    }

    @PatchMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody @Valid UserData userData) {
        return service.updateUser(id, userData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }
}
