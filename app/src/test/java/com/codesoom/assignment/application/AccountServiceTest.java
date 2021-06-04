package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Account;
import com.codesoom.assignment.domain.AccountRepository;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AccountServiceTest {
    private AccountService accountService;
    private AccountRepository accountRepository = mock(AccountRepository.class);

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        accountService = new AccountService(accountRepository, mapper);
    }

    @Nested
    @DisplayName("getAccounts 메서드는")
    class Describe_getAccounts {

        @Nested
        @DisplayName("회원정보가 존재하면")
        class Context_with_exist_account {
            @BeforeEach
            void setUp() {
                Account account = Account.builder()
                        .id(1L)
                        .name("Tester")
                        .password("password")
                        .email("email@email.com")
                        .build();

                given(accountRepository.findAll()).willReturn(List.of(account));
            }

            @Test
            @DisplayName("전체 회원정보 목록을 리턴합니다.")
            void it_return_account_list() {
                assertThat(accountService.getAccounts()).isNotEmpty();

                verify(accountRepository).findAll();
            }
        }

        @Nested
        @DisplayName("회원정보가 존재하지 않으면")
        class Context_with_not_exist_account {
            @BeforeEach
            void setUp() {
                given(accountRepository.findAll()).willReturn(List.of());
            }

            @Test
            @DisplayName("빈 목록을 리턴합니다.")
            void it_return_empty() {
                assertThat(accountService.getAccounts()).isEmpty();
                verify(accountRepository).findAll();
            }
        }
    }

}
