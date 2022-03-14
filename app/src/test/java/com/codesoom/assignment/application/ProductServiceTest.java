package com.codesoom.assignment.application;

import com.codesoom.assignment.errors.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
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
import static org.mockito.Mockito.verify;

@DisplayName("ProductService 클래스")
class ProductServiceTest {
    private ProductService productService;

    private ProductRepository productRepository = mock(ProductRepository.class);

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        productService = new ProductService(mapper, productRepository);

        Product product = Product.builder()
                .id(1L)
                .name("쥐돌이")
                .maker("냥이월드")
                .price(5000)
                .build();

        given(productRepository.findAll()).willReturn(List.of(product));

        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        given(productRepository.save(any(Product.class))).will(invocation -> {
            Product source = invocation.getArgument(0);
            return Product.builder()
                    .id(2L)
                    .name(source.getName())
                    .maker(source.getMaker())
                    .price(source.getPrice())
                    .build();
        });
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {
        @Nested
        @DisplayName("등록된 Product가 있다면")
        class Context_has_product {
            @Test
            @DisplayName("전체 리스트를 리턴한다.")
            void it_return_list() {

                List<Product> products = productService.getProducts();

                Product product = products.get(0);

                assertThat(product.getName()).isEqualTo("쥐돌이");
            }
        }

        @Nested
        @DisplayName("등록된 Product가 없다면")
        class Context_hasNot_product {
            @Test
            @DisplayName("빈 리스트를 리턴한다.")
            void it_return_list() {
                given(productRepository.findAll()).willReturn(List.of());

                assertThat(productService.getProducts()).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_getProduct {
        @Nested
        @DisplayName("등록된 id 값이 주어졌을때")
        class Context_when_product_is_exist {
            @Test
            @DisplayName("등록된 Product 정보를 리턴한다.")
            void it_return_product() {
                Product product = productService.getProduct(1L);

                assertThat(product.getName()).isEqualTo("쥐돌이");
            }
        }

        @Nested
        @DisplayName("id에 해당하는 Product가 존재하지 않으면")
        class Context_when_product_isnot_exist {
            @Test
            @DisplayName("Product를 찾을 수 없다는 예외를 던진다.")
            void it_throw_ProductNotFoundException() {
                assertThatThrownBy(() -> productService.getProduct(1000L))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("createProduct 메소드는")
    class Describe_create {
        @Nested
        @DisplayName("Product를 입력받으면")
        class Context_when_product {
            @Test
            @DisplayName("저장하고 Product를 리턴한다.")
            void it_return_product() {
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
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_update {
        ProductData productData;

        @BeforeEach
        void setUp() {
            productData = ProductData.builder()
                    .name("쥐순이")
                    .maker("냥이월드")
                    .price(5000)
                    .build();
        }

        @Nested
        @DisplayName("등록된 id가 주어진다면")
        class Context_when_product_is_exist {
            @Test
            @DisplayName("id에 해당하는 Product 정보를 수정하고 리턴한다.")
            void it_fix_product_return() {
                Product product = productService.updateProduct(1L, productData);

                assertThat(product.getId()).isEqualTo(1L);
                assertThat(product.getName()).isEqualTo(productData.getName());
            }
        }

        @Nested
        @DisplayName("등록되지않은 id가 주어진다면")
        class Context_when_product_isnot_exist {
            @Test
            @DisplayName("id에 해당하는 Product를 찾을 수 없어 수정할 수 없다고 예외를 던진다.")
            void it_throw_ProductNotFoundException() {
                assertThatThrownBy(() -> productService.updateProduct(1000L, productData))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class Describe_delete {
        @Nested
        @DisplayName("등록된 id가 주어진다면")
        class Context_when_product_is_exist {
            @Test
            @DisplayName("id에 해당하는 Product가 삭제되고 남은 상품 리스트를 리턴한다.")
            void it_return_products() {
                productService.deleteProduct(1L);

                verify(productRepository).delete(any(Product.class));
            }
        }

        @Nested
        @DisplayName("등록되지않은 id가 주어진다면")
        class Context_when_product_isnot_exist {
            @Test
            @DisplayName("id에 해당하는 Product를 찾을 수 없어 삭제할 수 없다고 예외를 던진다.")
            void it_return_products() {
                assertThatThrownBy(() -> productService.deleteProduct(1000L))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
