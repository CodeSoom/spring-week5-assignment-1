package com.codesoom.assignment.product.adapter.out.persistence;

import com.codesoom.assignment.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.codesoom.assignment.support.IdFixture.ID_MAX;
import static com.codesoom.assignment.support.ProductFixture.TOY_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("ProductPersistenceAdapter JPA 테스트")
class JpaProductPersistenceAdapterTest {

    @Autowired
    private JpaProductPersistenceAdapter jpaProductPersistenceAdapter;

    @BeforeEach
    void setUpDeleteAllFixture() {
        jpaProductPersistenceAdapter.deleteAllInBatch();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class findAll_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_장난감이_없을_때 {
            @Test
            @DisplayName("빈 리스트를 리턴한다")
            void it_returns_empty_list() {
                assertThat(jpaProductPersistenceAdapter.findAll())
                        .isEmpty();
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_장난감이_있을_때 {
            @BeforeEach
            void setUpCreate() {
                jpaProductPersistenceAdapter.save(TOY_1.엔티티_생성());
            }

            @Test
            @DisplayName("비어있지 않은 리스트를 리턴한다")
            void it_returns_empty_list() {
                List<Product> products = jpaProductPersistenceAdapter.findAll();

                assertThat(products).isNotEmpty();
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class findById_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어지면 {
            @Test
            @DisplayName("빈 값을 리턴한다")
            void it_returns_empty_product() {
                assertThat(jpaProductPersistenceAdapter.findById(ID_MAX.value()))
                        .isEmpty();
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어지면 {
            private Product productSource;

            @BeforeEach
            void setUpCreate() {
                productSource = jpaProductPersistenceAdapter.save(TOY_1.엔티티_생성());
            }

            @Test
            @DisplayName("해당 id의 장난감 정보를 리턴한다")
            void it_returns_product() {
                Optional<Product> product = jpaProductPersistenceAdapter.findById(productSource.getId());

                assertThat(product).isPresent();
                assertThat(product.get().getName()).isEqualTo(TOY_1.NAME());
                assertThat(product.get().getMaker()).isEqualTo(TOY_1.MAKER());
                assertThat(product.get().getPrice()).isEqualTo(TOY_1.PRICE());
                assertThat(product.get().getImageUrl()).isEqualTo(TOY_1.IMAGE());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class save_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class null이_주어지면 {
            @Test
            @DisplayName("예외를 던진다")
            void it_returns_exception() {
                assertThatThrownBy(() -> jpaProductPersistenceAdapter.save(null))
                        .hasCauseInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 새로운_상품이_주어지면 {
            @Test
            @DisplayName("상품을 저장하고 리턴한다")
            void it_returns_product() {
                Product product = jpaProductPersistenceAdapter.save(TOY_1.엔티티_생성());

                assertThat(product).isNotNull();
                assertThat(product.getName()).isEqualTo(TOY_1.NAME());
                assertThat(product.getMaker()).isEqualTo(TOY_1.MAKER());
                assertThat(product.getPrice()).isEqualTo(TOY_1.PRICE());
                assertThat(product.getImageUrl()).isEqualTo(TOY_1.IMAGE());
            }

            @Test
            @DisplayName("findAll 메서드 리턴값이 1 증가한다")
            void it_returns_count() {
                int oldSize = jpaProductPersistenceAdapter.findAll().size();

                jpaProductPersistenceAdapter.save(TOY_1.엔티티_생성());

                int newSize = jpaProductPersistenceAdapter.findAll().size();

                assertThat(newSize - oldSize).isEqualTo(1);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class delete_메서드는 {
        private Product productSource;

        @BeforeEach
        void setUpCreateFixture() {
            productSource = jpaProductPersistenceAdapter.save(TOY_1.엔티티_생성());
        }

        @Test
        @DisplayName("상품을 삭제한다")
        void it_deleted() {
            Long id = productSource.getId();

            assertThat(jpaProductPersistenceAdapter.findById(id)).isNotEmpty();

            jpaProductPersistenceAdapter.delete(productSource);

            assertThat(jpaProductPersistenceAdapter.findById(id)).isEmpty();
        }
    }
}
