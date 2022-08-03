package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@DisplayName("ProductRepository 인터페이스의")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product getInputProduct() {
        return new Product("캣타워", "코드숨", 12000, "url");
    }

    private Product getExpectProduct(Long id) {
        return new Product(id, "캣타워", "코드숨", 12000, "url");
    }

    @Nested
    @DisplayName("save 메서드는")
    class Describe_save {
        @Nested
        @DisplayName("상품이 주어지면")
        class Context_with_product {
            Product givenProduct = getInputProduct();

            Product expectProduct(Long id) {
                return getExpectProduct(id);
            }

            @Test
            @DisplayName("상품을 저장하고 리턴한다")
            void It_returns_productAndSave() {
                Product resultProduct = productRepository.save(givenProduct);

                assertThat(resultProduct)
                        .isEqualTo(expectProduct(resultProduct.getId()));
            }
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {
        @Nested
        @DisplayName("상품이 주어지면")
        class Context_with_product {
            Product createProduct() {
                return productRepository.save(getInputProduct());
            }

            @Test
            @DisplayName("상품을 리턴한다")
            void It_returns_product() {
                Product createdProduct = createProduct();

                assertThat(getExpectProduct(createdProduct.getId()))
                        .isEqualTo(createdProduct);
            }
        }
    }
}
