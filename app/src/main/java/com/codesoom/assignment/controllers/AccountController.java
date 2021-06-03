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
 * 회원 정보의 CRUD에 대한 http 요청을 처리합니다.
 *
 */

@RestController
@CrossOrigin
@RequestMapping("/users")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 전체 회원정보 목록을 리턴합니다.
     *
     * @return 회원정보 목록
     */
    @GetMapping
    public List<Account> accountList() {
        return accountService.getAccounts();
    }

    /**
     * 회원정보를 리턴합니다.
     *
     * @param id 조회하려는 회원정보 ID
     * @return 회원정보
     */
    @GetMapping("{id}")
    public Account accountDetail(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    /**
     * 새로운 회원정보를 작성합니다.
     *
     * @param accountData 작성하려는 회원정보의 내용
     * @return 작성된 회원정보
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Account accountCreate(@RequestBody @Valid AccountData accountData) {
        return accountService.createAccount(accountData);
    }

    /**
     * 회원정보를 수정합니다.
     *
     * @param id 수정하려는 회원정보 ID
     * @param accountData 수정하려는 회원정보 내용
     * @return 수정된 회원정보
     */
    @PatchMapping("{id}")
    public Account accountUpdate(@PathVariable Long id,
                              @RequestBody @Valid AccountData accountData
    ) {
      return accountService.updateAccount(id, accountData);
    }

    /**
     * 회원정보를 수정합니다.
     *
     * @param id 수정하려는 회원정보 ID
     * @param accountData 수정하려는 회원정보 내용
     * @return 수정된 회원정보
     */
    @PutMapping("{id}")
    public Account accountPut(@PathVariable Long id,
                              @RequestBody @Valid AccountData accountData
    ) {
        return accountService.updateAccount(id, accountData);
    }

    /**
     * 회원정보를 삭제합니다.
     *
     * @param id 삭제하려는 회원정보 ID
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void accountDelete(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}
