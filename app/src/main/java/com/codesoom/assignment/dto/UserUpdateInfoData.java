package com.codesoom.assignment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * User의 정보를 변경할 시 전달되는 DTO 객체입니다.
 */
@Getter
public class UserUpdateInfoData {
    @NotBlank
    private final String name;

    @NotBlank
    private final String password;

    @Builder
    @JsonCreator
    public UserUpdateInfoData(@JsonProperty("name") String name,
                              @JsonProperty("password") String password) {
        this.name = name;
        this.password = password;
    }
}
