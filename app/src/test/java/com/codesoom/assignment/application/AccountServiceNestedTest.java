package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.AccountRepository;
import com.codesoom.assignment.dto.AccountSaveData;
import com.codesoom.assignment.dto.AccountUpdateData;
import com.codesoom.assignment.exceptions.AccountFieldValidException;
import com.codesoom.assignment.exceptions.AccountNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static com.codesoom.assignment.Constant.ACCOUNT_EMAIL;
import static com.codesoom.assignment.Constant.ACCOUNT_NAME;
import static com.codesoom.assignment.Constant.ACCOUNT_PASSWORD;
import static com.codesoom.assignment.Constant.OTHER_ACCOUNT_EMAIL;
import static com.codesoom.assignment.Constant.OTHER_ACCOUNT_NAME;
import static com.codesoom.assignment.Constant.OTHER_ACCOUNT_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("AccountService 클래스")
@DataJpaTest
public class AccountServiceNestedTest {
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountService = new AccountService(accountRepository);
    }

    @Nested
    @DisplayName("findAccount 메서드는")
    class Describe_findAccount {
        private AccountSaveData accountSaveData;

        @BeforeEach
        void prepareFindAccount() {
            accountSaveData = accountService.creation(AccountSaveData.of(ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD));
        }

        @DisplayName("식별자가 존재하는 회원인 경우")
        @Nested
        class Context_exists_id {
            @DisplayName("정상적으로 회원이 조회 된다.")
            @Test
            void findAccount() {
                final AccountSaveData foundAccountData = accountService.findAccount(accountSaveData.getId());

                assertThat(foundAccountData.getName()).isEqualTo(accountSaveData.getName());
                assertThat(foundAccountData.getEmail()).isEqualTo(accountSaveData.getEmail());
                assertThat(foundAccountData.getPassword()).isEqualTo(accountSaveData.getPassword());
            }
        }

        @DisplayName("식별자가 존재하지 않는 회원인 경우")
        @Nested
        class Context_not_exists_id {
            @DisplayName("AccountNotFoundException 예외가 발생한다.")
            @Test
            void findNotExistsAccount() {
                assertThatThrownBy(() -> accountService.findAccount(100L))
                        .isInstanceOf(AccountNotFoundException.class)
                        .hasMessage(String.format(AccountNotFoundException.DEFAULT_MESSAGE, 100L));
            }
        }
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {

        @Nested
        @DisplayName("인자값이 정상인 경우")
        class Context_with_valid_data {
            private AccountSaveData source = AccountSaveData.of(ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD);

            @DisplayName("Account 를 생성해 반환합니다.")
            @Test
            void createAccount() {

                final AccountSaveData savedData = accountService.creation(source);

                assertThat(savedData.getId()).isNotNull();
                assertThat(savedData.getName()).isEqualTo(ACCOUNT_NAME);
                assertThat(savedData.getEmail()).isEqualTo(ACCOUNT_EMAIL);
                assertThat(savedData.getPassword()).isEqualTo(ACCOUNT_PASSWORD);
            }
        }

        @Nested
        @DisplayName("인자값이 정상인 경우")
        class Context_with_invalid_data {
            private AccountSaveData accountFromEmptyName;
            private AccountSaveData accountFromEmptyEmail;
            private AccountSaveData accountFromEmptyPassword;

            @BeforeEach
            void prepareCreation() {
                accountFromEmptyName = AccountSaveData.of("", ACCOUNT_EMAIL, ACCOUNT_PASSWORD);
                accountFromEmptyEmail = AccountSaveData.of(ACCOUNT_NAME, "", ACCOUNT_PASSWORD);
                accountFromEmptyPassword = AccountSaveData.of(ACCOUNT_NAME, ACCOUNT_EMAIL, "");
            }

            @DisplayName("Account 를 생성해 반환합니다.")
            @Test
            void createAccount() {
                final String causeMessage = "공백일 수 없습니다";

                assertThatThrownBy(() -> accountService.creation(accountFromEmptyName))
                        .isInstanceOf(AccountFieldValidException.class)
                        .hasMessageContaining(String.format(AccountFieldValidException.DEFAULT_MESSAGE_FORM,
                                "name",
                                causeMessage));

                assertThatThrownBy(() -> accountService.creation(accountFromEmptyEmail))
                        .isInstanceOf(AccountFieldValidException.class)
                        .hasMessageContaining(String.format(AccountFieldValidException.DEFAULT_MESSAGE_FORM,
                                "email",
                                causeMessage));

                assertThatThrownBy(() -> accountService.creation(accountFromEmptyPassword))
                        .isInstanceOf(AccountFieldValidException.class)
                        .hasMessageContaining(String.format(AccountFieldValidException.DEFAULT_MESSAGE_FORM,
                                "password",
                                causeMessage));

            }
        }
    }


    @Nested
    @DisplayName("patch 메서드는")
    class Describe_patch {
        private AccountSaveData accountData;

        @BeforeEach
        void preparePatch() {
            accountData = accountService.creation(AccountSaveData.of(ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD));
        }

        @Nested
        @DisplayName("수정하려는 식별자가 존재하는 경우")
        class Context_with_exists_id {
            @DisplayName("정상적으로 수정된다.")
            @Test
            void patchAccount() {
                final AccountUpdateData target = AccountUpdateData.of(OTHER_ACCOUNT_NAME, OTHER_ACCOUNT_EMAIL, OTHER_ACCOUNT_PASSWORD);
                final AccountSaveData updatedData = accountService.patchAccount(accountData.getId(), target);

                assertThat(updatedData.getId()).isEqualTo(accountData.getId());
                assertThat(updatedData.getName()).isEqualTo(target.getName());
                assertThat(updatedData.getEmail()).isEqualTo(target.getEmail());
                assertThat(updatedData.getPassword()).isEqualTo(target.getPassword());

            }
        }

        @Nested
        @DisplayName("수정하려는 식별자가 존재하지 않는 경우")
        class Context_not_exists_id {
            @DisplayName("AccountNotFoundException 예외가 발생한다.")
            @Test
            void patchNotExistsId() {
                final AccountUpdateData target = AccountUpdateData.of(OTHER_ACCOUNT_NAME, OTHER_ACCOUNT_EMAIL, OTHER_ACCOUNT_PASSWORD);

                assertThatThrownBy(() -> accountService.patchAccount(1000L, target))
                        .isInstanceOf(AccountNotFoundException.class)
                        .hasMessage(String.format(AccountNotFoundException.DEFAULT_MESSAGE, 1000L));
            }
        }
    }


    @Nested
    @DisplayName("delete 메서드는")
    class Describe_delete {
        private AccountSaveData accountData = AccountSaveData.of(ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD);

        @Nested
        @DisplayName("삭제하고자 하는 회원 식별자가 존재하는 경우")
        class Context_with_exists_id {
            @BeforeEach
            void prepareDelete() {
                accountData = accountService.creation(accountData);
            }

            @DisplayName("정상적으로 삭제된다.")
            @Test
            void deleteAccount() {
                accountService.deleteAccount(accountData.getId());

                assertThatThrownBy(() -> accountService.findAccount(accountData.getId()))
                        .isInstanceOf(AccountNotFoundException.class)
                        .hasMessage(String.format(AccountNotFoundException.DEFAULT_MESSAGE, accountData.getId()));

            }
        }

        @Nested
        @DisplayName("삭제하고자 하는 회원 식별자가 존재하지 않는 경우")
        class Context_not_exists_id {

            @DisplayName("AccountNotFoundException 예외가 발생한다.")
            @Test
            void deleteNotExistsId() {
                assertThatThrownBy(() -> accountService.deleteAccount(1000L))
                        .isInstanceOf(AccountNotFoundException.class)
                        .hasMessage(String.format(AccountNotFoundException.DEFAULT_MESSAGE, 1000L));
            }

        }
    }


}
