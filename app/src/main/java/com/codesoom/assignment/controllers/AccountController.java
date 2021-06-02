package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.AccountService;
import com.codesoom.assignment.domain.Account;
import com.codesoom.assignment.dto.AccountData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 *
 * 웹에서 회원정보에 대한 요청을 받아 처리 및 출력을 담당합니다.
 *
 * @author flying_fisherman
 */

@RestController
@CrossOrigin
@RequestMapping("/products/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 전체 회원정보를 List화 하여 리턴합니다.
     *
     * @return 전체 회원정보입니다.
     */
    @GetMapping
    public List<Account> accountList() {
        return accountService.getAccounts();
    }

    /**
     * id를 받아 해당 id의 회원정보를 리턴합니다.
     *
     * @param id 조회하려는 회원정보 ID입니다.
     * @return 해당 ID의 회원정보입니다.
     */
    @GetMapping("{id}")
    public Account accountDetail(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    /**
     * 필요한 회원정보의 내용을 받아 새로운 회원정보를 작성합니다.
     *
     * @param accountData 작성하려는 회원정보의 내용입니다.
     * @return 작성된 회원정보입니다.
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Account accountCreate(@RequestBody @Valid AccountData accountData) {
        return accountService.createAccount(accountData);
    }

    /**
     * id와 수정하고자 하는 내용을 받아 해당 id의 회원정보를 수정합니다.
     *
     * @param id 수정하려는 회원정보 ID입니다.
     * @param accountData 수정하고자 하는 회원정보 내용입니다.
     * @return 수정된 회원정보입니다.
     */
    @PatchMapping("{id}")
    public Account accountUpdate(@PathVariable Long id,
                              @RequestBody @Valid AccountData accountData
    ) {
      return accountService.updateAccount(id, accountData);
    }

    /**
     * id와 수정하고자 하는 내용을 받아 해당 id의 회원정보를 수정합니다.
     *
     * @param id 수정하려는 회원정보 ID입니다.
     * @param accountData 수정하고자 하는 회원정보 내용입니다.
     * @return 수정된 회원정보입니다.
     */
    @PutMapping("{id}")
    public Account accountPut(@PathVariable Long id,
                              @RequestBody @Valid AccountData accountData
    ) {
        return accountService.updateAccount(id, accountData);
    }

    /**
     * id를 받아 해당 id의 회원정보를 삭제합니다.
     *
     * @param id 삭제하려는 회원정보 ID입니다.
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void accountDelete(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}
