package com.codesoom.assignment.infra;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


public class Validators {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static Set<ConstraintViolation<Object>> validate(Object o) {
        return validator.validate(o);
    }
}
