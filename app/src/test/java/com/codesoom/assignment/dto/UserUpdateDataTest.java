package com.codesoom.assignment.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserUpdateDataTest {

    @Test
    void builder() {
        assertThat(UserUpdateData.builder().toString())
                .isEqualTo("UserUpdateData.UserUpdateDataBuilder(name=null, email=null, password=null)");
    }
}