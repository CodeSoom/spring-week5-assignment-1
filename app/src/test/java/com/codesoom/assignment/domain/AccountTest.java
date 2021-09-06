package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.AccountData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codesoom.assignment.Constant.ACCOUNT_EMAIL;
import static com.codesoom.assignment.Constant.ACCOUNT_NAME;
import static com.codesoom.assignment.Constant.ACCOUNT_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Account 객체 테스트")
class AccountTest {

    @DisplayName("빌더 패턴을 이용해 객체 생성이 가능하다.")
    @Test
    void creationWithBuilder() {
        final Account account = Account.builder()
                .id(1L)
                .name(ACCOUNT_NAME)
                .email(ACCOUNT_EMAIL)
                .password(ACCOUNT_PASSWORD)
                .build();

        assertThat(account.getId()).isEqualTo(1L);
        assertThat(account.getName()).isEqualTo(ACCOUNT_NAME);
        assertThat(account.getEmail()).isEqualTo(ACCOUNT_EMAIL);
        assertThat(account.getPassword()).isEqualTo(ACCOUNT_PASSWORD);
    }

    @DisplayName("정적 팩토리 메서드 of 를 통해 객체 생성이 가능하다.")
    @Test
    void creationWithOfFactoryMethod() {
        final Account account = Account.of(1L, ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD);

        assertThat(account.getId()).isEqualTo(1L);
        assertThat(account.getName()).isEqualTo(ACCOUNT_NAME);
        assertThat(account.getEmail()).isEqualTo(ACCOUNT_EMAIL);
        assertThat(account.getPassword()).isEqualTo(ACCOUNT_PASSWORD);
    }

    @DisplayName("정적 팩토리 메서드 from 를 통해 객체 생성이 가능하다.")
    @Test
    void creationWithFromFactoryMethod() {
        AccountData accountData = new AccountData(1L, ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD);
        final Account account = Account.from(accountData);

        assertThat(account.getId()).isEqualTo(1L);
        assertThat(account.getName()).isEqualTo(ACCOUNT_NAME);
        assertThat(account.getEmail()).isEqualTo(ACCOUNT_EMAIL);
        assertThat(account.getPassword()).isEqualTo(ACCOUNT_PASSWORD);
    }

    @DisplayName("정보를 변경할 수 있습니다.")
    @Test
    void change() {
        final Account account = Account.of(1L, ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD);
        final Account updateData = Account.of("Other", "Other@email.com", "Other");
        account.change(updateData);

        assertThat(account.getId()).isEqualTo(1L);
        assertThat(account.getName()).isEqualTo("Other");
        assertThat(account.getEmail()).isEqualTo("Other@email.com");
        assertThat(account.getPassword()).isEqualTo("Other");
    }
}
