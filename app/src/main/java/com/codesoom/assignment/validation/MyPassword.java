package com.codesoom.assignment.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { MyPasswordValidator.class })
public @interface MyPassword {

    String message() default "올바르지 않는 비밀번호 형식입니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
