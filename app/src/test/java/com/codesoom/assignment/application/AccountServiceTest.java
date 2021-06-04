package com.codesoom.assignment.application;

import com.codesoom.assignment.AccountNotFoundException;
import com.codesoom.assignment.domain.Account;
import com.codesoom.assignment.domain.AccountRepository;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class AccountServiceTest {
    private AccountService accountService;
    private AccountRepository accountRepository = mock(AccountRepository.class);
    private Account account;

    private final static Long REGISTERED_ID = 1L;
    private final static Long NOT_REGISTERED_ID = 100L;

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        accountService = new AccountService(accountRepository, mapper);

        account = Account.builder()
                .id(1L)
                .name("Tester")
                .password("password")
                .email("email@email.com")
                .build();
    }

    @Nested
    @DisplayName("getAccounts 메서드는")
    class Describe_getAccounts {

        @Nested
        @DisplayName("회원정보가 존재하면")
        class Context_with_exist_account {
            @BeforeEach
            void setUp() {

                given(accountRepository.findAll())
                        .willReturn(List.of(account));
            }

            @Test
            @DisplayName("전체 회원정보 목록을 리턴합니다.")
            void it_return_account_list() {
                assertThat(accountService.getAccounts())
                        .isNotEmpty();

                verify(accountRepository).findAll();
            }
        }

        @Nested
        @DisplayName("회원정보가 존재하지 않으면")
        class Context_with_not_exist_account {
            @BeforeEach
            void setUp() {
                given(accountRepository.findAll())
                        .willReturn(List.of());
            }

            @Test
            @DisplayName("빈 목록을 리턴합니다.")
            void it_return_empty() {
                assertThat(accountService.getAccounts())
                        .isEmpty();
                verify(accountRepository).findAll();
            }
        }
    }

    @Nested
    @DisplayName("getAccount 메서드")
    class Describe_getAccount {

        @Nested
        @DisplayName("존재하는 회원정보의 ID가 주어지면")
        class Context_with_existed_id {
            @BeforeEach
            void setUp() {
                given(accountRepository.findById(REGISTERED_ID))
                        .willReturn(Optional.of(account));
            }

            @Test
            @DisplayName("회원정보를 리턴합니다.")
            void it_return_account() {
                assertThat(accountService.getAccount(REGISTERED_ID).getName())
                        .isEqualTo("Tester");
                assertThat(accountService.getAccount(REGISTERED_ID).getEmail())
                        .isEqualTo("email@email.com");

                verify(accountRepository, atLeastOnce()).findById(anyLong());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 회원정보의 ID가 주어지면")
        class Context_with_not_existed_id {
            @BeforeEach
            void setUp() {
                given(accountRepository.findById(NOT_REGISTERED_ID))
                        .willThrow(AccountNotFoundException.class);
            }

            @Test
            @DisplayName("AccountNotFound 예외를 던집니다.")
            void it_throw_exception() {
                assertThatThrownBy(() -> accountService.getAccount(NOT_REGISTERED_ID))
                        .isInstanceOf(AccountNotFoundException.class);
            }
        }
    }

}
