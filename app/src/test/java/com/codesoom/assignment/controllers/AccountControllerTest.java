package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.AccountService;
import com.codesoom.assignment.domain.Account;
import com.codesoom.assignment.dto.AccountData;
import com.codesoom.assignment.exceptions.AccountNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;
import java.util.stream.Stream;

import static com.codesoom.assignment.Constant.ACCOUNT_EMAIL;
import static com.codesoom.assignment.Constant.ACCOUNT_NAME;
import static com.codesoom.assignment.Constant.ACCOUNT_PASSWORD;
import static com.codesoom.assignment.Constant.OTHER_ACCOUNT_EMAIL;
import static com.codesoom.assignment.Constant.OTHER_ACCOUNT_NAME;
import static com.codesoom.assignment.Constant.OTHER_ACCOUNT_PASSWORD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("AccountController 테스트")
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    private static final String API_PATH = "/user";
    private static final String FORM = "{\"id\":%s,\"name\":\"%s\",\"email\":\"%s\", \"password\":\"%s\"}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;


    @BeforeEach
    void setUp() {
        Account account = Account.of(1L, ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD);

        given(accountService.creation(any(AccountData.class))).willReturn(AccountData.from(account));

        given(accountService.patchAccount(eq(account.getId()), any(AccountData.class)))
                .willReturn(AccountData.of(OTHER_ACCOUNT_NAME, OTHER_ACCOUNT_EMAIL, OTHER_ACCOUNT_PASSWORD));

        given(accountService.patchAccount(eq(100L), any(AccountData.class)))
                .willThrow(new AccountNotFoundException(100L));
    }

    @DisplayName("회원 정보 등록 API를 호출해 회원 정보를 등록할 수 있다. - POST /user")
    @Test
    void createAccount() throws Exception {
        String content = String.format(FORM, null, ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD);
        mockMvc.perform(
                        post(API_PATH)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(ACCOUNT_NAME))
                .andExpect(jsonPath("$.email").value(ACCOUNT_EMAIL))
                .andExpect(jsonPath("$.password").doesNotHaveJsonPath());
    }

    @DisplayName("잘못 된 정보로 회원 등록 API를 호출하면 등록에 실패한다. - POST /user BODY {invalidData}")
    @ParameterizedTest
    @MethodSource("provideInvalidAccountData")
    void createWithInvalidAccount(String fieldName, String content) throws Exception {
        mockMvc.perform(
                        post(API_PATH)
                                .locale(Locale.KOREAN)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("[" + fieldName + "](은)는 공백일 수 없습니다 입력된 값: []"));
    }

    public static Stream<Arguments> provideInvalidAccountData() {
        return Stream.of(
                Arguments.of("name", String.format(FORM, null, "", ACCOUNT_EMAIL, ACCOUNT_PASSWORD)),
                Arguments.of("email", String.format(FORM, null, ACCOUNT_NAME, "", ACCOUNT_PASSWORD)),
                Arguments.of("password", String.format(FORM, null, ACCOUNT_NAME, ACCOUNT_EMAIL, ""))
        );
    }

    @DisplayName("등록된 회원의 정보를 수정할 수 있다. - PATCH /user/{id}")
    @Test
    void patchWithExistsAccount() throws Exception {
        final AccountData savedAccountData = accountService.creation(
                AccountData.of(ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD));

        final String content = String.format(FORM,
                savedAccountData.getId(),
                OTHER_ACCOUNT_NAME,
                OTHER_ACCOUNT_EMAIL,
                OTHER_ACCOUNT_PASSWORD);

        mockMvc.perform(
                        patch(API_PATH + "/" + savedAccountData.getId())
                                .locale(Locale.KOREAN)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(OTHER_ACCOUNT_NAME))
                .andExpect(jsonPath("$.email").value(OTHER_ACCOUNT_EMAIL))
                .andExpect(jsonPath("$.password").doesNotHaveJsonPath());
    }

    @DisplayName("등록되지 않은 회원의 정보는 수정할 수 없다. - PATCH /user/{notExistsId}")
    @Test
    void patchNotExistsAccount() throws Exception {
        final String content = String.format(FORM,
                100L,
                OTHER_ACCOUNT_NAME,
                OTHER_ACCOUNT_EMAIL,
                OTHER_ACCOUNT_PASSWORD);

        mockMvc.perform(
                        patch(API_PATH + "/100")
                                .locale(Locale.KOREAN)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value(String.format(AccountNotFoundException.DEFAULT_MESSAGE, 100L)));
    }

    @DisplayName("식별자로 회원 정보를 삭제할 수 있다 - DELETE /user/{id}")
    @ParameterizedTest
    @ValueSource(longs = {0, 1, 2, 3, 4, 5})
    void deleteAccount(long id) throws Exception {
        mockMvc.perform(delete(API_PATH+"/"+ id))
                .andExpect(status().isNoContent());

        verify(accountService).deleteAccount(id);
    }

}
