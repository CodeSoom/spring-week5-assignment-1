package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Account;
import com.codesoom.assignment.domain.AccountRepository;
import com.codesoom.assignment.dto.AccountSaveData;
import com.codesoom.assignment.dto.AccountUpdateData;
import com.codesoom.assignment.exceptions.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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


    @Transactional
    public AccountSaveData creation(AccountSaveData data) {
        final Account account = data.toAccount();
        final Account savedAccount = accountRepository.save(account);

        return AccountSaveData.from(savedAccount);
    }

    @Transactional
    public AccountSaveData patchAccount(long id, AccountUpdateData data) {
        final Account foundAccount = accountRepository.findById(id)
                .orElseThrow(()-> new AccountNotFoundException(id));

        foundAccount.change(data.toAccount());

        return AccountSaveData.from(foundAccount);
    }

    @Transactional
    public void deleteAccount(Long id) {
        final Account foundAccount = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        accountRepository.delete(foundAccount);
    }

    public AccountSaveData findAccount(long id) {
        final Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        return AccountSaveData.from(account);
    }

    public List<AccountSaveData> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(AccountSaveData::from)
                .collect(Collectors.toList());
    }
}
