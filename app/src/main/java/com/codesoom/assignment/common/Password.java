package com.codesoom.assignment.common;

import com.codesoom.assignment.common.validator.PasswordValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
    @Target -> 어디에 사용할 것인가
    @Retention -> 어노테이션 유지범위
    @Constraint -> 검증 클래스 지정
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String message() default "";
    Class[] groups() default {};
    Class[] payload() default {};
}
