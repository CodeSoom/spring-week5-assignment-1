package com.codesoom.assignment.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    private final Long DEFAULT_ID = 1L;
    private final String NAME = "쥐인형";
    private final String MAKER = "냥이월드";
    private final Integer PRICE = 9900;
    private final String IMAGE_URL = "http://localhost:8080/cat";

    @Nested
    @DisplayName("Lombok 사용")
    class contextLombokAnnotation {
        @Test
        @DisplayName("@Builder 테스트")
        void creationWithBuilder() {
            Product.ProductBuilder productBuilder = Product.builder()
                    .id(DEFAULT_ID)
                    .name(NAME)
                    .maker(MAKER)
                    .price(PRICE);

            Product product = productBuilder.build();

            assertThat(productBuilder.toString()).isEqualTo(String.valueOf(productBuilder));

            assertThat(product.getId()).isEqualTo(DEFAULT_ID);
            assertThat(product.getName()).isEqualTo(NAME);
            assertThat(product.getMaker()).isEqualTo(MAKER);
            assertThat(product.getPrice()).isEqualTo(PRICE);
            assertThat(product.getImageUrl()).isNull();
        }

        @Test
        @DisplayName("@AllArgsConstructor 테스트")
        void creationWithAllArgsConstructor() {
            Product product = new Product(DEFAULT_ID, NAME, MAKER, PRICE, IMAGE_URL);

            assertThat(product.getId()).isEqualTo(DEFAULT_ID);
            assertThat(product.getName()).isEqualTo(NAME);
            assertThat(product.getMaker()).isEqualTo(MAKER);
            assertThat(product.getPrice()).isEqualTo(PRICE);
            assertThat(product.getImageUrl()).isEqualTo(IMAGE_URL);
        }

        @Test
        @DisplayName("@NoArgsConstructor 테스트")
        void creationWithNoArgsConstructor() {
            Product product = new Product();

            assertThat(product.getId()).isNull();
            assertThat(product.getName()).isBlank();
            assertThat(product.getMaker()).isBlank();
            assertThat(product.getPrice()).isNull();
            assertThat(product.getImageUrl()).isBlank();
        }
    }

    @Test
    void changeWith() {
        Product product = Product.builder()
                .id(1L)
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .build();

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

}
