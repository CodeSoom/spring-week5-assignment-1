package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Account;
import com.codesoom.assignment.domain.AccountRepository;
import com.codesoom.assignment.dto.AccountData;
import com.codesoom.assignment.exceptions.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 정보를 관리한다.
 * 1. 회원 생성하기 - creation(AccountData)
 * 2. 회원 수정하기 - patchAccount(AccountData)
 * 3. 회원 삭제하기 - deleteAccount(Long id)
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {
    private final AccountRepository accountRepository;


    public AccountData creation(AccountData data) {
        final Account account = data.toAccount();
        final Account savedAccount = accountRepository.save(account);

        return AccountData.from(savedAccount);
    }

    @Transactional
    public AccountData patchAccount(long id, AccountData data) {
        final Account foundAccount = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        foundAccount.change(data.toAccount());

        return AccountData.from(foundAccount);
    }

    public void deleteAccount(Long id) {
        final Account foundAccount = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        accountRepository.delete(foundAccount);
    }
}
