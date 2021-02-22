package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    private static final Long ID = 1L;

    private static final String NAME = "뱀 장난감";

    private static final String MAKER = "야옹이네 장난감";

    private static final Integer PRICE = 3000;

    private static final String IMAGE = "https://bit.ly/3qzXRME";

    @DisplayName("장난감이 정상적으로 등록되었는지 확인하기 위해 상품 정보를 확인합니다")
    void creationWithBuilder() {
        Product product = Product.builder()
                .id(ID)
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .image(IMAGE)
                .build();

        assertThat(product.getId()).isEqualTo(ID);
        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getMaker()).isEqualTo(MAKER);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getImage()).isEqualTo(IMAGE);
    }

    @Test
    @DisplayName("장난감의 정보가 정상적으로 변경되었는지 확인하기 위해 상품 정보를 확인합니다")
    void change() {
        Product product = Product.builder()
                .id(ID)
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .build();

        Product source = Product.builder()
                .id(3L)
                .name("물고기 장난감")
                .maker("애옹이네 장난감")
                .price(5000)
                .build();

        product.change(source);

        assertThat(product.getId()).isEqualTo(3L);
        assertThat(product.getName()).isEqualTo("물고기 장난감");
        assertThat(product.getMaker()).isEqualTo("애옹이네 장난감");
        assertThat(product.getPrice()).isEqualTo(5000);
    }
}
