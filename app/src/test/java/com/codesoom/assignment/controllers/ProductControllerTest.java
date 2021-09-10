package com.codesoom.assignment.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static com.codesoom.assignment.constants.ProductConstants.PRODUCT_LIST;

import java.util.Arrays;
import java.util.List;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;

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

@Nested
@ExtendWith(MockitoExtension.class)
@DisplayName("ProductController 클래스")
public class ProductControllerTest {
    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Nested
    @DisplayName("list 메서드는")
    public class Describe_list {
        private List<Product> subject() {
            return productController.list();
        }

        private OngoingStubbing<List<Product>> mockFindAll() {
            return when(productService.getProducts());
        }

        private ProductService verifyFindAll(final int invokeCounts) {
            return verify(productService, times(invokeCounts));
        }

        @BeforeEach
        private void beforeEach() {
            mockFindAll()
                .thenReturn(PRODUCT_LIST);
        }

        @AfterEach
        private void afterEach() {
            verifyFindAll(1)
                .getProducts();
        }

        @Test
        @DisplayName("ProductService getProducts 메서드의 리턴값을 리턴한다.")
        public void it_returns_a_productService_getProducts_return_value() {
            assertThat(subject())
                .matches(
                    outputList -> Arrays.deepEquals(
                        PRODUCT_LIST.toArray(), outputList.toArray()
                    )
                );
        }
    }
}
