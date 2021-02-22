package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    private ProductService productService;

    private ProductRepository productRepository = mock(ProductRepository.class);

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);

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

    @DisplayName("서비스에 등록된 상품이 아무것도 없을 때, 비어 있는 상품 리스트를 반환합니다.")
    @Test
    void getProductsWithNoProduct() {
        given(productRepository.findAll()).willReturn(List.of());

        assertThat(productService.getProducts()).isEmpty();
    }

    @DisplayName("서비스에 등록된 상품이 있을 때, 비어 있지 않은 상품 리스트 반환합니다.")
    @Test
    void getProducts() {
        List<Product> products = productService.getProducts();

        assertThat(products).isNotEmpty();

        Product product = products.get(0);

        assertThat(product.getName()).isEqualTo("쥐돌이");
    }

    @DisplayName("서비스에 존재하는 상품을 요청 했을 때, 해당하는 상품을 반환합니다.")
    @Test
    void getProductWithExsitedId() {
        Product product = productService.getProduct(1L);

        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("쥐돌이");
    }

    @DisplayName("서비스에 존재하지 않는 상품을 요청 했을 때, 예외를 호출합니다.")
    @Test
    void getProductWithNotExsitedId() {
        assertThatThrownBy(() -> productService.getProduct(1000L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("서비스에 상품 생성을 요청했을 때, 특정한 상품이 생성됩니다.")
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

    @DisplayName("서비스에 존재하는 상품에 대하여 수정을 요청했을 때, 수정된 상품을 반환합니다.")
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

    @DisplayName("서비스에 존재하지 않는 상품에 대하여 수정을 요청하였을 때, 예외를 호출합니다.")
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

    @DisplayName("서비스에 존재하는 상품에 대하여 삭제 요청을 하였을 때, 상품을 삭제합니다.")
    @Test
    void deleteProductWithExistedId() {
        productService.deleteProduct(1L);

        verify(productRepository).delete(any(Product.class));
    }

    @DisplayName("서비스에 존재하지 않는 상품에 대하여 삭제 요청을 하였을 때, 예외를 호출합니다.")
    @Test
    void deleteProductWithNotExistedId() {
        assertThatThrownBy(() -> productService.deleteProduct(1000L))
                .isInstanceOf(ProductNotFoundException.class);
    }
}
