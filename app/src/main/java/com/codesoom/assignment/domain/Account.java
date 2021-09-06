package com.codesoom.assignment.domain;

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

    public void change(Account source) {
        name = source.name;
        email = source.email;
        password = source.password;
    }
}
