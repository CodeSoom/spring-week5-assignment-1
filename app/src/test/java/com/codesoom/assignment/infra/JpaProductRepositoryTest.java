package com.codesoom.assignment.infra;

import static com.codesoom.assignment.domain.ProductConstants.PRODUCT;
import static com.codesoom.assignment.domain.ProductConstants.NAME;
import static com.codesoom.assignment.domain.ProductConstants.MAKER;
import static com.codesoom.assignment.domain.ProductConstants.PRICE;
import static com.codesoom.assignment.domain.ProductConstants.IMAGE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;

import com.codesoom.assignment.domain.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Nested
@DisplayName("JpaProductRepository 클래스")
@DataJpaTest
public class JpaProductRepositoryTest {
    @Autowired
    private JpaProductRepository jpaProductRepository;

    private Product savedProduct;
    private List<Product> products;

    void subject_findAll() {
        products = jpaProductRepository.findAll();
    }

    void subject_save() {
        savedProduct = jpaProductRepository.save(PRODUCT);
    }

    void subject_delete() {
        jpaProductRepository.delete(savedProduct);
    }

    @BeforeEach
    void beforeEach() {
        jpaProductRepository.deleteAll();
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {
        @Nested
        @DisplayName("저장된 Product가 있는 경우")
        class Context_product_exist {
            @BeforeEach
            void beforeEach() {
                subject_save();
            }

            @Test
            @DisplayName("Product 목록을 리턴한다.")
            void it_returns_a_product_list() {
                subject_findAll();

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
                subject_findAll();

                assertThat(products)
                    .isEmpty();
            }
        }
    }

}
