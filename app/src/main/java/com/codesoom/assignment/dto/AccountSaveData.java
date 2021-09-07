package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountSaveData {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public static AccountSaveData from(Account account) {
        return AccountSaveData.builder()
                .id(account.getId())
                .name(account.getName())
                .password(account.getPassword())
                .email(account.getEmail())
                .build();
    }

    public static AccountSaveData of(String name, String email, String password) {
        return new AccountSaveData(null, name, email, password);
    }

    public Account toAccount() {
        return Account.builder()
                .id(id)
                .name(name)
                .password(password)
                .email(email)
                .build();
    }
}
