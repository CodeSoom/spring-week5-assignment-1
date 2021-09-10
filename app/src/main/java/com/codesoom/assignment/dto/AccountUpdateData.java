package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Account;
import com.codesoom.assignment.exceptions.AccountFieldValidException;
import com.codesoom.assignment.infra.Validators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateData {

    private String name;

    private String email;

    private String password;

    public static AccountUpdateData of(String name, String email, String password) {
        return new AccountUpdateData(name, email, password);
    }

    public Account toAccount() {
        final Set<ConstraintViolation<Object>> validate = Validators.validate(this);

        if (validate.isEmpty()) {
            return Account.builder()
                    .name(name)
                    .password(password)
                    .email(email)
                    .build();
        }

        throw new AccountFieldValidException(validate);
    }

    public boolean isValid() {
        if (isEmpty(name)) {
            return false;
        }
        if (isEmpty(password)) {
            return false;
        }
        return !isEmpty(email);
    }

    private boolean isEmpty(String field) {
        return "".equals(field);
    }
}
