package com.codesoom.assignment.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserDataTest {
    private final Long givenID = 1L;

    @Test
    void isValidWhenItsFalse() {
        final UserData userData = new UserData(
                givenID,
                null,
                null,
                null
        );

        assertThat(userData.isValid()).isFalse();
    }

    @Test
    void isValidItsNotNullPassword() {
        final UserData userData = new UserData(
                givenID,
                null,
                null,
                "secret"
        );

        assertThat(userData.isValid()).isTrue();
    }

    @Test
    void stringToUserDataTest() throws JsonProcessingException {
        final String givenString = "{" +
                "\"id\":1," +
                "\"email\":\"juuni.ni.i@gmail.com\"," +
                "\"name\":\"juunini\"," +
                "\"password\":\"secret\"" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        final UserData userData = objectMapper.readValue(givenString, UserData.class);

        assertThat(userData.id()).isEqualTo(1L);
        assertThat(userData.email()).isEqualTo("juuni.ni.i@gmail.com");
        assertThat(userData.name()).isEqualTo("juunini");
        assertThat(userData.password()).isEqualTo("secret");
    }
}
