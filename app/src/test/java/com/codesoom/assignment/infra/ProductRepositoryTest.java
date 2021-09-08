package com.codesoom.assignment.infra;

import static com.codesoom.assignment.constants.ProductConstants.PRODUCT;
import static com.codesoom.assignment.constants.ProductConstants.NAME;
import static com.codesoom.assignment.constants.ProductConstants.MAKER;
import static com.codesoom.assignment.constants.ProductConstants.IMAGE_URL;
import static com.codesoom.assignment.constants.ProductConstants.PRICE;
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
    private List<Product> products;

    void subjectFindAll() {
        products = productRepository.findAll();
    }

    void subjectSave() {
        savedProduct = productRepository.save(PRODUCT);
    }

    void subjectDelete() {
        productRepository.delete(savedProduct);
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {
        @Nested
        @DisplayName("저장된 Product가 있는 경우")
        class Context_product_exist {
            @BeforeEach
            void beforeEach() {
                subjectSave();
            }

            @AfterEach
            void afterEach() {
                subjectDelete();
            }

            @Test
            @DisplayName("Product 목록을 리턴한다.")
            void it_returns_a_product_list() {
                subjectFindAll();

                assertThat(products)
                    .extracting(
                    Product::getName, Product::getMaker,
                    Product::getImageUrl, Product::getPrice
                    )
                    .contains(tuple(NAME, MAKER, IMAGE_URL, PRICE));
            }
        }

        @Nested
        @DisplayName("저장된 Product가 없는 경우")
        class Context_product_empty {
            @Test
            @DisplayName("빈 목록을 리턴한다.")
            void it_returns_a_empty_list() {
                subjectFindAll();

                assertThat(products)
                    .isEmpty();
            }
        }
    }

}
