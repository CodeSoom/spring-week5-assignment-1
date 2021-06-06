package com.codesoom.assignment.application;

import com.codesoom.assignment.AccountNotFoundException;
import com.codesoom.assignment.domain.Account;
import com.codesoom.assignment.domain.AccountRepository;
import com.codesoom.assignment.dto.AccountData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class AccountServiceTest {
    private AccountService accountService;
    private AccountRepository accountRepository = mock(AccountRepository.class);
    private AccountData accountdata, updateAccountData;
    private Account account, updateAccount;
    private List<Account> accounts;

    private final static Long REGISTERED_ID = 1L;
    private final static Long NOT_REGISTERED_ID = 100L;

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        accountService = new AccountService(accountRepository, mapper);
        accounts = new ArrayList<>();

        accountdata = AccountData.builder()
                .id(REGISTERED_ID)
                .name("Tester")
                .password("password")
                .email("email@email.com")
                .build();

        account = mapper.map(accountdata, Account.class);

        updateAccountData = AccountData.builder()
                .name("Update Tester")
                .password("NewPassword")
                .email("email2@email.com")
                .build();

        updateAccount = mapper.map(updateAccountData, Account.class);
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

    @Nested
    @DisplayName("createAccount 메서드")
    class Describe_createAccount {
        @Nested
        @DisplayName("적합한 내용을 전달받으면")
        class Context_with_right_context {
            @BeforeEach
            void setUp() {
                given(accountRepository.save(any(Account.class))).will(invocation -> {
                    Account source = invocation.getArgument(0);
                    return Account.builder()
                            .id(REGISTERED_ID)
                            .name("Tester")
                            .password("password")
                            .email("email@email.com")
                            .build();
                });
            }

            @Test
            @DisplayName("새로운 회원정보를 작성하고, 리턴합니다.")
            void it_create_account_and_return() {
                assertThat(accountService.createAccount(accountdata)
                        .getName()).isEqualTo("Tester");
                assertThat(accountService.createAccount(accountdata)
                        .getPassword()).isEqualTo("password");

                verify(accountRepository, atLeastOnce()).save(any(Account.class));
            }
        }

        @Nested
        @DisplayName("허용되지 않은 문법을 전달받으면")
        class Context_with_wrong_script {
            @BeforeEach
            void setUp() {
                given(accountRepository.save(any(Account.class))).will(invocation -> {
                    Account source = invocation.getArgument(0);
                    return Account.builder()
                            .id(NOT_REGISTERED_ID)
                            .name("")
                            .password("")
                            .email("")
                            .build();
                });
            }

            @Test
            @DisplayName("HttpMessageNotReadable 예외를 던집니다.")
            void it_throw_httpMessageNotReadable() {
                assertThatExceptionOfType
                        (HttpMessageNotReadableException.class);
            }
        }
    }

    @Nested
    @DisplayName("updateAccount 메서드")
    class Describe_updateAccount {

        @Nested
        @DisplayName("수정하려는 적합한 정보를 받으면")
        class Context_with_right_context {
            @BeforeEach
            void setUp() {
                given(accountRepository.findById(REGISTERED_ID))
                        .willReturn(Optional.of(updateAccount));
            }

            @Test
            @DisplayName("회원정보를 수정하고, 리턴합니다.")
            void it_update_account_and_return() {
                assertThat(accountService.updateAccount(REGISTERED_ID, updateAccountData)
                        .getName()).isEqualTo("Update Tester");
                assertThat(accountService.updateAccount(REGISTERED_ID, updateAccountData)
                        .getPassword()).isEqualTo("NewPassword");

                verify(accountRepository, atLeastOnce()).findById(REGISTERED_ID);
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
            void it_thorw_exception() {
                assertThatThrownBy(() -> accountService.updateAccount(NOT_REGISTERED_ID, updateAccountData)
                        ).isInstanceOf(AccountNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteAccount 메서드")
    class Describe_deleteAccount {

        @Nested
        @DisplayName("삭제하려는 등록된 회원정보의 id를 받으면")
        class Context_with_existed_id {
            @BeforeEach
            void setUp() {
                given(accountRepository.findById(REGISTERED_ID))
                        .willReturn(Optional.of(account));

                account = accountService.deleteAccount(REGISTERED_ID);
            }

            @Test
            @DisplayName("회원정보를 삭제합니다.")
            void it_delete_account() {
                verify(accountRepository).delete(account);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 회원정보의 id를 받으면")
        class Context_with_not_existed_id {
            @BeforeEach
            void setUp() {
                given(accountRepository.findById(NOT_REGISTERED_ID))
                        .willThrow(AccountNotFoundException.class);
            }

            @Test
            @DisplayName("AccountNotFound 예외를 던집니다.")
            void it_thorw_exception() {
                assertThatThrownBy(() -> accountService.updateAccount(NOT_REGISTERED_ID, updateAccountData)
                ).isInstanceOf(AccountNotFoundException.class);
            }
        }
    }

}
