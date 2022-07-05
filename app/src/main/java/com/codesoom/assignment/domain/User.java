package com.codesoom.assignment.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

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


    public User update(final User source){
        email = source.email;
        name = source.name;
        password = source.password;
    };
}
