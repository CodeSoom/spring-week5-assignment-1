package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .name("쥐돌이")
                .maker("냥이월드")
                .price(5000)
                .build();
    }

    @Test
    void creationWithBuilder() {
        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("쥐돌이");
        assertThat(product.getMaker()).isEqualTo("냥이월드");
        assertThat(product.getPrice()).isEqualTo(5000);
        assertThat(product.getImageUrl()).isNull();
    }

    @Test
    void change() {
        product.changeWith(Product.builder()
                .name("쥐순이")
                .maker("코드숨")
                .price(10000)
                .imageUrl("http://localhost:8080/rat")
                .build());

        assertThat(product.getName()).isEqualTo("쥐순이");
        assertThat(product.getMaker()).isEqualTo("코드숨");
        assertThat(product.getPrice()).isEqualTo(10000);
        assertThat(product.getImageUrl())
                .isEqualTo("http://localhost:8080/rat");
    }

    @Test
    void toStringTest() {
        assertThat(product.toString())
                .isEqualTo("Product(id=1, name=쥐돌이, maker=냥이월드, price=5000, imageUrl=null)");
        assertThat(Product.builder().toString())
                .isEqualTo("Product.ProductBuilder(id=null, name=null, maker=null, price=null, imageUrl=null)");
    }
}
