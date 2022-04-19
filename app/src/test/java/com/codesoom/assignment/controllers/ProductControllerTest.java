package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName("ProductController 에서")
class ProductControllerTest {
    private static final String PRODUCT_NAME = "상품1";
    private static final String PRODUCT_MAKER = "메이커1";
    private static final Integer PRODUCT_PRICE = 100000;
    private static final String PRODUCT_IMAGE_URL = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F9941A1385B99240D2E";

    private static final Integer UPDATE_PRODUCT_PRICE = 700;
    private static final String UPDATE_PRODUCT_NAME = "상품100";
    private static final String UPDATE_PRODUCT_MAKER = "maker100";
    private static final String UPDATE_PRODUCT_IMAGE_URL = "changedImageUrl";

    @Autowired
    private ProductService productService;
    private ProductController productController;

    @BeforeEach
    void setUp() {
        productController = new ProductController(productService);
    }

    /**
     * 하나의 Product 를 생성해 등록합니다.
     * @return 생성한 Product를 리턴
     */
    private Product createProduct() {
        ProductData productDto = ProductData.builder()
                .name(PRODUCT_NAME)
                .maker(PRODUCT_MAKER)
                .price(PRODUCT_PRICE)
                .build();
        return productService.createProduct(productDto);
    }

    @Nested
    @DisplayName("list 메소드는")
    class Describe_of_list {

        @BeforeEach
        void setUp() {
            createProduct();
        }

        @Nested
        @DisplayName("Product 객체가 없을 경우")
        class Context_with_empty_list {

            @BeforeEach
            void setUp() {
                List<Product> products = productController.list();
                for (Product product : products) {
                    productController.destroy(product.getId());
                }
            }

            @Test
            @DisplayName("빈 리스트를 반환한다")
            void it_return_empty_list() {
                List<Product> products = productController.list();

                assertThat(products).isEmpty();
            }
        }

        @Nested
        @DisplayName("Product 객체가 있을 경우")
        class Context_with_product_list {

            @Test
            @DisplayName("Product 객체가 포함된 배열을 반환한다")
            void it_returns_product_list() {
                List<Product> products = productController.list();
                assertThat(products).isNotEmpty();
            }
        }
    }

    @Nested
    @DisplayName("detail 메소드는")
    class Describe_of_detail {
        private Product product;

        @BeforeEach
        void setUp() {
            product = createProduct();
        }

        @Nested
        @DisplayName("조회할수 있는 제품의 id가 주어지면")
        class Context_with_product {
            private long productId;

            @BeforeEach
            void setUp() {
                productId = product.getId();
            }

            @Test
            @DisplayName("id와 동일한 제품을 반환한다")
            void it_return_product() {
                Product found = productController.detail(productId);

                assertThat(found).isNotNull();
            }
        }

        @Nested
        @DisplayName("조회할수 없는 제품의 id가 주어지면")
        class Context_with_invalid_id {
            private long productId;

            @BeforeEach
            void setUp() {
                productId = product.getId();
                productService.deleteProduct(productId);
            }

            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_throw_productNotFoundException() {
                assertThatThrownBy(() -> productController.detail(productId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("create 메소드는")
    class Describe_of_create {

        @Nested
        @DisplayName("생성할 수 있는 객체가 주어지면")
        class Context_with_created {
            private ProductData productData;

            @BeforeEach
            void setUp() {
                productData = ProductData
                        .builder()
                        .name(PRODUCT_NAME)
                        .maker(PRODUCT_MAKER)
                        .price(PRODUCT_PRICE)
                        .imageUrl(PRODUCT_IMAGE_URL)
                        .build();
            }

            @Test
            @DisplayName("제품을 생성하고, 생성된 제품을 반환한다")
            void it_return_created_product() {
                Product product = productController.create(productData);

                assertThat(product).isNotNull();
                assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE);
                assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
            }
        }
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_of_update {
        private Product product;

        @BeforeEach
        void setUp() {
            product = createProduct();
        }

        @Nested
        @DisplayName("업데이트할 수 있는 제품의 id와 업데이트할 정보가 주어지면")
        class Context_with_valid_id {
            private Long productId;
            private final ProductData productDto = ProductData
                    .builder()
                    .name(UPDATE_PRODUCT_NAME)
                    .maker(UPDATE_PRODUCT_MAKER)
                    .price(UPDATE_PRODUCT_PRICE)
                    .imageUrl(UPDATE_PRODUCT_IMAGE_URL)
                    .build();

            @BeforeEach
            void setUp() {
                productId = product.getId();
            }

            @Test
            @DisplayName("제품을 업데이트하고, 업데이트한 제품을 반환한다")
            void it_return_updated_product() {
                Product updatedProduct = productController.update(productId, productDto);

                assertThat(updatedProduct).isNotNull();
                assertThat(updatedProduct.getPrice()).isEqualTo(UPDATE_PRODUCT_PRICE);
                assertThat(updatedProduct.getName()).isEqualTo(UPDATE_PRODUCT_NAME);
            }
        }

        @Nested
        @DisplayName("업데이트할 수 없는 제품의 id가 주어지면")
        class Context_with_invalid_id {
            private Long productId;
            private final ProductData productDto = ProductData
                    .builder()
                    .name(PRODUCT_NAME)
                    .maker(PRODUCT_MAKER)
                    .price(PRODUCT_PRICE)
                    .imageUrl(PRODUCT_IMAGE_URL)
                    .build();

            @BeforeEach
            void setUp() {
                productId = product.getId();
                productService.deleteProduct(productId);
            }

            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_throw_productNotFoundException() {
                assertThatThrownBy(() -> productController.update(productId, productDto))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("destroy 메소드는")
    class Describe_of_delete {
        private Product product;

        @BeforeEach
        void setUp() {
            product = createProduct();
        }

        @Nested
        @DisplayName("삭제할 수 있는 제품의 id가 주어지면")
        class Context_with_valid_id {
            private Long productId;

            @BeforeEach
            void setUp() {
                productId = product.getId();
            }

            @Test
            @DisplayName("해당 제품을 삭제한다")
            void it_return_void_with_delete() {
                productController.destroy(productId);

                assertThatThrownBy(() -> productService.getProduct(productId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("삭제할 수 없는 제품의 id가 주어지면")
        class Context_with_invalid_id {
            private Long productId;

            @BeforeEach
            void setUp() {
                productId = product.getId();
                productService.deleteProduct(productId);
            }

            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_throw_productNotFoundException() {
                assertThatThrownBy(() -> productController.destroy(productId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
