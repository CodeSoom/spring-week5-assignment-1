package com.codesoom.assignment.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ProductDate 클래스의")
class ProductDataTest {

    public static final String NORMAL_NAME = "코드숨";
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
        productData = new ProductData(null, NORMAL_NAME, NORMAL_PRICE, NORMAL_URL);

        constraintViolations = validator.validate(productData);

        assertEquals(1, constraintViolations.size());
        assertEquals("이름은 필수값입니다.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("이름은 길이의 범위를 벗어날 수 없다.")
    void nameCannotBeOutOfRange() {
        productData = new ProductData("원", NORMAL_NAME, NORMAL_PRICE, NORMAL_URL);

        constraintViolations = validator.validate(productData);

        assertEquals(1, constraintViolations.size());
        assertEquals("이름의 길이가 범위를 벗어납니다.", constraintViolations.iterator().next().getMessage());

        productData = new ProductData("길이가 범위를 넘는 값을 가진 이름임ㅇ", NORMAL_NAME, NORMAL_PRICE, NORMAL_URL);

        constraintViolations = validator.validate(productData);

        assertEquals(1, constraintViolations.size());
        assertEquals("이름의 길이가 범위를 벗어납니다.", constraintViolations.iterator().next().getMessage());
    }
}