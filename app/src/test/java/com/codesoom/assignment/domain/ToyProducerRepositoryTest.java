package com.codesoom.assignment.domain;

import com.codesoom.assignment.domain.entities.ToyProducer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("ToyProducerRepository")
class ToyProducerRepositoryTest {
    @Autowired
    private ToyProducerRepository repository;

    private ToyProducer producer;
    private final String PRODUCER_NAME = "Test Producer";

    @BeforeEach
    void setUp() {
        producer = ToyProducer.builder()
                .name(PRODUCER_NAME)
                .build();
    }

    @AfterEach
    void clear() {
        repository.deleteAll();
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {
        private List<ToyProducer> subject() {
            return repository.findAll();
        }

        @Nested
        @DisplayName("만약 생산자가 존재하지 않는다면")
        class Context_without_existing_producer {
            @Test
            @DisplayName("비어 있는 List를 반환한다")
            void it_returns_empty_list() {
                final List<ToyProducer> actual = subject();

                assertThat(actual).isEmpty();
            }
        }

        @Nested
        @DisplayName("만약 생산자가 존재한다면")
        class Context_with_existing_producer {
            @BeforeEach
            void setUp() {
                repository.save(producer);
            }

            @Test
            @DisplayName("비어 있지 않은 List를 반환한다")
            void it_returns_not_empty_list() {
                final List<ToyProducer> actual = subject();

                assertThat(actual).isNotEmpty();
            }
        }
    }

}
