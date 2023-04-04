package com.codesoom.assignment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserResultData {
    private Long id;
    @NotBlank
    private String email;
    @NotBlank
    private String name;
}
