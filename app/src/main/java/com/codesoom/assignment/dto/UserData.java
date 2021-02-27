package com.codesoom.assignment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dozermapper.core.Mapping;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Accessors(fluent = true)
@Getter
public class UserData {
    @JsonProperty("id")
    Long id;

    @NotBlank
    @JsonProperty("email")
    @Mapping("email")
    String email;

    @NotBlank
    @JsonProperty("name")
    @Mapping("name")
    String name;

    @NotBlank
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
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
