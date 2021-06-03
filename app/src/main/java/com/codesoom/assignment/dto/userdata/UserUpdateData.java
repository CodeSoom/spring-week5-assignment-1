package com.codesoom.assignment.dto.userdata;

import com.codesoom.assignment.dto.UserData;
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
public class UserUpdateData extends UserData {

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String password;
}
