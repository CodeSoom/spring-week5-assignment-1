package com.codesoom.assignment.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator <Password, String> {

    private String pattern;

    @Override
    public void initialize(Password constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean matches = Pattern.matches(pattern, value);

        if (!matches) {
            return false;
        }
        return true;
    }

}
