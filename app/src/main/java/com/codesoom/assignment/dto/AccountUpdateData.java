package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateData {

    private String name;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public static AccountUpdateData of(String name, String email, String password) {
        return new AccountUpdateData(name, email, password);
    }

    public Account toAccount() {
        return Account.builder()
                .name(name)
                .password(password)
                .email(email)
                .build();
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
