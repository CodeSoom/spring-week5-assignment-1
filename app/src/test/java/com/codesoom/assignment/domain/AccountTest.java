package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.AccountSaveData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codesoom.assignment.Constant.ACCOUNT_EMAIL;
import static com.codesoom.assignment.Constant.ACCOUNT_NAME;
import static com.codesoom.assignment.Constant.ACCOUNT_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Account 객체 테스트")
class AccountTest {

    @DisplayName("객체 생성을 할 수 있다.")
    @Test
    void create() {
        final Account account = new Account();

        assertThat(account).isInstanceOf(Account.class);
    }

    @DisplayName("빌더, 정적 팩토리 메서드는 모두 동일한 결과를 낸다. .")
    @Test
    void creationWithBuilder() {
        final Account account1 = Account.builder()
                .id(1L)
                .name(ACCOUNT_NAME)
                .email(ACCOUNT_EMAIL)
                .password(ACCOUNT_PASSWORD)
                .build();

        final Account account2 = Account.of(1L, ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD);
        final Account account3 = Account.from(new AccountSaveData(1L, ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD));

        assertThatAccountDataField(account1);
        assertThatAccountDataField(account2);
        assertThatAccountDataField(account3);
    }

    private void assertThatAccountDataField(Account account) {
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
