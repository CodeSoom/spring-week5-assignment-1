package com.codesoom.assignment.application;

import com.codesoom.assignment.AccountNotFoundException;
import com.codesoom.assignment.domain.Account;
import com.codesoom.assignment.domain.AccountRepository;
import com.codesoom.assignment.dto.AccountData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 회원정보에 대한 생성, 조회, 수정, 삭제 처리를 담당합니다.
 */

@Service
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final Mapper mapper;

    public AccountService(AccountRepository accountRepository, Mapper dozerMapper) {
        this.accountRepository = accountRepository;
        this.mapper = dozerMapper;
    }

    /**
     *
     * @return 전체 회원정보입니다.
     */
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    /**
     *
     * @param id 조회하려는 회원정보 ID입니다.
     * @return 해당 ID의 회원정보입니다.
     */
    public Account getAccount(Long id) {
        return findAccount(id);
    }

    /**
     *
     * @param accountData 작성하려는 회원정보의 내용입니다.
     * @return 작성된 회원정보입니다.
     */
    public Account createAccount(AccountData accountData) {
        return accountRepository.save(
                mapper.map(accountData, Account.class)
        );
    }

    /**
     *
     * @param id 수정하려는 회원정보 ID입니다.
     * @param source 수정하고자 하는 회원정보 내용입니다.
     * @return 수정된 회원정보입니다.
     */
    // TODO : Entity를 변경하기 위해서는 꼭 Entity 내에 Presentation Logic을 넣어서 사용해야 하는가?
    public Account updateAccount(Long id, AccountData source) {
        Account account = findAccount(id);
        account.changeAccData(mapper.map(source, Account.class));

//        // Testing
//        source.setId(id);
//        Account account = source.changeData(source);
        return account;
    }

    /**
     *
     * @param id 삭제하려는 회원정보 ID입니다.
     * @return 삭제 전 회원정보입니다.
     */
    public Account deleteAccount(Long id) {
        Account account = findAccount(id);
        accountRepository.delete(account);

        return account;
    }

    /**
     *
     * @throws AccountNotFoundException id에 해당하는 회원정보가 존재하지 않습니다.
     *
     * @param id 데이터 내에서 검색하려는 회원정보 ID입니다.
     * @return 검색 결과의 회원정보입니다.
     */
    public Account findAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }
}
