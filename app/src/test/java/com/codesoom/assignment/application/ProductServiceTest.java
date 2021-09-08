package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.google.common.collect.Lists;

import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.util.List;

import static com.codesoom.assignment.domain.ProductConstants.ID;
import static com.codesoom.assignment.domain.ProductConstants.NAME;
import static com.codesoom.assignment.domain.ProductConstants.MAKER;
import static com.codesoom.assignment.domain.ProductConstants.PRICE;
import static com.codesoom.assignment.domain.ProductConstants.IMAGE_URL;
import static com.codesoom.assignment.domain.ProductConstants.PRODUCT;
import static com.codesoom.assignment.domain.ProductConstants.PRODUCT_LIST;
import static com.codesoom.assignment.domain.ProductConstants.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Nested
@DisplayName("ProductService 클래스")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Nested
    @DisplayName("getProducts 메서드는")
    class Describe_getProducts {
        ListAssert<Product> subject() {
            return assertThat(productService.getProducts());
        }
        OngoingStubbing<List<Product>> mockSubject() {
            return when(productRepository.findAll());
        }
        @AfterEach
        void afterEach() {
            verify(productRepository).findAll();
        }
        @Nested
        @DisplayName("저장된 Product가 있다면")
        class Context_product_exist {
            @BeforeEach
            void beforeEach() {
                mockSubject()
                    .thenReturn(PRODUCT_LIST);
            }
            @Test
            @DisplayName("Product 목록을 리턴한다.")
            void it_returns_a_product_list() {
                subject()
                    .isNotEmpty();
            }
        }
        @Nested
        @DisplayName("저장된 Product가 없다면")
        class Context_product_empty {
            @BeforeEach
            void beforeEach() {
                mockSubject()
                    .thenReturn(EMPTY_LIST);
            }
            @Test
            @DisplayName("빈 목록을 리턴한다.")
            void it_returns_a_empty_list() {
                subject()
                    .isEmpty();
            }
        }
    }
}
