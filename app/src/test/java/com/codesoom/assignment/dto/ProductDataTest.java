package com.codesoom.assignment.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProductDate 클래스의")
class ProductDataTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("이름은 빈 값을 가질 수 없다.")
    void nameCannotHaveEmptyValue() {
        ProductData productData = new ProductData(null, "코드숨", 2200000, "picture.com");

        Set<ConstraintViolation<ProductData>> constraintViolations = validator.validate(productData);

        assertEquals(1, constraintViolations.size());
        assertEquals("이름은 필수값입니다.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("이름은 길이의 범위를 벗어날 수 없다.")
    void nameCannotBeOutOfRange() {
        ProductData productData = new ProductData("원", "코드숨", 2200000, "picture.com");

        Set<ConstraintViolation<ProductData>> constraintViolations = validator.validate(productData);

        assertEquals(1, constraintViolations.size());
        assertEquals("이름의 길이가 범위를 벗어납니다.", constraintViolations.iterator().next().getMessage());

        productData = new ProductData("길이가 범위를 넘는 값을 가진 이름임ㅇ", "코드숨", 2200000, "picture.com");

        constraintViolations = validator.validate(productData);

        assertEquals(1, constraintViolations.size());
        assertEquals("이름의 길이가 범위를 벗어납니다.", constraintViolations.iterator().next().getMessage());
    }
}