package com.codesoom.assignment.global;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {PassWordFormValidator.class})
public @interface PassWordForm {
	String message() default "custom not blank !!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
