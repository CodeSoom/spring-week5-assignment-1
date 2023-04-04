package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Member {
    private String name;
    private String phone;
}
