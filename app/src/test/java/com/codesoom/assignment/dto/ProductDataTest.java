package com.codesoom.assignment.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductData 클래스")
class ProductDataTest {

    private ProductData.ProductDataBuilder builder;
    private ProductData productData;

    private Long defaultId = 1L;
    private String defaultName = "name";
    private String defaultMaker = "maker";
    private Integer defaultPrice = 1000;
    private String defaultImageUrl = "image.jpg";

    @BeforeEach
    void setFixture() {
        builder = ProductData.builder()
                .id(defaultId)
                .name(defaultName)
                .maker(defaultMaker)
                .price(defaultPrice)
                .imageUrl(defaultImageUrl);
        productData = builder.build();
    }

    @Nested
    @DisplayName("getId 메소드는")
    class Describe_of_getId {

        @Test
        @DisplayName("id를 반환한다")
        void it_returns_id() {
            assertThat(productData.getId()).isEqualTo(defaultId);
        }
    }

    @Nested
    @DisplayName("setId 메소드는")
    class Describe_of_setId {

        private Long newId = defaultId + 1;

        @Test
        @DisplayName("id를 갱신한다")
        void it_set_id() {
            productData.setId(newId);

            assertThat(productData.getId()).isEqualTo(newId);
        }
    }

    @Nested
    @DisplayName("getImageUrl 메소드는")
    class Describe_of_getImageUrl {

        @Test
        @DisplayName("imageUrl을 반환한다")
        void it_returns_id() {
            assertThat(productData.getImageUrl()).isEqualTo(defaultImageUrl);
        }
    }

    @Nested
    @DisplayName("setImageUrl 메소드는")
    class Describe_of_setImageUrl {

        private String newImageUrl = "newImage.jpg";

        @Test
        @DisplayName("imageUrl을 갱신한다")
        void it_set_imageUrl() {
            productData.setImageUrl(newImageUrl);

            assertThat(productData.getImageUrl()).isEqualTo(newImageUrl);
        }
    }

    @Nested
    @DisplayName("builder의")
    class Describe_of_builder {

        @Nested
        @DisplayName("toString 메소드는")
        class Describe_of_toString {

            @Test
            @DisplayName("상품 데이터를 문자열로 표현할 수 있다")
            void it_can_present_data_as_string() {
                assertThat(builder.toString())
                        .isEqualTo(
                                String.format("ProductData.ProductDataBuilder(id=%d, name=%s, maker=%s, price=%d, imageUrl=%s)",
                                defaultId,
                                defaultName,
                                defaultMaker,
                                defaultPrice,
                                defaultImageUrl));
            }
        }
    }
}
