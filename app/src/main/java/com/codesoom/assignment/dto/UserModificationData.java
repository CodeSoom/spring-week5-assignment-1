package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModificationData {
    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("password")
    private String password;
}
