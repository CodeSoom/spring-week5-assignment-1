package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {

    private static final String NAME = "뱀 장난감";
    private static final String MAKER = "야옹이네 장난감";
    private static final Integer PRICE = 3000;
    private static final String IMAGE = "https://bit.ly/3qzXRME";

    private static final String UPDATE_NAME = "물고기 장난감";
    private static final String UPDATE_MAKER = "애옹이네 장난감";
    private static final Integer UPDATE_PRICE = 5000;
    private static final String UPDATE_IMAGE = "https://bit.ly/2M4YXkw";

    private ProductService productService;

    private ProductRepository productRepository;

    private final Mapper dozerMapper = DozerBeanMapperBuilder.buildDefault();

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);

        productService = new ProductService(dozerMapper, productRepository);

        List<Product> products = new ArrayList<>();

        Product product = Product.builder()
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .image(IMAGE)
                .build();

        given(productRepository.findAll()).willReturn(products);

        given(productRepository.findById(1L))
                .willReturn(Optional.of(product));

        given(productRepository.save(any(Product.class)))
                .will(invocation -> invocation.<Product>getArgument(0));

        given(productRepository.findById(1L))
                .willReturn(Optional.of(product));
    }

    void getProductTest(Product product) {
        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getMaker()).isEqualTo(MAKER);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getImage()).isEqualTo(IMAGE);
    }

    void updateTest(ProductData update) {
        assertThat(update.getName()).isEqualTo(UPDATE_NAME);
        assertThat(update.getMaker()).isEqualTo(UPDATE_MAKER);
        assertThat(update.getPrice()).isEqualTo(UPDATE_PRICE);
        assertThat(update.getImage()).isEqualTo(UPDATE_IMAGE);
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {
        Product product;

        @Nested
        @DisplayName("상품이 존재한다면")
        class Context_with_product {

            @BeforeEach
            void setUp() {
                product = new Product();
            }

            @Test
            @DisplayName("상품 목록을 반환한다")
            void it_returns_list() {
                List<Product> products = productService.getProducts();

                products.add(product);

                verify(productRepository).findAll();

                assertThat(products).hasSize(1);
            }
        }

        @Nested
        @DisplayName("상품이 존재하지 않는다면")
        class Context_without_product {

            @Test
            @DisplayName("빈 목록을 반환한다")
            void it_returns_list() {
                List<Product> products = productService.getProducts();

                assertThat(products).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_getProduct {
        Product product;

        @Nested
        @DisplayName("등록된 상품의 ID가 주어진다면")
        class Context_with_valid_id {

            @Test
            @DisplayName("해당 ID를 갖는 상품을 반환한다")
            void it_returns_product() {
                product = productService.getProduct(1L);

                verify(productRepository).findById(1L);

                getProductTest(product);
            }
        }

        @Nested
        @DisplayName("등록되지 않은 상품의 ID가 주어진다면")
        class Context_with_invalid_id {

            @Test
            @DisplayName("해당 상품을 찾을 수 없다는 예외를 던진다")
            void it_returns_warning_message() {
                assertThatThrownBy(() -> productService.getProduct(100L))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("createProduct 메소드는")
    class Describe_createProduct {
        ProductData productData;

        @Test
        @DisplayName("새로운 상품을 등록한다")
        void it_returns_product() {
            productData = new ProductData();

            productService.createProduct(productData);

            verify(productRepository).save(any(Product.class));
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct {
        ProductData update;

        @Nested
        @DisplayName("등록된 상품의 ID가 주어진다면")
        class Context_with_valid_id_and_product {

            @BeforeEach
            void setUp() {
                update = ProductData.builder()
                        .name(UPDATE_NAME)
                        .maker(UPDATE_MAKER)
                        .price(UPDATE_PRICE)
                        .image(UPDATE_IMAGE)
                        .build();

                Product updatedProduct = productService.createProduct(update);

                given(productRepository.findById(1L))
                        .willReturn(Optional.of(updatedProduct));
            }

            @Test
            @DisplayName("해당 ID를 갖는 상품의 정보를 수정하고 반환한다")
            void it_returns_updated_product() {
                productService.updateProduct(1L, update);

                verify(productRepository).findById(1L);

                updateTest(update);
            }
        }

        @Nested
        @DisplayName("등록되지 않은 상품의 ID가 주어진다면")
        class Context_with_invalid_id {

            @Test
            @DisplayName("수정할 상품을 찾을 수 없다는 예외를 던진다")
            void it_returns_warning_message() {
                assertThatThrownBy(() -> productService.updateProduct(100L, update))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class Describe_deleteProduct {
        Product product;

        @Nested
        @DisplayName("등록된 상품의 ID가 주어진다면")
        class Context_with_valid_id {

            @Test
            @DisplayName("해당 ID를 갖는 장난감을 삭제하고 반환한다")
            void it_returns_deleted_product() {
                product = new Product();

                productService.deleteProduct(1L);

                verify(productRepository).findById(1L);
                verify(productRepository).delete(any(Product.class));

                assertThat(productRepository.findAll()).isNotIn(1L);
            }
        }

        @Nested
        @DisplayName("등록되지 않은 상품의 ID가 주어진다면")
        class Context_without_invalid_id {

            @Test
            @DisplayName("삭제할 상품을 찾을 수 없다는 예외를 던진다")
            void it_returns_warning_message() {
                assertThatThrownBy(() -> productService.deleteProduct(100L))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
