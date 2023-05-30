package com.codesoom.assignment.web.shop.user.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class UserResultData {
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    private String name;
}
