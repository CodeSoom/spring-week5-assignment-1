package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    /*TODO
        회원 생성하기 - POST /users
        회원 수정하기 - PATCH /users/{id}
        회원 삭제하기 - DELETE /users/{id}
        이름, 이메일, 비밀번호는 필수 입력 항목입니다. 만약 잘못된 정보로 회원이 만들어지지 않도록 유효성 검사를 하고 올바른 에러를 웹에게 응답할 수 있도록
     */
    private final UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserData userData){
        return userService.createUser(userData);
    }

    @PatchMapping ("/{id}")
    public User update(@PathVariable Long id, @RequestBody @Valid UserData userData){
        return userService.updateUser(id, userData);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
