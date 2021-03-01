package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    private final Product givenProduct = Product.builder()
            .id(1L)
            .name("쥐돌이")
            .maker("냥이월드")
            .price(5000)
            .build();

    @Test
    void creationWithBuilder() {
        assertThat(givenProduct.getId()).isEqualTo(1L);
        assertThat(givenProduct.getName()).isEqualTo("쥐돌이");
        assertThat(givenProduct.getMaker()).isEqualTo("냥이월드");
        assertThat(givenProduct.getPrice()).isEqualTo(5000);
        assertThat(givenProduct.getImageUrl()).isNull();
    }

    @Test
    void change() {
        givenProduct.change("쥐순이", "코드숨", 10000,
                "http://localhost:8080/rat");

        assertThat(givenProduct.getName()).isEqualTo("쥐순이");
        assertThat(givenProduct.getMaker()).isEqualTo("코드숨");
        assertThat(givenProduct.getPrice()).isEqualTo(10000);
        assertThat(givenProduct.getImageUrl())
                .isEqualTo("http://localhost:8080/rat");
    }

    @Test
    void toStringTest() {
        final String productString = Product.builder()
                .id(1L)
                .name("쥐돌이")
                .maker("냥이월드")
                .price(5000)
                .toString();
        assertThat(productString)
                .contains(givenProduct.getId().toString())
                .contains(givenProduct.getName())
                .contains(givenProduct.getMaker())
                .contains(givenProduct.getPrice().toString());
    }
}
