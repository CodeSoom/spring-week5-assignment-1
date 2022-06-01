package com.codesoom.assignment.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyPasswordValidator implements ConstraintValidator<MyPassword, String> {

    // 8 ~ 16 글자 사이여야 한다.
    // 특수 문자, 영문, 숫자 3종류를 조합해야 한다.
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
