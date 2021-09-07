package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class AccountData {

    private Long id;

    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("email")
    private String email;

    @NotBlank
    @Mapping("password")
    @JsonIgnore
    private String password;

    public static AccountData from(Account account) {
        return AccountData.builder()
                .id(account.getId())
                .name(account.getName())
                .password(account.getPassword())
                .email(account.getEmail())
                .build();
    }

    public static AccountData of(String name, String email, String password) {
        return new AccountData(null, name, email, password);
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
