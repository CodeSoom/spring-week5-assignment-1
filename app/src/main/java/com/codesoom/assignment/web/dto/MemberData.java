package com.codesoom.assignment.web.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 고양이 장난감 데이터 - Web DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberData {
    private Long id;

    @NotBlank(groups = {MemberDataValidation.create.class, MemberDataValidation.update.class})
    @Mapping("name")
    private String name;

    @NotBlank(groups = {MemberDataValidation.create.class, MemberDataValidation.update.class})
    @Mapping("password")
    private String password;

    @NotBlank(groups = {MemberDataValidation.create.class})
    @Mapping("email")
    private String email;

}
