package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("ProductService 테스트")
class ProductServiceTest {
    private ProductService productService;
    private ProductRepository productRepository;

    private final String SETUP_PRODUCT_NAME = "setup_product_name";
    private final String SETUP_PRODUCT_MAKER = "setup_product_maker";
    private final Integer SETUP_PRODUCT_PRICE = 1000;
    private final String SETUP_PRODUCT_IMAGEURL = "setup_product_image";

    private final String CREATED_PRODUCT_NAME = "created_product_name";
    private final String CREATED_PRODUCT_MAKER = "created_product_maker";
    private final Integer CREATED_PRODUCT_PRICE = 2000;
    private final String CREATED_PRODUCT_IMAGEURL = "created_product_image";

    private final String UPDATED_PRODUCT_NAME = "updated_product_name";
    private final String UPDATED_PRODUCT_MAKER = "updated_product_maker";
    private final Integer UPDATED_PRODUCT_PRICE = 3000;
    private final String UPDATED_PRODUCT_IMAGEURL = "updated_product_image";

    private final Long EXISTED_ID = 1L;
    private final Long CREATED_ID = 2L;
    private final Long NOT_EXISTED_ID = 100L;

    private List<Product> products;
    private Product setupProduct;
    private Product createdProduct;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        setupProduct = Product.builder()
                .id(EXISTED_ID)
                .name(SETUP_PRODUCT_NAME)
                .maker(SETUP_PRODUCT_MAKER)
                .price(SETUP_PRODUCT_PRICE)
                .imageUrl(SETUP_PRODUCT_IMAGEURL)
                .build();

        createdProduct = Product.builder()
                .id(CREATED_ID)
                .name(CREATED_PRODUCT_NAME)
                .maker(CREATED_PRODUCT_MAKER)
                .price(CREATED_PRODUCT_PRICE)
                .imageUrl(CREATED_PRODUCT_IMAGEURL)
                .build();

        products = Arrays.asList(setupProduct, createdProduct);
    }

    @Test
    void getProductsWithNoProduct() {
        given(productRepository.findAll()).willReturn(List.of());

        assertThat(productService.findAll()).isEmpty();
    }
    @Nested
    @DisplayName("getProducts 메서드는")
    class Describe_getProducts {
        @Nested
        @DisplayName("만약 상품 목록이 존재한다면")
        class isProductExisted{
            @Test
            @DisplayName("저장되어 있는 상 목록을 리턴한다")
            void itReturnsListOfProducts() {
                given(productService.findAll()).willReturn(products);

                List<Product> lists = productService.findAll();
                assertThat(lists).isEqualTo(products);
                assertThat(lists).containsExactly(setupProduct,createdProduct);

                verify(productRepository).findAll();
            }
        }
        @Nested
        @DisplayName("만약 상품 목록이 존재하지 않는다면")
        class isProductNotExisted{
            @Test
            @DisplayName("비어 있는 목록을 리턴한다")
            void itReturnsEmptyfProducts() {
                given(productService.findAll()).willReturn(List.of());

                List<Product> lists = productService.findAll();
                assertThat(lists).isEmpty();

                verify(productRepository).findAll();
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메서드는")
    class Describe_getProduct {
        @Nested
        @DisplayName("만약 저장되어 있는 상의 아이디가 주어진다면")
        class Context_WithExistedId {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감을 리턴한다")
            void itReturnsWithExistedProduct() {
                given(productRepository.findById(givenExistedId)).willReturn(Optional.of(setupProduct));

                Product product = productService.getProduct(givenExistedId);
                assertThat(product.getId()).isEqualTo(givenExistedId);

                verify(productRepository).findById(givenExistedId);
            }
        }
        @Nested
        @DisplayName("만약 저장되어 있지 않는 고양이 장난감의 아이디가 주어진다면")
        class Context_WithNotExistedId {
            private final Long givenNotExistedId = NOT_EXISTED_ID;

            @Test
            @DisplayName("고양이 장난감을 찾을 수 없다는 예외를 던진다")
            void itThrowsProductNotFoundException() {
                assertThatThrownBy(() -> productService.getProduct(givenNotExistedId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Test
    void createProduct() {
        ProductData productData = ProductData.builder()
                .name("쥐돌이")
                .maker("냥이월드")
                .price(5000)
                .build();

        Product product = productService.createProduct(productData);

        verify(productRepository).save(any(Product.class));

        assertThat(product.getId()).isEqualTo(2L);
        assertThat(product.getName()).isEqualTo("쥐돌이");
        assertThat(product.getMaker()).isEqualTo("냥이월드");
    }

    @Test
    void updateProductWithExistedId() {
        ProductData productData = ProductData.builder()
                .name("쥐순이")
                .maker("냥이월드")
                .price(5000)
                .build();

        Product product = productService.updateProduct(1L, productData);

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("쥐순이");
    }

    @Test
    void updateProductWithNotExistedId() {
        ProductData productData = ProductData.builder()
                .name("쥐순이")
                .maker("냥이월드")
                .price(5000)
                .build();

        assertThatThrownBy(() -> productService.updateProduct(1000L, productData))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void deleteProductWithExistedId() {
        productService.deleteProduct(1L);

        verify(productRepository).delete(any(Product.class));
    }

    @Test
    void deleteProductWithNotExistedId() {
        assertThatThrownBy(() -> productService.deleteProduct(1000L))
                .isInstanceOf(ProductNotFoundException.class);
    }
}
