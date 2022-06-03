package com.codesoom.assignment.domain;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {PasswordValidator.class})
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "영문, 특수문자, 숫자 포함 8자 이상이어야 합니다";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String regexp() default ".*";

    String pattern() default "";
}
