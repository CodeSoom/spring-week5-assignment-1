package com.codesoom.assignment.infra;

import static com.codesoom.assignment.constants.ProductConstants.PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Nested
@DisplayName("ProductRepository 클래스")
@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    private Product savedProduct;

    private List<Product> subjectFindAll() {
        return productRepository.findAll();
    }

    private Product subjectSave() {
        return productRepository.save(PRODUCT);
    }

    private void subjectDelete() {
        productRepository.delete(savedProduct);
    }

    @Nested
    @DisplayName("findAll 메서드는")
    public class Describe_findAll {
        @Nested
        @DisplayName("저장된 Product가 있는 경우")
        public class Context_product_exist {
            @BeforeEach
            private void beforeEach() {
                savedProduct = subjectSave();
            }

            @AfterEach
            private void afterEach() {
                subjectDelete();
            }

            @Test
            @DisplayName("Product 목록을 리턴한다.")
            public void it_returns_a_product_list() {
                assertThat(subjectFindAll())
                    .extracting(
                    Product::getName, Product::getMaker,
                    Product::getImageUrl, Product::getPrice
                    )
                    .contains(
                        tuple(
                            PRODUCT.getName(), PRODUCT.getMaker(),
                            PRODUCT.getImageUrl(), PRODUCT.getPrice()
                        )
                    );
            }
        }

        @Nested
        @DisplayName("저장된 Product가 없는 경우")
        public class Context_product_empty {
            @Test
            @DisplayName("빈 목록을 리턴한다.")
            public void it_returns_a_empty_list() {
                assertThat(subjectFindAll())
                    .isEmpty();
            }
        }
    }
}
