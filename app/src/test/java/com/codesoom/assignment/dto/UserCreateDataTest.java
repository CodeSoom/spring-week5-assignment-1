package com.codesoom.assignment.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateDataTest {

    @Test
    void builder() {
        assertThat(UserCreateData.builder().toString())
                .isEqualTo("UserCreateData.UserCreateDataBuilder(name=null, email=null, password=null)");
    }
}