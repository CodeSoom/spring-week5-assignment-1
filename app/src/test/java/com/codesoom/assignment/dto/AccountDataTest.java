package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codesoom.assignment.Constant.ACCOUNT_EMAIL;
import static com.codesoom.assignment.Constant.ACCOUNT_NAME;
import static com.codesoom.assignment.Constant.ACCOUNT_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DTO 객체인 AccountData의 비즈니스 로직 테스트")
class AccountDataTest {

    @DisplayName("AccountData를 Account 도메인 객체로 변환 할 수 있다.")
    @Test
    void toAccount() {
        final AccountData accountData = new AccountData(1L, ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD);
        Account account = accountData.toAccount();

        assertThat(account.getId()).isEqualTo(1L);
        assertThat(account.getName()).isEqualTo(ACCOUNT_NAME);
        assertThat(account.getEmail()).isEqualTo(ACCOUNT_EMAIL);
        assertThat(account.getPassword()).isEqualTo(ACCOUNT_PASSWORD);
    }

    @DisplayName("Account 객체를 DTO 객체르 변환할 수 있다.")
    @Test
    void createAccountDataWithAccount() {
        final Account account = Account.builder()
                .id(1L)
                .name(ACCOUNT_NAME)
                .email(ACCOUNT_EMAIL)
                .password(ACCOUNT_PASSWORD)
                .build();

        AccountData accountData = AccountData.from(account);

        assertThat(accountData.getId()).isEqualTo(account.getId());
        assertThat(accountData.getName()).isEqualTo(account.getName());
        assertThat(accountData.getEmail()).isEqualTo(account.getEmail());
        assertThat(accountData.getPassword()).isEqualTo(account.getPassword());
    }

}
