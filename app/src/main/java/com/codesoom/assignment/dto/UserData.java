package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserData {

    private Long id;

    @NotEmpty
    @Mapping
    private String name;

    @NotEmpty
    @Mapping
    private String email;

    @NotEmpty
    @Mapping
    private String password;

    @Builder
    public UserData(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
