package com.codesoom.assignment.product.domain;

import com.codesoom.assignment.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codesoom.assignment.support.IdFixture.ID_MAX;
import static com.codesoom.assignment.support.IdFixture.ID_MIN;
import static com.codesoom.assignment.support.ProductFixture.TOY_1;
import static com.codesoom.assignment.support.ProductFixture.TOY_3;
import static com.codesoom.assignment.support.UserFixture.USER_1;
import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    @Test
    void creationWithBuilder() {
        Product product = TOY_1.엔티티_생성(ID_MIN.value());

        assertThat(product.getId()).isEqualTo(ID_MIN.value());
        assertThat(product.getName()).isEqualTo(TOY_1.NAME());
        assertThat(product.getMaker()).isEqualTo(TOY_1.MAKER());
        assertThat(product.getPrice()).isEqualTo(TOY_1.PRICE());
        assertThat(product.getImageUrl()).isEqualTo(TOY_1.IMAGE());
    }

    @Test
    void change() {
        Product product = TOY_1.엔티티_생성(ID_MIN.value());

        product.update(TOY_3.수정_요청_데이터_생성().toEntity());

        assertThat(product.getName()).isEqualTo(TOY_3.NAME());
        assertThat(product.getMaker()).isEqualTo(TOY_3.MAKER());
        assertThat(product.getPrice()).isEqualTo(TOY_3.PRICE());
        // PATCH: null이면 변경 안함
        assertThat(product.getImageUrl()).isEqualTo(TOY_1.IMAGE());
    }

    @Test
    @DisplayName("Product 객체 비교 테스트")
    void equals_and_hashcode() {
        Product product1 = TOY_1.엔티티_생성(ID_MIN.value());
        Product product2 = TOY_1.엔티티_생성(ID_MIN.value());

        assertThat(product1).isEqualTo(product2);

        Product product3 = TOY_1.엔티티_생성(ID_MAX.value());

        assertThat(product1).isNotEqualTo(product3);
    }
}
