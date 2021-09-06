package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 회원 정보를 관리한다.
 * TODO 다음 내용을 구현해야 한다.
 * 1. 회원 생성하기 - creation(AccountData)
 * 2. 회원 수정하기 - patchAccount(AccountData)
 * 3. 회원 삭제하기 - deleteAccount(Long id)
 */
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
}
