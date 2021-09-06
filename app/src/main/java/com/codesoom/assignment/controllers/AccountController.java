package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 회원 정보 HTTP Request 요청 API를 받아 처리한다.
 * TODO 구현해야 할 기능
 * 1. 회원 생성하기 - POST /user
 * 2. 회원 수정하기 - POST /user/{id}
 * 3. 회원 삭제하기 - DELETE /user/{id}
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
}
