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
 * 회원 정보에 대한 생성, 조회, 수정, 삭제 처리를 담당합니다.
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
     * 전체 회원정보 목록을 리턴합니다.
     *
     * @return 회원정보 목록
     */
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    /**
     * 회원정보를 리턴합니다.
     *
     * @param id 조회하려는 회원정보 ID
     * @return 회원정보
     */
    public Account getAccount(Long id) {
        return findAccount(id);
    }

    /**
     * 새로운 회원정보를 작성합니다.
     *
     * @param accountData 작성하려는 회원정보의 내용
     * @return 작성된 회원정보
     */
    public Account createAccount(AccountData accountData) {
//        return accountRepository.save(
//                mapper.map(accountData, Account.class)
//        );

        Account account = mapper.map(accountData, Account.class);
        return accountRepository.save(account);
    }

    /**
     * 회원정보를 수정합니다.
     *
     * @param id 수정하려는 회원정보 ID
     * @param source 수정하려는 회원정보 내용
     * @return 수정된 회원정보
     */
    // TODO : Entity를 변경하기 위해서는 꼭 Entity 내에 Presentation Logic을 넣어서 사용해야 하는가?
    // TODO : 0603 - 팩토리패턴, 데코레이터 패턴, 빌더 패턴 등에 대해 조사, 테스트 해 볼것.
    public Account updateAccount(Long id, AccountData source) {
        Account account = findAccount(id);
        account.changeAccData(mapper.map(source, Account.class));

//        // Testing
//        source.setId(id);
//        Account account = source.changeData(source);
        return account;
    }

    /**
     * 회원정보를 삭제합니다.
     *
     * @param id 삭제하려는 회원정보 ID
     * @return 삭제 전 회원정보
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
     * @param id 데이터 내에서 검색하려는 회원정보 ID
     * @return 회원정보
     */
    public Account findAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }
}
