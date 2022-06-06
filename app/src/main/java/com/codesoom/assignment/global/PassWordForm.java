package com.codesoom.assignment.global;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {PassWordFormValidator.class})
public @interface PassWordForm {
    String message() default "${ValidationMessage.properties.password}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
