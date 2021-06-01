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

    public AccountService(AccountRepository accRepo, Mapper dozerMapper) {
        this.accRepo = accRepo;
        this.mapper = dozerMapper;
    }

    public List<Account> getAccounts() {
        return accRepo.findAll();
    }

    public Account getAccount(Long id) {
        return findAccount(id);
    }

    public Account createAccount(AccountData accData) {
        return accRepo.save(
                mapper.map(accData, Account.class)
        );
    }

    // TODO : Entity를 변경하기 위해서는 꼭 Entity 내에 Presentation Logic을 넣어서 사용해야 하는가?
    public Account updateAccount(Long id, AccountData source) {
        Account acc = findAccount(id);
        acc.changeAccData(mapper.map(source, Account.class));

//        // Testing
//        source.setId(id);
//        Account acc = source.changeData(source);
        return acc;
    }

    public Account deleteAccount(Long id) {
        Account acc = findAccount(id);
        accRepo.delete(acc);

        return acc;
    }

    public Account findAccount(Long id) {
        return accRepo.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }
}
