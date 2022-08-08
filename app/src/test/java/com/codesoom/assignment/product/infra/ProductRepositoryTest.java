package com.codesoom.assignment.product.infra;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.ProductRepository;
import com.codesoom.assignment.product.dto.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {
        @Nested
        @DisplayName("상품 목록이 있다면")
        class Context_with_productList {
            void prepare() {
                productRepository.save(getInputProduct());
                productRepository.save(getInputProduct());
                productRepository.save(getInputProduct());
            }

            @Test
            @DisplayName("상품 목록을 리턴한다")
            void It_returns_productList() {
                prepare();

                assertThat(productRepository.findAll())
                        .hasSize(3);
            }
        }
    }

    @Nested
    @DisplayName("deleteAll 메서드는")
    class Describe_deleteAll {
        @Nested
        @DisplayName("상품들이 있으면")
        class Context_with_products {
            void prepare() {
                productRepository.save(getInputProduct());
                productRepository.save(getInputProduct());
                productRepository.save(getInputProduct());
            }

            @Test
            @DisplayName("상품들을 전부 제거한다")
            void It_remove_allProducts() {
                prepare();
                assertThat(productRepository.findAll()).hasSize(3);

                productRepository.deleteAll();
                assertThat(productRepository.findAll()).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메서드는")
    class Describe_deleteById {
        @Nested
        @DisplayName("찾는 상품이 있다면")
        class Context_with_product {
            Long findId;

            @BeforeEach
            void prepare() {
                findId = productRepository.save(getInputProduct()).getId();
            }

            @Test
            @DisplayName("상품을 삭제한다")
            void It_remove_product() {
                assertTrue(productRepository.findById(findId).isPresent());

                productRepository.deleteById(findId);

                assertTrue(productRepository.findById(findId).isEmpty());
            }
        }

        @Nested
        @DisplayName("찾는 상품이 없으면")
        class Context_without_product {
            @Test
            @DisplayName("EmptyResultDataAccessException 을 던진다")
            void It_throws_exception() {
                assertThatThrownBy(() -> productRepository.deleteById(-1L))
                        .isInstanceOf(RuntimeException.class)
                        .isExactlyInstanceOf(EmptyResultDataAccessException.class);
            }
        }

        @Nested
        @DisplayName("null 이 주어지면")
        class Context_with_null {
            @Test
            @DisplayName("InvalidDataAccessApiUsageException 을 던진다")
            void It_throws_exception() {
                assertThatThrownBy(() -> productRepository.deleteById(null))
                        .isInstanceOf(RuntimeException.class)
                        .isExactlyInstanceOf(InvalidDataAccessApiUsageException.class);
            }

        }
    }
}
