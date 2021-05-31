package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 사용자 데이터.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    @NotBlank(message = "이름은 필수 입력 항목입니다.",
              groups = {
                      UserValidationGroups.post.class,
                      UserValidationGroups.patch.class
              })
    @Size(min = 2,
          message = "이름은 2자 이상이어야 합니다.",
          groups = {
                  UserValidationGroups.post.class,
                  UserValidationGroups.patch.class
          })
    @Mapping("name")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 항목입니다.",
              groups = UserValidationGroups.post.class)
    @Email(message = "올바른 형식의 이메일 주소여야 합니다.",
           groups = {
                   UserValidationGroups.post.class,
                   UserValidationGroups.patch.class
           })
    @Mapping("email")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.",
              groups = {
                      UserValidationGroups.post.class,
                      UserValidationGroups.patch.class
              })
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.",
          groups = {
                  UserValidationGroups.post.class,
                  UserValidationGroups.patch.class
          })
    @Mapping("password")
    private String password;
}
