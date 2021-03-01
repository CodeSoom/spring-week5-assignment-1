package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @DisplayName("빌더 패턴을 이용하여 상품을 생성했을 때, 유효한 값을 반환한다.")
    @Test
    void creationWithBuilder() {
        Product product = Product.builder()
                .id(1L)
                .name("쥐돌이")
                .maker("냥이월드")
                .price(5000)
                .build();

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("쥐돌이");
        assertThat(product.getMaker()).isEqualTo("냥이월드");
        assertThat(product.getPrice()).isEqualTo(5000);
        assertThat(product.getImageUrl()).isNull();
    }

    @DisplayName("빌더 패턴을 이용하여 생성한 상품을 변경하였을 때, 유효한 값을 반환한다.")
    @Test
    void changeWith() {
        Product product = Product.builder()
            .id(1L)
            .name("쥐돌이")
            .maker("냥이월드")
            .price(5000)
            .build();

        product.changeWith(Product
            .builder()
            .id(1L)
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
}
