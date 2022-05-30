package com.codesoom.assignment.domain;

import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class User {

    private String email;

    private String name;

    private String password;

}
