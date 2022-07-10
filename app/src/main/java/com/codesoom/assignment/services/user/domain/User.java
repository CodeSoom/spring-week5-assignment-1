package com.codesoom.assignment.services.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @NotBlank
    private String name;

    @Setter
    @NotBlank
    private String email;

    @Setter
    @NotBlank
    private String password;

    public User(@NotNull String name, @NotNull String email, @NotNull String password) {
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(password);
    }

    public User(@NotNull Long id, @NotNull String name, @NotNull String email, @NotNull String password) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(password);
    }
}
