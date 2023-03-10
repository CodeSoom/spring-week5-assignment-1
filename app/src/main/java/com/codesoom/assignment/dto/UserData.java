package com.codesoom.assignment.dto;

import com.codesoom.assignment.group.UserCreateValidationGroup;
import com.codesoom.assignment.group.UserUpdateValidationGroup;
import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserData {

    private Long id;

    @Mapping("name")
    @NotBlank(message = "이름을 입력하지 않았습니다. 이름을 입력해주세요."
            ,groups = {UserCreateValidationGroup.class, UserUpdateValidationGroup.class})
    private String name;
    
    @Mapping("email")
    @NotBlank(message = "이메일을 입력하지 않았습니다. 이메일을 입력해주세요."
            ,groups = {UserCreateValidationGroup.class})
    @Email(message = "잘못된 형식의 이메일입니다. 형식에 맞는 이메일을 입력해주세요."
            ,groups = {UserCreateValidationGroup.class})
    private String email;

    @Mapping("password")
    @NotBlank(message = "비밀번호를 입력하지 않았습니다. 비밀번호를 입력해주세요."
            ,groups = {UserCreateValidationGroup.class, UserUpdateValidationGroup.class})
    private String password;





}
