package com.codesoom.assignment.user.dto;

import com.github.dozermapper.core.Mapping;
import javax.validation.constraints.NotBlank;
import lombok.Builder;

public class UserUpdateData {

  @NotBlank
  @Mapping("name")
  private String name;

  @Mapping("email")
  private String email;

  @NotBlank
  @Mapping("password")
  private String password;

  @Builder
  public UserUpdateData(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }
}
