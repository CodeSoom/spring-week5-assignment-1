package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.Account;
import com.codesoom.assignment.domain.AccountRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Primary
public interface JpaAccountRepository
        extends AccountRepository, CrudRepository<Account, Long> {
    List<Account> findAll();

    Optional<Account> findById(Long uid);

    Account save(Account account);

    void delete(Account account);
}
