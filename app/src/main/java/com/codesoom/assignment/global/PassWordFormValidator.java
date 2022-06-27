package com.codesoom.assignment.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@PropertySource(value = "classpath:validation_messages.yml", encoding = "UTF-8")
public class PassWordFormValidator implements ConstraintValidator<PassWordForm, String> {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");

    @Value(value = "${password}")
    private String PASSWORD_FORM_ERROR;

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
            context.buildConstraintViolationWithTemplate(PASSWORD_FORM_ERROR)
                    .addConstraintViolation();
            inValidCount += 1;
        }
        return inValidCount == 0;
    }
}
