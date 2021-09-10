package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;

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

import java.util.Arrays;
import java.util.List;

import static com.codesoom.assignment.constants.ProductConstants.PRODUCT_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
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
    public class Describe_getProducts {
        private ListAssert<Product> subject() {
            return assertThat(productService.getProducts());
        }

        private OngoingStubbing<List<Product>> mockFindAll() {
            return when(productRepository.findAll());
        }

        private ProductRepository verifyFindAll(final int invokeCounts) {
            return verify(productRepository, times(invokeCounts));
        }

        @BeforeEach
        private void beforeEach() {
            mockFindAll()
                .thenReturn(PRODUCT_LIST);
        }

        @AfterEach
        private void afterEach() {
            verifyFindAll(1)
                .findAll();
        }

        @Test
        @DisplayName("ProductRepository findAll 메서드의 리턴값을 리턴한다.")
        public void it_returns_a_product_list() {
            subject()
                .matches(
                    outputList -> Arrays.deepEquals(
                        PRODUCT_LIST.toArray(), outputList.toArray()
                    )
                );
        }
    }
}
