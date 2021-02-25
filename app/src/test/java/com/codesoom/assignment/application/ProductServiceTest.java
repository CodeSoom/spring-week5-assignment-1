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

@DisplayName("ProductService 클래")
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
    @DisplayName("제품이 없을 때 제품목록을 get하면 그 목록은 비어있다.")
    void getProductsWithNoProduct() {
        given(productRepository.findAll()).willReturn(List.of());

        assertThat(productService.getProducts()).isEmpty();
    }

    @Test
    @DisplayName("제품이 있을 때 제품 목록을 get하면 제품 리스트를 응답한다.")
    void getProducts() {
        List<Product> products = productService.getProducts();

        assertThat(products).isNotEmpty();

        Product product = products.get(0);

        assertThat(product.getName()).isEqualTo("쥐돌이");
    }

    @Test
    @DisplayName("있는 ID로 제품을 get하면 그 제품을 응답한다.")
    void getProductWithExistingId() {
        Product product = productService.getProduct(1L);

        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("쥐돌이");
    }

    @Test
    @DisplayName("없는 ID로 제품을 get하면 Exception을 응답한다.")
    void getProductWithNotExistingId() {
        assertThatThrownBy(() -> productService.getProduct(1000L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("외부에서 들어오는 ProductData로 제품을 생성한다.")
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
    @DisplayName("있는 ID로 제품을 update한다.")
    void updateProductWithExistingId() {
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
    @DisplayName("없는 ID로 제품을 업데이트하면 Exception을 응답한다.")
    void updateProductWithNotExistingId() {
        ProductData productData = ProductData.builder()
                .name("쥐순이")
                .maker("냥이월드")
                .price(5000)
                .build();

        assertThatThrownBy(() -> productService.updateProduct(1000L, productData))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("있는 ID로 제품을 delete한다.")
    void deleteProductWithExistingId() {
        productService.deleteProduct(1L);

        verify(productRepository).delete(any(Product.class));
    }

    @Test
    @DisplayName("없는 ID로 제품을 delete하면 Exception을 응답한다.")
    void deleteProductWithNotExistingId() {
        assertThatThrownBy(() -> productService.deleteProduct(1000L))
                .isInstanceOf(ProductNotFoundException.class);
    }
}
