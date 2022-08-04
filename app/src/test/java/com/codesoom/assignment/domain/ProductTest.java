package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product 클래스의")
class ProductTest {

    public static final long ID = 1L;
    public static final String NAME = "장난감";
    public static final String MAKER = "codesoom";
    public static final int PRICE = 9999;
    public static final String URL = "picture.url";

    @Test
    void creationWithBuilder() {
        Product product = Product.builder()
                .id(ID)
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .build();

        assertThat(product.getId()).isEqualTo(ID);
        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getMaker()).isEqualTo(MAKER);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getImageUrl()).isNull();
    }

    @Test
    @DisplayName("상품은 상태값을 가질 수 있다.")
    void productCanHasStatus() {
        Product product = Product.builder()
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .status(Status.SALE)
                .build();

        assertThat(product.getStatus()).isEqualTo(Status.SALE);
    }
}
