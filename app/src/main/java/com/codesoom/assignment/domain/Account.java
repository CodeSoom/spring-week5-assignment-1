package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.AccountData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public static Account from(AccountData data) {
        return Account.of(data.getId(), data.getName(), data.getEmail(), data.getPassword());
    }
    public static Account of(String name, String email, String password) {
        return Account.of(null, name, email, password);
    }
    public static Account of(Long id, String name, String email, String password) {
        return Account.builder()
                .id(id)
                .name(name)
                .password(password)
                .email(email)
                .build();
    }

    public void change(Account source) {
        name = source.name;
        email = source.email;
        password = source.password;
    }
}
