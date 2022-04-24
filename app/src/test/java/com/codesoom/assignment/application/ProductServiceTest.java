package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("ProductService 에서")
class ProductServiceTest {
    private static final Long PRODUCT_ID = 1L;
    private static final String PRODUCT_NAME = "상품1";
    private static final String PRODUCT_MAKER = "메이커1";
    private static final Integer PRODUCT_PRICE = 100000;
    private static final String PRODUCT_IMAGE_URL = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F9941A1385B99240D2E";

    private static final String UPDATE_PRODUCT_NAME = "상품1000";
    private static final Integer UPDATE_PRODUCT_PRICE = 100;

    private ProductService productService;

    private final ProductRepository productRepository = mock(ProductRepository.class);

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
    }

    /**
     * 하나의 Product 를 생성해 등록합니다.
     * @return 생성한 Product를 리턴
     */
    private Product createProduct() {
        return Product.builder()
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .maker(PRODUCT_MAKER)
                .price(PRODUCT_PRICE)
                .imageUrl(PRODUCT_IMAGE_URL)
                .build();
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_of_readAll_products {

        @Nested
        @DisplayName("조회할 수 있는 제품이 없을 경우")
        class Context_without_product {

            @Test
            @DisplayName("빈 배열을 리턴한다")
            void it_returns_emtpy_list() {
                List<Product> products = productService.getProducts();

                assertThat(products).isEmpty();
            }
        }

        @Nested
        @DisplayName("조회할 수 있는 제품이 있을 경우")
        class Context_with_product {

            @BeforeEach
            void setUp() {
                Product product = createProduct();

                given(productRepository.findAll()).willReturn(List.of(product));
            }

            @Test
            @DisplayName("Product 객체가 포함된 배열을 리턴한다")
            void it_returns_list_of_product() {
                List<Product> products = productService.getProducts();

                assertThat(products).isNotEmpty();
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_of_read_product {
        final Product product = createProduct();
        final Long productId = product.getId();

        @Nested
        @DisplayName("찾을수 있는 제품의 id가 주어지면")
        class Context_with_valid_id {

            @BeforeEach
            void setUp() {
                given(productRepository.findById(productId)).willReturn(Optional.of(product));
            }

            @Test
            @DisplayName("id와 동일한 제품을 반환한다")
            void it_return_product() {
                Product product = productService.getProduct(productId);

                assertThat(product).isNotNull();
                assertThat(product.getId()).isEqualTo(productId);
            }
        }

        @Nested
        @DisplayName("찾을수 없는 제품의 id가 주어지면")
        class Context_with_invalid_id {

            @BeforeEach
            void setUp() {
                given(productRepository.findById(productId)).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_throw_productNotFoundException() {
                assertThatThrownBy(() -> productService.getProduct(productId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("createProduct 메소드에서")
    class Describe_of_create_product {

        @Nested
        @DisplayName("생성할 수 있는 제품의 데이터가 주어지면")
        class Context_with_create_product {
            private final ProductData productData = ProductData
                    .builder()
                    .name(PRODUCT_NAME)
                    .maker(PRODUCT_MAKER)
                    .price(PRODUCT_PRICE)
                    .imageUrl(PRODUCT_IMAGE_URL)
                    .build();

            @BeforeEach
            void setUp() {
                given(productRepository.save(any(Product.class))).will(invocation -> {
                    Product source = invocation.getArgument(0);
                    return Product.builder()
                            .id(PRODUCT_ID + 1L)
                            .name(source.getName())
                            .maker(source.getMaker())
                            .price(source.getPrice())
                            .build();
                });
            }


            @Test
            @DisplayName("제품을 생성하고, 생성된 제품을 반환한다")
            void it_return_created_product() {
                Product product = productService.createProduct(productData);

                when(productService.getProduct(product.getId())).thenReturn(product);

                Product found = productService.getProduct(product.getId());

                assertThat(product).isNotNull();
                assertThat(product).isEqualTo(found);
                assertThat(product.getId()).isEqualTo(PRODUCT_ID + 1L);
                assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE);
                assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_of_update_product {
        private long productId;

        @BeforeEach
        void setUp() {
            Product product = createProduct();
            productId = product.getId();

            given(productRepository.findById(productId)).willReturn(Optional.of(product));
        }

        @Nested
        @DisplayName("업데이트할 수 있는 제품의 id와 데이터가 주어지면")
        class Context_with_valid_id {
            private final ProductData productData = ProductData
                    .builder()
                    .name(UPDATE_PRODUCT_NAME)
                    .maker(PRODUCT_MAKER)
                    .price(UPDATE_PRODUCT_PRICE)
                    .imageUrl(PRODUCT_IMAGE_URL)
                    .build();

            @Test
            @DisplayName("제품을 업데이트하고, 업데이트한 제품을 반환한다")
            void it_return_updated_product() {
                Product product = productService.updateProduct(productId, productData);

                assertThat(product).isNotNull();
                assertThat(product.getPrice()).isEqualTo(UPDATE_PRODUCT_PRICE);
                assertThat(product.getName()).isEqualTo(UPDATE_PRODUCT_NAME);
            }
        }

        @Nested
        @DisplayName("업데이트 할 수 없는 제품의 id가 주어지면")
        class Context_with_invalid_id {
            private final ProductData productData = ProductData
                    .builder()
                    .name(PRODUCT_NAME)
                    .maker(PRODUCT_MAKER)
                    .price(PRODUCT_PRICE)
                    .imageUrl(PRODUCT_IMAGE_URL)
                    .build();

            @BeforeEach
            void setUp() {
                given(productRepository.findById(productId)).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_throw_productNotFoundException() {
                assertThatThrownBy(() -> productService.updateProduct(productId, productData))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class Describe_of_delete_product {
        private Product product;

        @BeforeEach
        void setUp() {
            product = createProduct();
        }

        @Nested
        @DisplayName("삭제할 수 있는 제품의 id가 주어지면")
        class Context_with_valid_id {

            @BeforeEach
            void setUp() {
                given(productRepository.findById(product.getId()))
                        .willReturn(Optional.of(product));
            }

            @Test
            @DisplayName("제품을 삭제한 후, 삭제한 제품을 반환한다")
            void it_return_void() {
                Product deleteProduct = productService.deleteProduct(product.getId());

                when(productService.getProduct(deleteProduct.getId()))
                        .thenThrow(ProductNotFoundException.class);

                assertThatThrownBy(() -> productService.getProduct(deleteProduct.getId()))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("Id 에 맞는 Product가 없을 경우")
        class Context_with_invalid_id {
            private Long productId;

            @BeforeEach
            void setUp() {
                productId = product.getId();
            }

            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_throw_productNotFoundException() {
                assertThatThrownBy(() -> productService.deleteProduct(productId + 1L))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
