package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Account;
import com.codesoom.assignment.domain.AccountRepository;
import com.codesoom.assignment.dto.AccountSaveData;
import com.codesoom.assignment.dto.AccountUpdateData;
import com.codesoom.assignment.exceptions.AccountNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.codesoom.assignment.Constant.ACCOUNT_EMAIL;
import static com.codesoom.assignment.Constant.ACCOUNT_NAME;
import static com.codesoom.assignment.Constant.ACCOUNT_PASSWORD;
import static com.codesoom.assignment.Constant.OTHER_ACCOUNT_NAME;
import static com.codesoom.assignment.Constant.OTHER_ACCOUNT_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("AccountService 테스트")
class AccountServiceTest {

    private AccountRepository accountRepository;
    private AccountService accountService;

    private Account account;
    private AccountSaveData accountData;

    @BeforeEach
    void setUp() {
        accountData = AccountSaveData.of(ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD);
        account = Account.of(1L, ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD);

        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);

        setUpFixture();
    }

    private void setUpFixture() {
        given(accountRepository.save(any(Account.class))).willReturn(account);

        given(accountRepository.findById(account.getId())).willReturn(Optional.of(account));

        given(accountRepository.findById(100L)).willThrow(new AccountNotFoundException(100L));
    }

    @DisplayName("회원 정보를 등록할 수 있다. ")
    @Test
    void createWithValidAccountV1() {
        AccountSaveData savedAccount = accountService.creation(accountData);

        assertThat(savedAccount.getId()).isEqualTo(1L);
        assertThat(savedAccount.getName()).isEqualTo(accountData.getName());
        assertThat(savedAccount.getPassword()).isEqualTo(accountData.getPassword());
        assertThat(savedAccount.getEmail()).isEqualTo(accountData.getEmail());

        verify(accountRepository).save(any(Account.class));
    }

    @DisplayName("회원 정보를 수정할 수 있다.")
    @Test
    void patchWithValidAccount() {
        AccountSaveData savedAccount = accountService.creation(accountData);
        final AccountUpdateData target = AccountUpdateData.of(OTHER_ACCOUNT_NAME, OTHER_ACCOUNT_NAME, OTHER_ACCOUNT_PASSWORD);

        final AccountSaveData updatedAccountData = accountService.patchAccount(savedAccount.getId(), target);

        assertThat(updatedAccountData.getId()).isEqualTo(savedAccount.getId());
        assertThat(updatedAccountData.getName()).isEqualTo(target.getName());
        assertThat(updatedAccountData.getEmail()).isEqualTo(target.getEmail());
        assertThat(updatedAccountData.getPassword()).isEqualTo(target.getPassword());
    }


    @DisplayName("존재하지 않는 식별자의 회원 정보를 수정하려 하면 예외가 발생한다.")
    @Test
    void patchWithInValidAccount() {
        final AccountUpdateData target = AccountUpdateData.of(OTHER_ACCOUNT_NAME, OTHER_ACCOUNT_NAME, OTHER_ACCOUNT_PASSWORD);

        assertThatThrownBy(() -> accountService.patchAccount(100L, target))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessage(String.format(AccountNotFoundException.DEFAULT_MESSAGE, 100L));
    }

    @DisplayName("회원 정보를 삭제할 수 있다.")
    @Test
    void deleteWithValidAccount() {
        AccountSaveData savedAccount = accountService.creation(accountData);

        accountService.deleteAccount(savedAccount.getId());

        verify(accountRepository).delete(account);
    }

    @DisplayName("회원 정보를 삭제할 수 있다.")
    @Test
    void deleteWithInValidAccount() {
        assertThatThrownBy(()->accountService.deleteAccount(100L))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessage(String.format(AccountNotFoundException.DEFAULT_MESSAGE, 100L));
    }
}
