package com.codesoom.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class UserData {
    public UserData(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Getter
    private Long id;
    @Getter
    private String name;
    @Getter
    private String email;
    @Getter
    private String password;
}
