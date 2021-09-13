package com.codesoom.assignment;

import com.codesoom.assignment.dto.AccountSaveData;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.codesoom.assignment.Constant.ACCOUNT_EMAIL;
import static com.codesoom.assignment.Constant.ACCOUNT_NAME;
import static com.codesoom.assignment.Constant.ACCOUNT_PASSWORD;

/**
 * 필드에 공백(or null)이 포함된 회원 객체 매개변수를 전달한다.
 */
public class ProvideInvalidAccountArguments implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        String[][] params = {{"name", ""}, {"name", null}, {"email", ""}, {"email", null}, {"password", ""}, {"password", null}};

        final List<AccountSaveData> invalidProducts = IntStream.range(0, 6)
                .mapToObj(index -> {
                    final AccountSaveData accountSaveData = AccountSaveData.of(ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PASSWORD);
                    ReflectionTestUtils.setField(accountSaveData, params[index][0], params[index][1]);

                    return accountSaveData;
                }).collect(Collectors.toList());

        return Stream.of(Arguments.of(invalidProducts));
    }
}
