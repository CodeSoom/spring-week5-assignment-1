package com.codesoom.assignment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dozermapper.core.Mapping;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Accessors(fluent = true)
@Getter
public class UserData {
    @JsonProperty("id")
    Long id;

    @NotBlank(message = "email 은 빈칸일 수 없습니다.")
    @JsonProperty("email")
    @Mapping("email")
    @Email
    String email;

    @NotBlank(message = "name 은 빈칸일 수 없습니다.")
    @JsonProperty("name")
    @Mapping("name")
    String name;

    @NotBlank(message = "password 는 빈칸일 수 없습니다.")
    @JsonProperty("password")
    @Mapping("password")
    String password;

    @JsonCreator
    public UserData(
            @JsonProperty("id") Long id,
            @JsonProperty("email") String email,
            @JsonProperty("name") String name,
            @JsonProperty("password") String password
    ) {
        this.id = id;
        this.email = email == null ? "" : email;
        this.name = name == null ? "" : name;
        this.password = password == null ? "" : password;
    }

    public boolean isValid() {
        return this.name.isBlank() ||
                this.password.isBlank();
    }
}
