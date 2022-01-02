package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UserResultData {

    private Long id;

    private String name;

    private String email;

    @Builder
    public UserResultData(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
