package com.codesoom.assignment.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String password;

    public void changWith(User source) {
        this.id = source.id;
        this.name = source.name;
        this.email = source.email;
        this.password = source.password;
    }
}
