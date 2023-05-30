package com.codesoom.assignment.web.shop.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberRequest {
    @NotBlank
    protected String name;
    @NotBlank
    protected String phone;

    public MemberRequest(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
