package com.codesoom.assignment.domain;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    List<Account> findAll();

    Optional<Account> findById(Long uid);

    Account save(Account account);

    void delete(Account account);
}
