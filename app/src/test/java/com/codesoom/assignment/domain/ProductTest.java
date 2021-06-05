package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product 엔티티")
class ProductTest {

    @Nested
    @DisplayName("빌더 객체는")
    class Describe_of_builder {

        private Product.ProductBuilder builder = Product.builder();

        @Nested
        @DisplayName("상품 정보가 주어지면")
        class Context_of_product {

            private Long givenId = 1L;
            private String givenName = "쥐돌이";
            private String givenMaker = "냥이월드";
            private Integer givenPrice = 5000;
            private String givenImageUrl = "image.jpg";

            @BeforeEach
            void setup() {
                builder = builder
                        .id(givenId)
                        .name(givenName)
                        .maker(givenMaker)
                        .price(givenPrice)
                        .imageUrl(givenImageUrl);
            }

            @Test
            @DisplayName("상품을 생성할 수 있다")
            void it_can_build_product() {
                Product product = builder.build();

                assertThat(product.getId()).isEqualTo(givenId);
                assertThat(product.getName()).isEqualTo(givenName);
                assertThat(product.getMaker()).isEqualTo(givenMaker);
                assertThat(product.getPrice()).isEqualTo(givenPrice);
                assertThat(product.getImageUrl()).isEqualTo(givenImageUrl);
            }

            @Test
            @DisplayName("문자열로 표현할 수 있다")
            void it_can_present_to_string() {
                assertThat(builder.toString())
                        .isEqualTo(String.format("Product.ProductBuilder(id=%d, name=%s, maker=%s, price=%d, imageUrl=%s)",
                                givenId,
                                givenName,
                                givenMaker,
                                givenPrice,
                                givenImageUrl));
            }
        }

    }

    @Nested
    @DisplayName("change 메소드는")
    class Describe_of_change {

        @Test
        @DisplayName("상품을 변경한다")
        void it_changes_product() {
            Product product = Product.builder()
                    .id(1L)
                    .name("쥐돌이")
                    .maker("냥이월드")
                    .price(5000)
                    .build();

            product.change("쥐순이", "코드숨", 10000,
                    "http://localhost:8080/rat");

            assertThat(product.getName()).isEqualTo("쥐순이");
            assertThat(product.getMaker()).isEqualTo("코드숨");
            assertThat(product.getPrice()).isEqualTo(10000);
            assertThat(product.getImageUrl())
                    .isEqualTo("http://localhost:8080/rat");
        }
    }
}
