package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    public interface ValidateCreate {};
    public interface ValidateUpdate {};

    private Long id;

    @NotBlank(groups = { ValidateCreate.class, ValidateUpdate.class })
    @Mapping("name")
    private String name;

    @NotBlank(groups = ValidateCreate.class)
    @Mapping("email")
    private String email;

    @NotBlank(groups = { ValidateCreate.class, ValidateUpdate.class })
    @Mapping("password")
    private String password;
}
