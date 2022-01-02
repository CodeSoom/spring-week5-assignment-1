package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UserModificationData {

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 4, max = 1024)
    private String password;

    @Builder
    public UserModificationData(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
