package com.codesoom.assignment.utils;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.codesoom.assignment.support.ProductFixture.TOY_1;
import static com.codesoom.assignment.support.UserFixture.USER_1;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Jackson을 사용한 JsonUtil")
class JsonUtilTest {

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class Product_도메인_엔티티는 {
        @Test
        @DisplayName("데이터를 직렬화한 후 역 직렬화하면 기존 데이터를 리턴한다")
        void write_read() throws IOException {
            Product originData = TOY_1.엔티티_생성();

            String requestStr = JsonUtil.writeValue(originData);

            Product resultData = JsonUtil.readValue(requestStr, Product.class);

            assertThat(originData).isEqualTo(resultData);
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class User_도메인_엔티티는 {
        @Test
        @DisplayName("데이터를 직렬화한 후 역 직렬화하면 기존 데이터를 리턴한다")
        void write_read() throws IOException {
            User originData = USER_1.회원_엔티티_생성();

            String requestStr = JsonUtil.writeValue(originData);

            User resultData = JsonUtil.readValue(requestStr, User.class);

            assertThat(originData).isEqualTo(resultData);
        }
    }
}
