package com.codesoom.assignment.application.product;

import com.codesoom.assignment.application.JpaTest;
import com.codesoom.assignment.domain.product.Product;
import org.junit.jupiter.api.*;

import org.assertj.core.api.Assertions;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("ProductReader 클래스")
class ProductReaderTest extends JpaTest {

    private final String TEST_NAME = "testName";
    private final String TEST_MAKER = "testMaker";
    private final int TEST_PRICE = 1000;
    private final String TEST_IMAGE_URL = "testImageUrl";

    private Product createProduct(){
        return Product.builder()
                .name(TEST_NAME)
                .maker(TEST_MAKER)
                .price(TEST_PRICE)
                .imageUrl(TEST_IMAGE_URL)
                .build();
    }

    private ProductReader productReader;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class getProduct_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품_아이디가_주어지면 {
            private long savedProductId;

            @BeforeEach
            void setUp() {
                savedProductId = getProductRepository().save(createProduct()).getId();
                productReader = new ProductReader(getProductRepository());
            }

            @DisplayName("해당_상품정보를_리턴한다")
            @Test
            void it_returns_product() {
                Product product = productReader.getProduct(savedProductId);
                Assertions.assertThat(product.getName()).isEqualTo(TEST_NAME);
                Assertions.assertThat(product.getMaker()).isEqualTo(TEST_MAKER);
                Assertions.assertThat(product.getPrice()).isEqualTo(TEST_PRICE);
                Assertions.assertThat(product.getImageUrl()).isEqualTo(TEST_IMAGE_URL);
            }
        }
    }
}
