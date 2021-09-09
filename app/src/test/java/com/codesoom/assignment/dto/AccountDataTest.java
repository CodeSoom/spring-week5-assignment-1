package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Account;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Stream;

import static com.codesoom.assignment.Constant.ACCOUNT_EMAIL;
import static com.codesoom.assignment.Constant.ACCOUNT_NAME;
import static com.codesoom.assignment.Constant.ACCOUNT_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DTO 객체인 AccountData의 로직 테스트")
class AccountDataTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @DisplayName("AccountData를 Account 도메인 객체로 변환 할 수 있다.")
    @Test
    void toAccount() {
        final AccountSaveData accountData = new AccountSaveData(1L, ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD);
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

        AccountSaveData accountData = AccountSaveData.from(account);

        assertThat(accountData.getId()).isEqualTo(account.getId());
        assertThat(accountData.getName()).isEqualTo(account.getName());
        assertThat(accountData.getEmail()).isEqualTo(account.getEmail());
        assertThat(accountData.getPassword()).isEqualTo(account.getPassword());
    }

    @DisplayName("validate 메서드는 객체가 유효하면 빈 Set을 반환한다.")
    @Test
    void createWithExistsField() {
        final Account account = Account.builder()
                .id(1L)
                .name(ACCOUNT_NAME)
                .email(ACCOUNT_EMAIL)
                .password(ACCOUNT_PASSWORD)
                .build();

        AccountSaveData accountData = AccountSaveData.from(account);
        final Set<ConstraintViolation<AccountSaveData>> validate = validator.validate(accountData);

        assertThat(validate).isEmpty();
    }

    @DisplayName("필드에 공백 혹은 null 값은 유효성 검증에서 실패한다.")
    @ParameterizedTest
    @MethodSource("provideAccountDataWithEmptyField")
    void createWithEmptyField(AccountSaveData source) {

        final Set<ConstraintViolation<AccountSaveData>> validate = validator.validate(source);
        final ConstraintViolation<AccountSaveData> violation = validate.stream().findFirst().orElse(null);

        assertThat(violation).isNotNull();
        assertThat(violation.getMessage()).isEqualTo("공백일 수 없습니다");
    }

    public static Stream<Arguments> provideAccountDataWithEmptyField() {
        return Stream.of(
                Arguments.of(AccountSaveData.of(null, ACCOUNT_EMAIL, ACCOUNT_PASSWORD)),
                Arguments.of(AccountSaveData.of(ACCOUNT_NAME, "", ACCOUNT_PASSWORD)),
                Arguments.of(AccountSaveData.of(ACCOUNT_NAME, ACCOUNT_EMAIL, ""))
        );
    }

}
