package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("ProductService 인터페이스의")
public class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ToyService(productRepository);
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {
        @Nested
        @DisplayName("상품 정보가 주어지면")
        class Context_with_product {
            Product product = new Product("먼지털이", "코드숨", 2200000, "picture.com");
            ProductData productData = new ProductData("먼지털이", "코드숨", 2200000, "picture.com");
            Product expect = new Product(1L, "먼지털이", "코드숨", 2200000, "picture.com");

            @BeforeEach
            void prepare() {
                given(productRepository.save(product))
                        .willReturn(expect);
            }

            @Test
            @DisplayName("상품을 리턴한다")
            void It_returns_product() {
                assertThat(productService.create(productData)).isEqualTo(expect);

                verify(productRepository).save(any(Product.class));
            }
        }
    }
}
