package com.codesoom.assignment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserDTO {

    private final Long id;
    private final String name;
    private final String email;
    @JsonIgnore
    private final String password;

    @JsonCreator
    public UserDTO(@JsonProperty("id") Long id,
                   @JsonProperty("name") String name,
                   @JsonProperty("email") String email,
                   @JsonProperty("password") String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
