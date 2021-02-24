package com.codesoom.assignment.user.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserDataTest {
    private static Long USER1_ID = 1L;
    private static Long USER2_ID = 2L;

    @DisplayName("id 기준으로 상품 요청 객체의 해쉬값를 비교한다")
    @Test
    void compareHashcode() {
        UserData dto1 = getUserResponseDto(USER1_ID);
        UserData dto2 = getUserResponseDto(USER1_ID);

        assertThat(dto1).hasSameHashCodeAs(dto2);

        UserData dto3 = getUserResponseDto(USER2_ID);
        assertThat(dto1.hashCode()).isNotEqualTo(dto3.hashCode());
    }

    @DisplayName("id 기준으로 상품 요청 객체를 비교한다.")
    @Test
    void equalsWithSameId() {
        UserData dto1 = getUserResponseDto(USER1_ID);
        UserData dto2 = getUserResponseDto(USER1_ID);

        assertThat(dto1.equals(dto1)).isTrue();

        UserData dto3 = getUserResponseDto(USER2_ID);
        assertThat(dto3.equals(dto2)).isFalse();
    }

    @DisplayName("서로 다른 객체 비교후, 다르면 false를 리턴한다")
    @Test
    void equalsWithDifferentObject() {
        UserData dto1 = getUserResponseDto(USER1_ID);
        UserSaveRequestDto product = UserSaveRequestDto.builder().build();

        assertThat(dto1.equals(product)).isFalse();
    }

    private UserData getUserResponseDto(Long userId) {
        return UserData.builder()
                .id(userId)
                .build();
    }
}
