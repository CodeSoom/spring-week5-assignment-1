package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ToyCrudServiceTest {
    private ToyCrudService service;
    private final ToyRepository repository = mock(ToyRepository.class);
    private final ToyProducerRepository producerRepository = mock(ToyProducerRepository.class);

    private Toy toy;
    private Toy toyWithoutId;
    private ToyProducer producer;
    private ToyProducer producerWithoutId;
    private Won money;
    private ImageDemo demo;
    private final Long TOY_ID = 1L;
    private final Long TOY_ID_NOT_EXISTING = 10L;
    private final Long TOY_PRODUCER_ID = 1L;
    private final String PRODUCT_NAME = "Test Product";
    private final String PRODUCER_NAME = "Test Producer";
    private final BigDecimal MONEY_VALUE = new BigDecimal(1000);
    private final String IMAGE_URL = "https://metacode.biz/@test/avatar.jpg";


    @BeforeEach
    void setUp() {
        service = new ToyCrudService(repository, producerRepository);
        demo = new ImageDemo(IMAGE_URL);
        money = new Won(MONEY_VALUE);

        producer = ToyProducer.builder()
                .id(TOY_PRODUCER_ID)
                .name(PRODUCER_NAME)
                .build();
        producerWithoutId = ToyProducer.builder()
                .name(PRODUCER_NAME)
                .build();
        toyWithoutId = Toy.builder()
                .name(PRODUCT_NAME)
                .price(money)
                .producer(producer)
                .demo(demo)
                .build();
        toy = Toy.builder()
                .id(TOY_ID)
                .name(PRODUCT_NAME)
                .price(money)
                .producer(producer)
                .demo(demo)
                .build();
    }

    @Nested
    @DisplayName("showAll 메소드는")
    class Describe_showAll {
        private List<Toy> subject() {
            return service.showAll();
        }

        @Nested
        @DisplayName("만약 존재하는 장난감이 없다면")
        class Context_without_existing_toy {
            @BeforeEach
            void setUp() {
                given(repository.findAll()).willReturn(List.of());
            }

            @Test
            @DisplayName("빈 리스트를 반환한다")
            void it_returns_empty_list() {
                assertThat(subject()).isEmpty();
            }
        }

        @Nested
        @DisplayName("만약 존재하는 장난감이 있다면")
        class Context_with_existing_toy {
            @BeforeEach
            void setUp() {
                given(repository.findAll()).willReturn(List.of(toy));
            }

            @Test
            @DisplayName("비어 있지 않은 리스트를 반환한다")
            void it_returns_not_empty_list() {
                assertThat(subject()).isNotEmpty();
            }
        }
    }

    @Nested
    @DisplayName("showById 메소드는")
    class Describe_showById {
        abstract class ContextShowingByExisting {
            Toy withExistingToy() {
                return service.showById(TOY_ID);
            }
        }

        abstract class ContextShowingByNotExisting {
            void withoutExistingToy() {
                service.showById(TOY_ID_NOT_EXISTING);
            }
        }

        @Nested
        @DisplayName("만약 존재하는 Toy를 조회한다면")
        class Context_with_existing_toy extends ContextShowingByExisting {
            @BeforeEach
            void setUp() {
                given(repository.findById(TOY_ID)).willReturn(Optional.of(toy));
            }

            @Test
            @DisplayName("매개변수로 전달한 값을 Id로 가지고 있는 Toy를 반환한다")
            void it_returns_toy_having_id_equal_to_param() {
                assertThat(withExistingToy().getId()).isEqualTo(TOY_ID);
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 Toy를 조회한다면")
        class Context_with_not_existing_toy extends ContextShowingByNotExisting {
            @Test
            @DisplayName("예외를 발생시킨다")
            void it_throws_exception() {
                assertThatThrownBy(this::withoutExistingToy)
                        .isInstanceOf(ProductNotFoundException.class);
            }

        }
    }

    @Nested
    @DisplayName("create 메소드는")
    class Describe_create {
        private Toy subject() {
            return service.create(toyWithoutId);
        }

        @BeforeEach
        void setUp() {
            given(producerRepository.save(producerWithoutId)).willReturn(producer);
            given(repository.save(toyWithoutId)).willReturn(
                    Toy.builder()
                            .id(TOY_ID)
                            .name(PRODUCT_NAME)
                            .price(money)
                            .producer(producer)
                            .demo(demo)
                            .build()
            );
        }

        @Test
        @DisplayName("매개변수로 전달한 값이 반영된 Task를 반환한다")
        void it_returns_toy_reflecting_params() {
            assertThat(subject().getName()).isEqualTo(PRODUCT_NAME);
            assertThat(subject().getProducer()).isEqualTo(producer);
            assertThat(subject().getDemo()).isEqualTo(demo);
            assertThat(subject().getPrice()).isEqualTo(money);
        }
    }


    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {
        abstract class ContextUpdatingExisting {
            Toy withExistingToy() {
                return service.update(TOY_ID, toyWithoutId);
            }
        }

        abstract class ContextUpdatingNotExisting {
            void withoutExistingToy() {
                service.update(TOY_ID_NOT_EXISTING, toyWithoutId);
            }
        }

        @Nested
        @DisplayName("만약 존재하는 Toy를 수정한다면")
        class Context_with_existing_toy extends ContextUpdatingExisting {
            @BeforeEach
            void setUp() {
                given(repository.existsById(TOY_ID)).willReturn(Boolean.TRUE);
                given(repository.save(any(Toy.class))).will(invocation -> {
                    Toy source = invocation.getArgument(0);
                    return Toy.builder()
                            .id(TOY_ID)
                            .name(source.getName())
                            .producer(source.getProducer())
                            .price(source.getPrice())
                            .demo(source.getDemo())
                            .build();
                });
            }

            @Test
            @DisplayName("매개변수로 전달한 값을 Id로 가지고 있는 Toy를 반환한다")
            void it_returns_toy_having_id_equal_to_param() {
                assertThat(withExistingToy().getId()).isEqualTo(TOY_ID);
            }

            @Test
            @DisplayName("매개변수로 전달한 값이 반영된 Toy를 반환한다")
            void it_returns_toy_reflecting_params() {
                assertThat(withExistingToy().getName()).isEqualTo(PRODUCT_NAME);
                assertThat(withExistingToy().getProducer()).isEqualTo(producer);
                assertThat(withExistingToy().getDemo()).isEqualTo(demo);
                assertThat(withExistingToy().getPrice()).isEqualTo(money);
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 Toy를 수정한다면")
        class Context_with_not_existing_toy extends ContextUpdatingNotExisting {
            @Test
            @DisplayName("예외를 발생시킨다")
            void it_throws_exception() {
                assertThatThrownBy(this::withoutExistingToy)
                        .isInstanceOf(ProductNotFoundException.class);
            }

        }
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_deleteTask {
        abstract class ContextDeletingExisting {
            void withExistingToy() {
                service.deleteBy(TOY_ID);
            }
        }

        abstract class ContextDeletingNotExisting {
            void withoutExistingToy() {
                service.deleteBy(TOY_ID_NOT_EXISTING);
            }
        }

        @Nested
        @DisplayName("만약 존재하는 Toy를 삭제한다면")
        class Context_with_existing_toy extends ContextDeletingExisting {
            @BeforeEach
            void setUp() {
                given(repository.existsById(TOY_ID)).willReturn(Boolean.TRUE);
            }

            @Test
            @DisplayName("값을 반환하지 않는다")
            void it_returns_nothing() {
                withExistingToy();
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 Toy를 삭제한다면")
        class Context_with_not_existing_toy extends ContextDeletingNotExisting {

            @Test
            @DisplayName("예외를 발생시킨다")
            void it_throws_exception() {
                assertThatThrownBy(this::withoutExistingToy)
                        .isInstanceOf(ProductNotFoundException.class);
            }

        }
    }

}
