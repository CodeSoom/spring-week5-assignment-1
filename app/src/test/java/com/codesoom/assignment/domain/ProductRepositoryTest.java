package com.codesoom.assignment.domain;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("ProductRepository 클래스")
class ProductRepositoryTest {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ProducerRepository producerRepository;

    private Product product;
    private Producer producer;
    private final Long PRODUCT_ID = 2L;
    private final Long PRODUCER_ID = 2L;
    private final String PRODUCT_NAME = "Test Product";
    private final String PRODUCER_NAME = "Test Producer";
    private final BigDecimal MONEY_VALUE = new BigDecimal(1000);

    @BeforeEach
    void setUp() {
        producer = new ToyProducer(PRODUCER_ID, PRODUCT_NAME);
        product = new Toy(TOY_ID, TOY_NAME, producer, MONEY_VALUE);
    }

    @AfterEach
    void clear() {
        repository.deleteAll();
        producerRepository.deleteAll();
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {
        private List<Product> subject() {
            return repository.findAll();
        }

        @Nested
        @DisplayName("만약 상품이 존재하지 않는다면")
        class Context_without_existing_product {
            @Test
            @DisplayName("비어 있는 List를 반환한다")
            void it_returns_empty_list() {
                final List<Product> actual = subject();

                assertThat(actual).isEmpty();
            }
        }

        @Nested
        @DisplayName("만약 상품이 존재한다면")
        class Context_with_existing_product {
            @BeforeEach
            void setUp() {
                producerRepository.save(producer);
                repository.save(product);
            }

            @Test
            @DisplayName("비어 있지 않은 List를 반환한다")
            void it_returns_not_empty_list() {
                final List<Product> actual = subject();

                assertThat(actual).isNotEmpty();
            }
        }
    }

}
