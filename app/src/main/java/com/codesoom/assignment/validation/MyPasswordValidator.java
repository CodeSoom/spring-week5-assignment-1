package com.codesoom.assignment.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 올바른 패스워드 인지 검증하는 Validator 클래스
 * 규칙
 * 1. 8 ~ 16 글자 사이여야 한다.
 * 2. 특수 문자, 영문, 숫자 3종류를 조합해야 한다.
 */
public class MyPasswordValidator implements ConstraintValidator<MyPassword, String> {

    private Pattern pattern;
    @Override
    public void initialize(MyPassword constraintAnnotation) {
        pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
