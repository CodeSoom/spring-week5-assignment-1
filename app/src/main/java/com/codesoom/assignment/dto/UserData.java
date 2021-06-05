package com.codesoom.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserData {
    public Long id;
    @NotBlank(message = "잘못된 요청입니다. 파라미터를 확인해 주세요."
            ,groups = {UserValidationGroups.createUserGroup.class
            ,UserValidationGroups.updateUserGroup.class})
    public String name;
    @NotBlank(message = "잘못된 요청입니다. 파라미터를 확인해 주세요."
            ,groups = {UserValidationGroups.createUserGroup.class})
    public String email;
    @NotBlank(message = "잘못된 요청입니다. 파라미터를 확인해 주세요."
            ,groups = {UserValidationGroups.createUserGroup.class
            ,UserValidationGroups.updateUserGroup.class})
    public String password;
}
