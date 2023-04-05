package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Member {
    private String name;
    private String phone;

    public void update(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
