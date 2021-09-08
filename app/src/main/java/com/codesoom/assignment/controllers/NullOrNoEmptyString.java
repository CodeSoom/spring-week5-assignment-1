package com.codesoom.assignment.controllers;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = UserValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NullOrNoEmptyString {
    String message() default "should be null or not empty string";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
