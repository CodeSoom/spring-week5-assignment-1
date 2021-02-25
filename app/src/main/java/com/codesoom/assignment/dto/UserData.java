package com.codesoom.assignment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Accessors(fluent = true)
@Getter
public class UserData {
    @JsonProperty
    Long id;

    @NotBlank
    @JsonProperty
    String email;

    @NotBlank
    @JsonProperty
    String name;

    @NotBlank
    @JsonProperty
    String password;

    @JsonCreator
    UserData(
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
