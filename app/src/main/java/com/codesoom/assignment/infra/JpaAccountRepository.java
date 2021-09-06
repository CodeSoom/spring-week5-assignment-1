package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.Account;
import com.codesoom.assignment.domain.AccountRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Primary
public interface JpaAccountRepository extends AccountRepository, JpaRepository<Account, Long> {
}
