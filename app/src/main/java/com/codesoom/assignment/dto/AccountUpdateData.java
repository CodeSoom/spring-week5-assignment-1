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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateData {

    private Long id;

    private String name;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public static AccountUpdateData from(Account account) {
        return AccountUpdateData.builder()
                .id(account.getId())
                .name(account.getName())
                .password(account.getPassword())
                .email(account.getEmail())
                .build();
    }

    public static AccountUpdateData of(String name, String email, String password) {
        return new AccountUpdateData(null, name, email, password);
    }

    public Account toAccount() {
        return Account.builder()
                .id(id)
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
