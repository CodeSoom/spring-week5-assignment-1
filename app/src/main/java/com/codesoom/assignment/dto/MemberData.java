package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@Setter
public class MemberData {

    @NotBlank
    private String name;
    @NotBlank
    private String phone;

    @Override
    public String toString() {
        return "{\"name\":\"" + this.name + "\", \"phone\": \"" + this.phone + "\"}";
    }
}
