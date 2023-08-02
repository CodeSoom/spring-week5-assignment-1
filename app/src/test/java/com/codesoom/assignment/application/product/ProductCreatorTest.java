package com.codesoom.assignment.application.product;

import com.codesoom.assignment.application.JpaTest;
import com.codesoom.assignment.domain.product.Product;
import com.codesoom.assignment.dto.product.ProductData;
import org.junit.jupiter.api.*;
import org.assertj.core.api.Assertions;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("ProductCreator 클래스")
class ProductCreatorTest extends JpaTest {

    private final String TEST_NAME = "testName";
    private final String TEST_MAKER = "testMaker";
    private final int TEST_PRICE = 1000;
    private final String TEST_IMAGE_URL = "testImageUrl";

    private ProductData createProductData() {
        return ProductData.builder()
                .name(TEST_NAME)
                .price(TEST_PRICE)
                .maker(TEST_MAKER)
                .imageUrl(TEST_IMAGE_URL)
                .build();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class createProduct_메서드는 {
        private ProductCreator productCreator;
        private ProductData productData;
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품정보가_주어지면 {
            @BeforeEach
            void setUp() {
                productCreator = new ProductCreator(getProductRepository());
                productData = createProductData();
            }

            @Test
            @DisplayName("해당상품정보를_저장후_해당상품정보를_리턴한다")
            void 해당상품정보를_저장후_해당상품정보를_리턴한다() {
                Product product = productCreator.createProduct(productData);
                Assertions.assertThat(product.getName()).isEqualTo(TEST_NAME);
                Assertions.assertThat(product.getMaker()).isEqualTo(TEST_MAKER);
                Assertions.assertThat(product.getPrice()).isEqualTo(TEST_PRICE);
                Assertions.assertThat(product.getImageUrl()).isEqualTo(TEST_IMAGE_URL);
            }
        }
    }
}
