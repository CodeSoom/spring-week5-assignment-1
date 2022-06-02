package com.codesoom.assignment.global;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class CustomNotBlankValidator implements ConstraintValidator<CustomNotBlank, String> {
	@Override
	public void initialize(CustomNotBlank constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.isNotBlank(value);
	}
}
