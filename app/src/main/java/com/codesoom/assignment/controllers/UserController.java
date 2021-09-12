// TODO
// 1. 회원 생성하기 - POST /user
// 2. 회원 수정하기 - PUT, PATCH /user/{id}
// 3. 회원 삭제하기 - DELETE /user/{id}

package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserData createAccount() {
        return UserData.builder()
                .email("bloomspes@gmail.com")
                .build();
    }
}
