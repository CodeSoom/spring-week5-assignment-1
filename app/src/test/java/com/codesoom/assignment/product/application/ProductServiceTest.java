package com.codesoom.assignment.product.application;

import com.codesoom.assignment.exceptions.product.ProductNotFoundException;
import com.codesoom.assignment.product.adapter.in.web.dto.ProductRequest;
import com.codesoom.assignment.product.application.port.out.ProductRepository;
import com.codesoom.assignment.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
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

    @Test
    void getProductsWithNoProduct() {
        given(productRepository.findAll()).willReturn(List.of());

        assertThat(productService.getProducts()).isEmpty();
    }

    @Test
    void getProducts() {
        List<Product> products = productService.getProducts();

        assertThat(products).isNotEmpty();

        Product product = products.get(0);

        assertThat(product.getName()).isEqualTo("쥐돌이");
    }

    @Test
    void getProductWithExsitedId() {
        Product product = productService.getProduct(1L);

        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("쥐돌이");
    }

    @Test
    void getProductWithNotExsitedId() {
        assertThatThrownBy(() -> productService.getProduct(1000L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void createProduct() {
        ProductRequest productRequest = ProductRequest.builder()
                .name("쥐돌이")
                .maker("냥이월드")
                .price(5000)
                .build();

        Product product = productService.createProduct(productRequest);

        verify(productRepository).save(any(Product.class));

        assertThat(product.getId()).isEqualTo(2L);
        assertThat(product.getName()).isEqualTo("쥐돌이");
        assertThat(product.getMaker()).isEqualTo("냥이월드");
    }

    @Test
    void updateProductWithExistedId() {
        ProductRequest productRequest = ProductRequest.builder()
                .name("쥐순이")
                .maker("냥이월드")
                .price(5000)
                .build();

        Product product = productService.updateProduct(1L, productRequest);

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("쥐순이");
    }

    @Test
    void updateProductWithNotExistedId() {
        ProductRequest productRequest = ProductRequest.builder()
                .name("쥐순이")
                .maker("냥이월드")
                .price(5000)
                .build();

        assertThatThrownBy(() -> productService.updateProduct(1000L, productRequest))
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
