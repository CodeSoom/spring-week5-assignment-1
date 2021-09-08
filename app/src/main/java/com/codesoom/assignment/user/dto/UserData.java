package com.codesoom.assignment.user.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Builder
public class UserData {
  @NotBlank
  @Mapping("name")
  private final String name;

  @NotBlank
  @Mapping("email")
  private final String email;

  @NotBlank
  @Mapping("password")
  private final String password;
}
