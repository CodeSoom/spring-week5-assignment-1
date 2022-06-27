package com.codesoom.assignment.utils;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DtoGenerator test")
class DtoGeneratorTest {

    private Product product;
    private ProductDto productDto;
    private final Long ID = 1L;
    private final String NAME = "고양이 풀";
    private final String MAKER = "(주)애옹이";
    private final BigDecimal PRICE = BigDecimal.valueOf(10000);

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(ID)
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .build();

        productDto = ProductDto.builder()
                .id(ID)
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .build();
    }

    @Nested
    @DisplayName("ProductDto를 입력하면")
    class when_input_ProductDto {

        @Test
        @DisplayName("Product를 반환한다.")
        void toProduct() {
            assertThat(DtoGenerator.toProduct(productDto)).isEqualTo(product);
        }

    }

    @Nested
    @DisplayName("Product를 입력하면")
    class when_input_Product {

        @Test
        @DisplayName("ProductDto를 반환한다.")
        void fromProduct() {
            assertThat(DtoGenerator.fromProduct(product)).isEqualTo(productDto);
        }

    }
}