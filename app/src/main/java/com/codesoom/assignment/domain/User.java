package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Builder
    public User(@NotBlank String name, @NotBlank String email,
                @NotBlank String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void update(User source) {
        this.name = source.getName();
        this.email = source.getEmail();
        this.password = source.getPassword();
    }
}
