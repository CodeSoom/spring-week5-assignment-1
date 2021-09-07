package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.AccountService;
import com.codesoom.assignment.dto.AccountData;
import lombok.RequiredArgsConstructor;
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
 * 회원 정보 HTTP Request 요청 API를 받아 처리한다.
 * 1. 회원 생성하기 - POST /user
 * 2. 회원 수정하기 - PATCH /user/{id}
 * 3. 회원 삭제하기 - DELETE /user/{id}
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin
public class AccountController {
    private final AccountService accountService;

    /**
     * 회원 정보를 등록한다.
     * @param accountData 저장할 회원 정보
     * @return 저장된 회원 상세 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountData createAccount(@RequestBody @Valid AccountData accountData) {
        return accountService.creation(accountData);
    }

    /**
     * 회원 정보를 수정한다.
     * @param id 수정 할 회원 식별자
     * @param accountData 수정 할 회원 정보
     * @return 수정된 회원 정보
     */
    @PatchMapping("{id}")
    public AccountData patchAccount(@PathVariable long id,
                                    @RequestBody @Valid AccountData accountData) {
        return accountService.patchAccount(id, accountData);
    }

    /**
     * 회원 정보를 삭제한다.
     * @param id 삭제 할 회원 식별자
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}
