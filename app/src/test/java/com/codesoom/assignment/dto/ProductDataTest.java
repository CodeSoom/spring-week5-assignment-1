package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ProductData 클래스의")
class ProductDataTest {
    public static final String NORMAL_NAME = "장난감";
    public static final String NORMAL_MAKER = "코드숨";
    public static final int NORMAL_PRICE = 2200000;
    public static final String NORMAL_URL = "picture.com";
    public static final String SALE = "SALE";
    private static Validator validator;
    private ProductData productData;
    private Set<ConstraintViolation<ProductData>> constraintViolations;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("이름은 빈 값을 가질 수 없다.")
    void nameCannotHaveEmptyValue() {
        productData = new ProductData(null, NORMAL_MAKER, NORMAL_PRICE, NORMAL_URL);

        constraintViolations = validator.validate(productData);

        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    @DisplayName("이름은 길이의 범위를 벗어날 수 없다.")
    void nameCannotBeOutOfRange() {
        productData = new ProductData("원", NORMAL_MAKER, NORMAL_PRICE, NORMAL_URL);

        constraintViolations = validator.validate(productData);

        assertThat(constraintViolations).isNotEmpty();

        productData = new ProductData("길이가 범위를 넘은 값을 가진 이름입니다.", NORMAL_MAKER, NORMAL_PRICE, NORMAL_URL);

        constraintViolations = validator.validate(productData);

        assertThat(constraintViolations).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "  "})
    @NullSource
    @DisplayName("메이커는 빈 값을 가질 수 없다.")
    void makerCannotBeEmpty(String input) {
        productData = new ProductData(NORMAL_NAME, input, NORMAL_PRICE, NORMAL_URL);

        constraintViolations = validator.validate(productData);

        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    @DisplayName("메이커는 길이의 범위를 벗어날 수 없다.")
    void makerCannotBeOutOfRange() {
        productData = new ProductData(NORMAL_NAME, "길이의 범위를 벗어남", NORMAL_PRICE, NORMAL_URL);

        constraintViolations = validator.validate(productData);

        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    @DisplayName("가격은 필수값이다.")
    void priceIsMandatory() {
        productData = new ProductData(NORMAL_NAME, NORMAL_MAKER, null, NORMAL_URL);

        constraintViolations = validator.validate(productData);

        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    @DisplayName("가격은 음수이거나 타입의 한계를 초과할 수 없다.")
    void priceCannotBeNegativeOrExceedTypeLimit() {
        productData = new ProductData(NORMAL_NAME, NORMAL_MAKER, -100, NORMAL_URL);


        constraintViolations = validator.validate(productData);

        assertThat(constraintViolations).isNotEmpty();

        productData = new ProductData(NORMAL_NAME, NORMAL_MAKER, 10000001, NORMAL_URL, SALE);

        constraintViolations = validator.validate(productData);

        assertEquals(1, constraintViolations.size());
        assertEquals("가격의 한계치를 벗어났습니다.", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "  "})
    @NullSource
    @DisplayName("상태값은 필수 값이다.")
    void statusIsMandatory(String input) {
        productData = ProductData.builder()
                .name(NORMAL_NAME)
                .maker(NORMAL_MAKER)
                .price(NORMAL_PRICE)
                .status(input)
                .build();

        constraintViolations = validator.validate(productData);

        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    @DisplayName("상품 정보를 상품으로 변경할 수 있다.")
    void productDataCanConvertProduct() {
        assertEquals(new Product(null, NORMAL_NAME, NORMAL_MAKER, NORMAL_PRICE, NORMAL_URL, Status.SALE),
                new ProductData(NORMAL_NAME, NORMAL_MAKER, NORMAL_PRICE, NORMAL_URL, SALE).toProduct());
    }
}
