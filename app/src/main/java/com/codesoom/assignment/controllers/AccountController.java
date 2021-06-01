package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.AccountService;
import com.codesoom.assignment.domain.Account;
import com.codesoom.assignment.dto.AccountData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/products/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> acc_list() {
        return accountService.getAccounts();
    }

    @GetMapping("{id}")
    public Account acc_detail(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Account acc_create(@RequestBody @Valid AccountData accountData) {
        return accountService.createAccount(accountData);
    }

    @PatchMapping("{id}")
    public Account acc_update(@PathVariable Long id,
                              @RequestBody @Valid AccountData accountData
    ) {
      return accountService.updateAccount(id, accountData);
    }

    @PutMapping("{id}")
    public Account acc_put(@PathVariable Long id,
                              @RequestBody @Valid AccountData accountData
    ) {
        return accountService.updateAccount(id, accountData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void acc_delete(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}
