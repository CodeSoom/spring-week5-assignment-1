package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.AccountSaveData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

        Stream.of(account1, account2, account3).forEach((it) -> {
            assertThat(it.getId()).isEqualTo(1L);
            assertThat(it.getName()).isEqualTo(ACCOUNT_NAME);
            assertThat(it.getEmail()).isEqualTo(ACCOUNT_EMAIL);
            assertThat(it.getPassword()).isEqualTo(ACCOUNT_PASSWORD);
        });
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

    @DisplayName("잘못된 데이터로는 변경되지 않습니다.")
    @ParameterizedTest
    @MethodSource("provideInvalidAccount")
    void changeWithInvalidData(List<Account> pairs) {
        final Account account = Account.of(1L, ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD);

        for (Account updateData : pairs) {
            account.change(updateData);

            assertThat(account.getName()).isNotEmpty();
            assertThat(account.getEmail()).isNotEmpty();
            assertThat(account.getPassword()).isNotEmpty();
        }

    }

    public static Stream<Arguments> provideInvalidAccount() {
        String[][] params = {{"name", ""}, {"name", null}, {"email", ""}, {"email", null}, {"password", ""}, {"password", null}};

        final List<Account> invalidAccounts = IntStream.range(0, 6)
                .mapToObj(index -> {
                    final Account account = Account.of(ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD);
                    ReflectionTestUtils.setField(account, params[index][0], params[index][1]);

                    return account;
                }).collect(Collectors.toList());

        return Stream.of(Arguments.of(invalidAccounts));
    }

}
