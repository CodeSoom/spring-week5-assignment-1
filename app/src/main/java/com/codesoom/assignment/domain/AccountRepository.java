package com.codesoom.assignment.domain;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Account save(Account account);

    Optional<Account> findById(Long id);

    List<Account> findAll();

    void delete(Account account);
}
