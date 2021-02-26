package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    private static final String NAME = "뱀 장난감";

    private static final String MAKER = "야옹이네 장난감";

    private static final Integer PRICE = 3000;

    private static final String IMAGE = "https://bit.ly/3qzXRME";

    @DisplayName("상품이 정상적으로 등록되었는지 확인하기 위해 상품 정보를 확인합니다")
    void creationWithBuilder() {
        Product product = Product.builder()
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .image(IMAGE)
                .build();

        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getMaker()).isEqualTo(MAKER);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getImage()).isEqualTo(IMAGE);
    }

    @Test
    @DisplayName("상품의 정보가 정상적으로 변경되었는지 확인하기 위해 상품 정보를 확인합니다")
    void updateWith() {
        Product product = Product.builder()
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .image(IMAGE)
                .build();

        product.updateWith(Product.builder()
                .name("물고기 장난감")
                .maker("애옹이네 장난감")
                .price(5000)
                .image("https://bit.ly/2M4YXkw")
                .build()
        );

        assertThat(product.getName()).isEqualTo("물고기 장난감");
        assertThat(product.getMaker()).isEqualTo("애옹이네 장난감");
        assertThat(product.getPrice()).isEqualTo(5000);
        assertThat(product.getImage()).isEqualTo("https://bit.ly/2M4YXkw");
    }
}
