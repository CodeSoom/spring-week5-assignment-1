package com.codesoom.assignment.dto.userdata;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateData extends UserData {

    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Email
    @Mapping("email")
    private String email;

    @NotBlank
    @Mapping("password")
    private String password;
}
