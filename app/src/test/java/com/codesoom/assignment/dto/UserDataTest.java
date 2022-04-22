package com.codesoom.assignment.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserDataTest {

    @Test
    void builder() {
        assertThat(UserData.builder().toString())
                .isEqualTo("UserData.UserDataBuilder(name=null, email=null, password=null)");
    }
}