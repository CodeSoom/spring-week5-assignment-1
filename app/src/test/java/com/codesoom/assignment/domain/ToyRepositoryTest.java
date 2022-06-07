package com.codesoom.assignment.domain;

import com.codesoom.assignment.domain.entities.Toy;
import com.codesoom.assignment.domain.entities.ToyProducer;
import com.codesoom.assignment.domain.vos.ImageDemo;
import com.codesoom.assignment.domain.vos.Won;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("ToyRepository")
class ToyRepositoryTest {
    @Autowired
    private ToyRepository repository;
    @Autowired
    private ToyProducerRepository producerRepository;

    private Toy product;
    private ToyProducer producer;
    private Won money;
    private ImageDemo demo;
    private final String PRODUCT_NAME = "Test Product";
    private final String PRODUCER_NAME = "Test Producer";
    private final BigDecimal MONEY_VALUE = new BigDecimal(1000);
    private final String IMAGE_URL = "https://metacode.biz/@test/avatar.jpg";

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        producerRepository.deleteAll();

        demo = new ImageDemo(IMAGE_URL);
        money = new Won(MONEY_VALUE);

        producer = ToyProducer.builder()
                .name(PRODUCER_NAME)
                .build();
        product = Toy.builder()
                .name(PRODUCT_NAME)
                .price(money)
                .producer(producer)
                .demo(demo)
                .build();
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {
        private List<Toy> subject() {
            return repository.findAll();
        }

        @Nested
        @DisplayName("만약 상품이 존재하지 않는다면")
        class Context_without_existing_product {
            @Test
            @DisplayName("비어 있는 List를 반환한다")
            void it_returns_empty_list() {
                final List<Toy> actual = subject();

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
                final List<Toy> actual = subject();

                assertThat(actual).isNotEmpty();
            }
        }
    }

}
