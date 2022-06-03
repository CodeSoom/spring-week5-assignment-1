package com.codesoom.assignment.global;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassWordFormValidator implements ConstraintValidator<PassWordForm, String> {
    private static Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");

    @Override
    public void initialize(PassWordForm constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        Matcher passMatcher1 = PASSWORD_PATTERN.matcher(value);
        int inValidCount = 0;
        if (!passMatcher1.find()) {
            context.buildConstraintViolationWithTemplate("비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.")
                    .addConstraintViolation();
            inValidCount += 1;
        }
        return inValidCount == 0;
    }
}
