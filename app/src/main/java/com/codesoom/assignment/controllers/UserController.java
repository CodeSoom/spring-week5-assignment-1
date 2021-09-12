// TODO
// 1. 회원 생성하기 - POST /user
// 2. 회원 수정하기 - PUT, PATCH /user/{id}
// 3. 회원 삭제하기 - DELETE /user/{id}

package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private UserData userData;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserData createAccount() {
        UserData userData = UserData.builder().build();
        User user = userService.registerUser(userData);
        return UserData.builder()
                .email(user.getEmail())
                .build();
    }
}
