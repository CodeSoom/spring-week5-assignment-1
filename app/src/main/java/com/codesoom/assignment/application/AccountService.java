package com.codesoom.assignment.application;

import com.codesoom.assignment.AccountNotFoundException;
import com.codesoom.assignment.domain.Account;
import com.codesoom.assignment.domain.AccountRepository;
import com.codesoom.assignment.dto.AccountData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountService {
    private final AccountRepository accRepo;
    private final Mapper mapper;
    private Object AccountData;

    public AccountService(AccountRepository accRepo, Mapper dozerMapper) {
        this.accRepo = accRepo;
        this.mapper = dozerMapper;
    }

    public List<Account> getAccounts() {
        return accRepo.findAll();
    }

    public Account getAccount(Long uid) {
        return findAccount(uid);
    }

    public Account createAccount(AccountData accData) {
        return accRepo.save(
                mapper.map(AccountData, Account.class)
        );
    }

    public Account updateAccount(Long uid, AccountData accData) {
        Account acc = findAccount(uid);
        return accData.changeData(acc, accData);
    }

    public Account findAccount(Long uid) {
        return accRepo.findById(uid)
                .orElseThrow(() -> new AccountNotFoundException(uid));
    }
}
