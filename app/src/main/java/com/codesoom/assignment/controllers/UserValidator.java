package com.codesoom.assignment.controllers;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserValidator implements
        ConstraintValidator<NullOrNoEmptyString, String> {

    @Override
    public void initialize(NullOrNoEmptyString contactNumber) {
    }

    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || value.length() > 0;
    }
}
