package com.codesoom.assignment.user.dto;

import com.codesoom.assignment.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserResponseDtoTest {
    private static Long USER1_ID = 1L;
    private static Long USER2_ID = 2L;

    @DisplayName("id 기준으로 상품 요청 객체의 해쉬값를 비교한다")
    @Test
    void compareHashcode() {
        UserResponseDto dto1 = getUserResponseDto(USER1_ID);
        UserResponseDto dto2 = getUserResponseDto(USER1_ID);

        assertThat(dto1).hasSameHashCodeAs(dto2);

        UserResponseDto dto3 = getUserResponseDto(USER2_ID);
        assertThat(dto1.hashCode()).isNotEqualTo(dto3.hashCode());
    }

    @DisplayName("id 기준으로 상품 요청 객체를 비교한다.")
    @Test
    void equalsWithSameId() {
        UserResponseDto dto1 = getUserResponseDto(USER1_ID);
        UserResponseDto dto2 = getUserResponseDto(USER1_ID);

        assertThat(dto1.equals(dto1)).isTrue();
        assertThat(dto1.equals(dto2)).isTrue();

        UserResponseDto dto3 = getUserResponseDto(USER2_ID);
        assertThat(dto3.equals(dto2)).isFalse();
    }

    @DisplayName("서로 다른 객체 비교후, 다르면 false를 리턴한다")
    @Test
    void equalsWithDifferentObject() {
        UserResponseDto dto1 = getUserResponseDto(USER1_ID);
        Product product = Product.builder().build();

        assertThat(dto1.equals(product)).isFalse();
    }

    private UserResponseDto getUserResponseDto(Long userId) {
        return UserResponseDto.builder()
                .id(userId)
                .build();
    }
}
