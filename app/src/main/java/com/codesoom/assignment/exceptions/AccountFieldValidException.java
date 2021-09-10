package com.codesoom.assignment.exceptions;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class AccountFieldValidException extends RuntimeException {
    public static final String DEFAULT_MESSAGE_FORM = "회원 정보의 [%s]이 다음과 같은 이유로 유효하지 않습니다. 사유: %s";
    public static final String EMPTY_STRING = "";

    private static final StringBuilder builder = new StringBuilder();

    public AccountFieldValidException(Set<ConstraintViolation<Object>> validate) {
        super(makeExceptionMessage(validate));
    }

    private static String makeExceptionMessage(Set<ConstraintViolation<Object>> validate) {
        builder.replace(0, builder.length(), EMPTY_STRING);

        for (ConstraintViolation<Object> v : validate) {
            builder.append(String.format(DEFAULT_MESSAGE_FORM, v.getPropertyPath(), v.getMessage()));
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }
}
