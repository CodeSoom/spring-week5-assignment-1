package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 생성, 수정, 삭제에 대한 요청을 처리합니다.
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

//    회원 생성하기 - POST /user
//    회원 수정하기 - POST /user/{id}
//    회원 삭제하기 - DELETE /user/{id}

    public User createUser(@RequestBody UserData userData) {

        return null;
    }
}
