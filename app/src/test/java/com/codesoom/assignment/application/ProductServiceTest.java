package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
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

class ProductServiceTest {
    private ProductService service;

    private ProductRepository repository = mock(ProductRepository.class);

    private static final String  PRODUCT_NAME = "춘식이 고구마 장난감";
    private static final String  MAKER        = "카카오";
    private static final Integer PRICE        = 10000;
    private static final String  IMAGE_URL    = "http://localhost:8080/choonsik";

    @BeforeEach
    void setUp() {
        service = new ProductService(repository);

        Product product = Product.builder()
                                    .id(1L)
                                    .name(PRODUCT_NAME)
                                    .maker(MAKER)
                                    .price(PRICE)
                                    .imageUrl(IMAGE_URL)
                                .build();

        //service가 아니라 repository임을 주의!
        given(repository.findAll()).willReturn(List.of(product));
        given(repository.findById(1L)).willReturn(Optional.of(product));
        given(repository.findById(1000L)).willThrow(ProductNotFoundException.class);
    }

    @Nested
    @DisplayName("createProduct 메소드는")
    class Describe_createProduct {
        @Nested
        @DisplayName("id에 해당하는 product가 존재하지 않으면")
        class Context_without_ExistedProduct {
            @Test
            @DisplayName("새로운 product를 만들어 리포지토리에 저장한다")
            void it_save_product() {
                ProductData product = ProductData.builder()
                                                    .id(1L)
                                                    .name(PRODUCT_NAME)
                                                    .maker(MAKER)
                                                    .price(PRICE)
                                                    .imageUrl(IMAGE_URL)
                                                    .build();
                service.createProduct(product);

                //TODO ProductData -> Product
//                verify(repository).save(product);

                assertThat(service.getProduct(1L)).isNotNull();
                assertThat(service.getProduct(1L).getName())
                        .isEqualTo(PRODUCT_NAME);
            }
        }

        @Nested
        @DisplayName("id에 해당하는 product가 이미 기존재한다면")
        class Context_with_ExistedProduct {
            @Test
            @DisplayName("id에 해당하는 product를 업데이트한다")
            void it_update_product() {
                ProductData product = ProductData.builder()
                                                    .id(1L)
                                                    .name(PRODUCT_NAME + "!!!")
                                                    .maker(MAKER)
                                                    .price(PRICE)
                                                    .imageUrl(IMAGE_URL)
                                                    .build();

                Product updatedProduct = service.updateProduct(1L, product);

                //TODO ProductData -> Product
//                verify(repository).save(product);

                assertThat(updatedProduct.getName())
                        .isEqualTo(PRODUCT_NAME + "!!!");
            }
        }
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {
        @Test
        @DisplayName("모든 Product 리스트를 리턴한다")
        void it_return_products() {
            List<Product> products = service.getProducts();

            assertThat(products.size()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_getProduct {
        @Nested
        @DisplayName("id가 존재할 때")
        class Context_with_validId {
            @Test
            @DisplayName("해당하는 id의 Product를 리턴한다")
            void it_return_product() {
                Product product = service.getProduct(1L);

                verify(repository).findById(1L);

                assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
                assertThat(product.getMaker()).isEqualTo(MAKER);
                assertThat(product.getPrice()).isEqualTo(PRICE);
                assertThat(product.getImageUrl()).isEqualTo(IMAGE_URL);
            }
        }

        @Nested
        @DisplayName("id가 존재하지 않을 때")
        class Context_with_invalidId {
            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_throw_ProductNotFoundException() {
                assertThatThrownBy(() -> service.getProduct(1000L))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(repository).findById(1000L);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class Describe_deleteProduct {
        @Test
        @DisplayName("id에 해당하는 product를 삭제한다")
        void it_delete_product() {
            service.deleteProduct(1L);

            verify(repository).delete(any(Product.class));
        }
    }
}
