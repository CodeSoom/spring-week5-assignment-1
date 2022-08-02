package com.codesoom.assignment.dto;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ProductData 클래스의")
class ProductDataTest {
    public static final String NORMAL_NAME = "장난감";
    public static final String NORMAL_MAKER = "코드숨";
    public static final int NORMAL_PRICE = 2200000;
    public static final String NORMAL_URL = "picture.com";
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

        assertEquals(1, constraintViolations.size());
        assertEquals("이름은 필수값입니다.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("이름은 길이의 범위를 벗어날 수 없다.")
    void nameCannotBeOutOfRange() {
        productData = new ProductData("원", NORMAL_MAKER, NORMAL_PRICE, NORMAL_URL);

        constraintViolations = validator.validate(productData);

        assertEquals(1, constraintViolations.size());
        assertEquals("이름의 길이가 범위를 벗어납니다.", constraintViolations.iterator().next().getMessage());

        productData = new ProductData("길이가 범위를 넘은 값을 가진 이름입니다.", NORMAL_MAKER, NORMAL_PRICE, NORMAL_URL);

        constraintViolations = validator.validate(productData);

        assertEquals(1, constraintViolations.size());
        assertEquals("이름의 길이가 범위를 벗어납니다.", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "  "})
    @NullSource
    @DisplayName("메이커는 빈 값을 가질 수 없다.")
    void makerCannotBeEmpty(String input) {
        productData = new ProductData(NORMAL_NAME, input, NORMAL_PRICE, NORMAL_URL);

        constraintViolations = validator.validate(productData);

        assertEquals(1, constraintViolations.size());
        assertEquals("메이커는 필수값입니다.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("메이커는 길이의 범위를 벗어날 수 없다.")
    void makerCannotBeOutOfRange() {
        productData = new ProductData(NORMAL_NAME, "길이의 범위를 벗어남", NORMAL_PRICE, NORMAL_URL);

        constraintViolations = validator.validate(productData);

        assertEquals(1, constraintViolations.size());
        assertEquals("메이커 길이가 범위를 벗어납니다.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("가격은 필수값이다.")
    void priceIsMandatory() {
        productData = new ProductData(NORMAL_NAME, NORMAL_MAKER, null, NORMAL_URL);

        constraintViolations = validator.validate(productData);

        assertEquals(1, constraintViolations.size());
        assertEquals("가격은 필수값입니다.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("가격은 음수이거나 타입의 한계를 초과할 수 없다.")
    void priceCannotBeNegativeOrExceedTypeLimit() {
        productData = new ProductData(NORMAL_NAME, NORMAL_MAKER, -100, NORMAL_URL);


        constraintViolations = validator.validate(productData);

        assertEquals(1, constraintViolations.size());
        assertEquals("가격은 음수일 수 없습니다.", constraintViolations.iterator().next().getMessage());

        productData = new ProductData(NORMAL_NAME, NORMAL_MAKER, 10000001, NORMAL_URL);

        constraintViolations = validator.validate(productData);

        assertEquals(1, constraintViolations.size());
        assertEquals("가격의 한계치를 벗어났습니다.", constraintViolations.iterator().next().getMessage());
    }
}
